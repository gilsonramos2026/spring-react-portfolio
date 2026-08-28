package com.portfolio.service.admin.impl;

import com.portfolio.dto.request.TestimonialRequest;
import com.portfolio.dto.response.TestimonialResponse;
import com.portfolio.entity.Testimonial;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.mapper.TestimonialMapper;
import com.portfolio.repository.TestimonialRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita o Mockito para inicializar e gerenciar os mocks nesta classe de teste do JUnit 5
class AdminTestimonialServiceImplTest {

    @Mock
    private TestimonialRepository testRepo; // Cria um mock do repositório de depoimentos para isolar o acesso ao banco de dados

    @Mock
    private TestimonialMapper mapper; // Cria um mock do conversor responsável por aplicar dados de requisições e mapear entidades para DTOs

    @InjectMocks
    private AdminTestimonialServiceImpl testimonialService; // Instancia o serviço real administrativo injetando os mocks acima nele

    @Test
    @DisplayName("Deve retornar uma lista com todos os depoimentos cadastrados")
    void shouldReturnAllTestimonialsSuccessfully() {
        // Arrange (Configuração do Cenário)
        Testimonial t = Testimonial.builder().id(1L).name("Cliente X").build();
        TestimonialResponse r = TestimonialResponse.builder().id(1L).name("Cliente X").build();

        // Configura o repositório para retornar a listagem completa (visão administrativa)
        when(testRepo.findAll()).thenReturn(List.of(t));
        when(mapper.toResponse(t)).thenReturn(r);

        // Act (Execução da Ação)
        List<TestimonialResponse> result = testimonialService.getAllTestimonials();

        // Assert (Validação das Expectativas)
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(testRepo, times(1)).findAll(); // Confirma que a busca total foi acionada exatamente 1 vez
    }

    @Test
    @DisplayName("Deve criar um novo depoimento forçando a flag active como TRUE")
    void shouldCreateTestimonialWithActiveTrue() {
        // Arrange
        TestimonialRequest req = TestimonialRequest.builder().name("Cliente Y").build();
        TestimonialResponse resp = TestimonialResponse.builder().id(1L).name("Cliente Y").build();

        // Usa o ArgumentCaptor para interceptar a entidade Testimonial enviada ao repositório no momento do save
        ArgumentCaptor<Testimonial> captor = ArgumentCaptor.forClass(Testimonial.class);
        when(testRepo.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));
        when(mapper.toResponse(any(Testimonial.class))).thenReturn(resp);

        // Act
        TestimonialResponse result = testimonialService.createTestimonial(req);

        // Assert
        assertNotNull(result);
        assertTrue(captor.getValue().getActive()); // Valida a regra de negócio essencial: todo novo depoimento nasce ativo por padrão (active=true)
        verify(testRepo, times(1)).save(any(Testimonial.class)); // Confirma a persistência do novo registro
    }

    @Test
    @DisplayName("Deve aplicar soft delete mudando a flag active para FALSE em vez de remover fisicamente")
    void shouldApplySoftDeleteSuccessfully() {
        // Arrange
        Long id = 1L;
        Testimonial t = Testimonial.builder().id(id).name("Cliente Z").active(true).build();

        // Configura o repositório para encontrar o registro ativo pelo ID e salvá-lo atualizado
        when(testRepo.findById(id)).thenReturn(Optional.of(t));
        when(testRepo.save(any(Testimonial.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        // Aciona a deleção administrativa do depoimento
        testimonialService.deleteTestimonial(id);

        // Assert
        assertFalse(t.getActive()); // Valida o Soft Delete: a flag active passa a ser false, desativando o depoimento no sistema público
        verify(testRepo, times(1)).save(t); // Confirma que a entidade alterada foi salva no banco
        verify(testRepo, never()).deleteById(any()); // Garante com rigor absoluto que a remoção física (permanente) na tabela nunca ocorreu
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar atualizar um ID inexistente")
    void shouldThrowExceptionWhenUpdatingInexistentId() {
        // Arrange
        Long id = 99L;
        TestimonialRequest req = TestimonialRequest.builder().name("Cliente").build();
        // Simula que o ID informado não foi encontrado no banco (retorna Optional vazio)
        when(testRepo.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        // Valida que a exceção customizada é lançada ao tentar atualizar um depoimento fantasma
        assertThrows(ResourceNotFoundException.class, () -> testimonialService.updateTestimonial(id, req));
        verify(testRepo, never()).save(any()); // Garante estritamente que o método save nunca foi chamado
    }
}

package com.portfolio.service.publicapi.impl;

import com.portfolio.dto.response.TestimonialResponse;
import com.portfolio.entity.Testimonial;
import com.portfolio.mapper.TestimonialMapper;
import com.portfolio.repository.TestimonialRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita a inicialização dos mocks do Mockito integrados ao JUnit 5
class PublicTestimonialServiceImplTest {

    @Mock
    private TestimonialRepository testRepo; // Cria um mock do repositório de depoimentos para simular o banco de dados

    @Mock
    private TestimonialMapper mapper; // Cria um mock do conversor de entidade Testimonial para o DTO TestimonialResponse

    @InjectMocks
    private PublicTestimonialServiceImpl testimonialService; // Instancia o serviço real de depoimentos injetando os mocks acima nele

    @Test
    @DisplayName("Deve retornar apenas depoimentos destacados quando a flag featured for TRUE")
    void shouldReturnOnlyFeaturedTestimonialsWhenFeaturedIsTrue() {
        // Arrange (Configuração do Cenário)
        // Cria uma entidade falsa de Testimonial com active=true e featured=true usando o padrão Builder
        Testimonial t = Testimonial.builder().id(1L).name("Client A").featured(true).active(true).build();
        // Cria o DTO de resposta correspondente que o mapper deve retornar
        TestimonialResponse r = TestimonialResponse.builder().id(1L).name("Client A").featured(true).build();

        // Configura o repositório simulado: quando o método de busca de depoimentos destacados for acionado, retorna a lista com o item 't'
        when(testRepo.findByActiveTrueAndFeaturedTrueOrderBySortOrderAsc()).thenReturn(List.of(t));
        // Configura o mapper para retornar o DTO 'r' ao receber a entidade 't'
        when(mapper.toResponse(t)).thenReturn(r);

        // Act (Execução da Ação)
        // Chama o método do serviço passando 'true' para o parâmetro featured
        List<TestimonialResponse> result = testimonialService.getTestimonials(true);

        // Assert (Validação das Expectativas)
        assertNotNull(result); // Garante que a lista não é nula
        assertEquals(1, result.size()); // Confirma que veio exatamente 1 item
        assertTrue(result.get(0).getFeatured()); // Valida se o depoimento retornado é realmente destacado

        // Verifica se o repositório correto de destacados foi acionado exatamente 1 vez
        verify(testRepo, times(1)).findByActiveTrueAndFeaturedTrueOrderBySortOrderAsc();
        // Garante que o repositório de listagem geral NÃO foi chamado por engano
        verify(testRepo, never()).findByActiveTrueOrderBySortOrderAsc();
    }

    @Test
    @DisplayName("Deve retornar todos os depoimentos ativos quando a flag featured for null ou FALSE")
    void shouldReturnAllActiveTestimonialsWhenFeaturedIsFalseOrNull() {
        // Arrange
        // Cria um depoimento comum com featured=false e active=true
        Testimonial t = Testimonial.builder().id(2L).name("Client B").featured(false).active(true).build();
        TestimonialResponse r = TestimonialResponse.builder().id(2L).name("Client B").featured(false).build();

        // Configura o repositório para retornar a listagem geral ordenada ao buscar sem foco exclusivo em destacados
        when(testRepo.findByActiveTrueOrderBySortOrderAsc()).thenReturn(List.of(t));
        when(mapper.toResponse(t)).thenReturn(r);

        // Act
        // Chama o método do serviço passando 'false'
        List<TestimonialResponse> result = testimonialService.getTestimonials(false);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        // Verifica se o repositório de listagem geral foi acionado
        verify(testRepo, times(1)).findByActiveTrueOrderBySortOrderAsc();
        // Garante que o repositório de destacados nunca foi chamado neste cenário
        verify(testRepo, never()).findByActiveTrueAndFeaturedTrueOrderBySortOrderAsc();
    }
}
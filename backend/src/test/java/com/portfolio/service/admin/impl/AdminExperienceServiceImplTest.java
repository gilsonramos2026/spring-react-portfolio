package com.portfolio.service.admin.impl;

import com.portfolio.dto.request.ExperienceRequest;
import com.portfolio.dto.response.ExperienceResponse;
import com.portfolio.entity.Experience;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.mapper.ExperienceMapper;
import com.portfolio.repository.ExperienceRepository;
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

@ExtendWith(MockitoExtension.class) // Inicializa o Mockito e o JUnit 5 para gerenciar os mocks desta classe de teste
class AdminExperienceServiceImplTest {

    @Mock
    private ExperienceRepository expRepo; // Cria um mock do repositório de experiências para isolar as interações com o banco de dados

    @Mock
    private ExperienceMapper mapper; // Cria um mock do conversor responsável por aplicar dados da requisição e mapear entidades para DTOs

    @InjectMocks
    private AdminExperienceServiceImpl expService; // Instancia o serviço real administrativo injetando os mocks acima nele

    @Test
    @DisplayName("Deve retornar uma lista com todas as experiências profissionais cadastradas")
    void shouldReturnAllExperiencesSuccessfully() {
        // Arrange (Configuração do Cenário)
        Experience e = Experience.builder().id(1L).company("Empresa X").build();
        ExperienceResponse r = ExperienceResponse.builder().id(1L).company("Empresa X").build();

        // Configura o repositório simulado para retornar a listagem completa (visão do painel admin)
        when(expRepo.findAll()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(r);

        // Act (Execução da Ação)
        List<ExperienceResponse> result = expService.getAllExperiences();

        // Assert (Validação das Expectativas)
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(expRepo, times(1)).findAll(); // Confirma que a busca total foi acionada exatamente 1 vez
    }

    @Test
    @DisplayName("Deve criar um novo registro de experiência profissional forçando a flag active como TRUE")
    void shouldCreateExperienceWithActiveTrue() {
        // Arrange
        ExperienceRequest request = ExperienceRequest.builder().company("Empresa Y").build();
        ExperienceResponse response = ExperienceResponse.builder().id(1L).company("Empresa Y").build();

        // Usa o ArgumentCaptor para interceptar o objeto Experience enviado ao repositório no salvamento
        ArgumentCaptor<Experience> captor = ArgumentCaptor.forClass(Experience.class);
        when(expRepo.save(captor.capture())).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toResponse(any(Experience.class))).thenReturn(response);

        // Act
        ExperienceResponse result = expService.createExperience(request);

        // Assert
        assertNotNull(result);
        Experience saved = captor.getValue();
        assertTrue(saved.getActive()); // Valida a regra de negócio essencial: novas experiências nascem ativas por padrão (active=true)
        verify(mapper, times(1)).applyRequest(any(Experience.class), eq(request)); // Garante que o mapper aplicou os dados do request na nova entidade
        verify(expRepo, times(1)).save(any(Experience.class)); // Confirma a persistência do novo registro
    }

    @Test
    @DisplayName("Deve atualizar um registro de experiência profissional existente com sucesso")
    void shouldUpdateExperienceSuccessfully() {
        // Arrange
        Long id = 1L;
        ExperienceRequest request = ExperienceRequest.builder().company("Empresa Z").build();
        Experience e = Experience.builder().id(id).company("Empresa Antiga").build();
        ExperienceResponse response = ExperienceResponse.builder().id(id).company("Empresa Z").build();

        // Configura o repositório para encontrar o registro existente pelo ID e salvá-lo atualizado
        when(expRepo.findById(id)).thenReturn(Optional.of(e));
        when(expRepo.save(e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(response);

        // Act
        ExperienceResponse result = expService.updateExperience(id, request);

        // Assert
        assertNotNull(result);
        assertEquals("Empresa Z", result.getCompany()); // Confirma que a empresa foi alterada no retorno
        verify(mapper, times(1)).applyRequest(e, request); // Garante que as propriedades do request foram aplicadas na entidade encontrada
        verify(expRepo, times(1)).save(e); // Confirma que o save foi executado
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar atualizar um ID que não existe")
    void shouldThrowExceptionWhenUpdatingInexistentId() {
        // Arrange
        Long id = 99L;
        ExperienceRequest request = ExperienceRequest.builder().company("Empresa").build();
        // Simula que o ID informado não foi encontrado no banco (retorna Optional vazio)
        when(expRepo.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        // Valida que a exceção customizada é lançada ao tentar atualizar uma experiência inexistente
        assertThrows(ResourceNotFoundException.class, () -> expService.updateExperience(id, request));
        verify(expRepo, never()).save(any()); // Garante estritamente que o método save nunca foi chamado
    }

    @Test
    @DisplayName("Deve aplicar soft delete alterando a flag active para FALSE em vez de remover fisicamente")
    void shouldApplySoftDeleteSuccessfully() {
        // Arrange
        Long id = 1L;
        Experience e = Experience.builder().id(id).company("Empresa X").active(true).build();

        // Configura o repositório para encontrar o registro ativo
        when(expRepo.findById(id)).thenReturn(Optional.of(e));
        when(expRepo.save(e)).thenReturn(e);

        // Act
        // Aciona o fluxo de deleção administrativa
        expService.deleteExperience(id);

        // Assert
        assertFalse(e.getActive()); // Valida o Soft Delete: a flag active passa a ser false, desativando logicamente a experiência
        verify(expRepo, times(1)).save(e); // Confirma que a entidade alterada foi salva no banco
        verify(expRepo, never()).deleteById(any()); // Garante com rigor que a deleção permanente (física) da tabela nunca ocorreu
    }
}
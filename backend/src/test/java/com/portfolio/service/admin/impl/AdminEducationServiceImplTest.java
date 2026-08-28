package com.portfolio.service.admin.impl;

import com.portfolio.dto.request.EducationRequest;
import com.portfolio.dto.response.EducationResponse;
import com.portfolio.entity.Education;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.mapper.EducationMapper;
import com.portfolio.repository.EducationRepository;
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

@ExtendWith(MockitoExtension.class) // Inicializa o Mockito para gerenciar os mocks nesta classe de teste do JUnit 5
class AdminEducationServiceImplTest {

    @Mock
    private EducationRepository eduRepo; // Cria um mock do repositório de educação para isolar as consultas ao banco de dados

    @Mock
    private EducationMapper mapper; // Cria um mock do conversor responsável por aplicar dados da requisição e mapear entidades para DTOs

    @InjectMocks
    private AdminEducationServiceImpl eduService; // Instancia a classe de serviço real injetando os mocks de repositório e mapper nela

    @Test
    @DisplayName("Deve retornar uma lista com todos os registros de educação cadastrados")
    void shouldReturnAllEducationsSuccessfully() {
        // Arrange (Configuração do Cenário)
        Education e = Education.builder().id(1L).institution("Universidade X").build();
        EducationResponse r = EducationResponse.builder().id(1L).institution("Universidade X").build();

        // Configura o repositório para retornar a listagem geral cadastrada (visão administrativa)
        when(eduRepo.findAll()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(r);

        // Act (Execução da Ação)
        List<EducationResponse> result = eduService.getAllEducations();

        // Assert (Validação das Expectativas)
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(eduRepo, times(1)).findAll(); // Confirma que o método de busca total foi acionado 1 vez
    }

    @Test
    @DisplayName("Deve criar um novo registro de educação forçando a flag active como TRUE")
    void shouldCreateEducationWithActiveTrue() {
        // Arrange
        EducationRequest request = EducationRequest.builder().institution("Universidade Y").build();
        EducationResponse response = EducationResponse.builder().id(1L).institution("Universidade Y").build();

        // Usa o ArgumentCaptor para interceptar a entidade Education que é passada para o repositório no momento do save
        ArgumentCaptor<Education> captor = ArgumentCaptor.forClass(Education.class);
        when(eduRepo.save(captor.capture())).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toResponse(any(Education.class))).thenReturn(response);

        // Act
        EducationResponse result = eduService.createEducation(request);

        // Assert
        assertNotNull(result);
        Education saved = captor.getValue();
        assertTrue(saved.getActive()); // Valida a regra de negócio fundamental: novas formações acadêmicas nascem ativas por padrão (active=true)
        verify(mapper, times(1)).applyRequest(any(Education.class), eq(request)); // Garante que o mapper aplicou os dados do request na entidade
        verify(eduRepo, times(1)).save(any(Education.class)); // Confirma o salvamento no banco
    }

    @Test
    @DisplayName("Deve atualizar um registro de educação existente com sucesso")
    void shouldUpdateEducationSuccessfully() {
        // Arrange
        Long id = 1L;
        EducationRequest request = EducationRequest.builder().institution("Universidade Z").build();
        Education e = Education.builder().id(id).institution("Universidade Antiga").build();
        EducationResponse response = EducationResponse.builder().id(id).institution("Universidade Z").build(); // (Nota: Ajustado visualmente)

        // Configura o repositório para encontrar o registro existente pelo ID e salvá-lo
        when(eduRepo.findById(id)).thenReturn(Optional.of(e));
        when(eduRepo.save(e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(response);

        // Act
        EducationResponse result = eduService.updateEducation(id, request);

        // Assert
        assertNotNull(result);
        assertEquals("Universidade Z", result.getInstitution()); // Confirma que a instituição foi atualizada no retorno
        verify(mapper, times(1)).applyRequest(e, request); // Garante que as alterações do request foram aplicadas à entidade recuperada
        verify(eduRepo, times(1)).save(e); // Confirma a persistência das alterações
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar atualizar um ID que não existe")
    void shouldThrowExceptionWhenUpdatingInexistentId() {
        // Arrange
        Long id = 99L;
        EducationRequest request = EducationRequest.builder().institution("Universidade").build();
        // Simula que o ID informado não existe no banco (retorna Optional vazio)
        when(eduRepo.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        // Valida que a exceção customizada é lançada ao tentar atualizar um registro fantasma
        assertThrows(ResourceNotFoundException.class, () -> eduService.updateEducation(id, request));
        verify(eduRepo, never()).save(any()); // Garante que o método save nunca foi chamado
    }

    @Test
    @DisplayName("Deve aplicar soft delete alterando a flag active para FALSE em vez de remover fisicamente")
    void shouldApplySoftDeleteSuccessfully() {
        // Arrange
        Long id = 1L;
        Education e = Education.builder().id(id).institution("Universidade X").active(true).build();

        // Configura o repositório para encontrar o registro ativo
        when(eduRepo.findById(id)).thenReturn(Optional.of(e));
        when(eduRepo.save(e)).thenReturn(e);

        // Act
        // Aciona a deleção administrativa
        eduService.deleteEducation(id);

        // Assert
        assertFalse(e.getActive()); // Valida o Soft Delete: a flag active se torna false, desativando logicamente a formação
        verify(eduRepo, times(1)).save(e); // Confirma que a entidade alterada foi salva no banco
        verify(eduRepo, never()).deleteById(any()); // Garante com rigor absoluto que o registro NUNCA foi apagado fisicamente da tabela
    }
}
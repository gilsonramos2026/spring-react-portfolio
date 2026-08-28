package com.portfolio.service.publicapi.impl;

import com.portfolio.dto.response.EducationResponse;
import com.portfolio.entity.Education;
import com.portfolio.mapper.EducationMapper;
import com.portfolio.repository.EducationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita a integração do Mockito com o JUnit 5 para inicializar os mocks
class PublicEducationServiceImplTest {

    @Mock
    private EducationRepository eduRepo; // Cria um mock do repositório de educação para isolar o teste do banco de dados relacional

    @Mock
    private EducationMapper mapper; // Cria um mock do conversor responsável por traduzir a entidade Education para o DTO EducationResponse

    @InjectMocks
    private PublicEducationServiceImpl eduService; // Instancia a classe de serviço real e injeta os mocks de repositório e mapper dentro dela

    @Test // Informa ao JUnit que este método representa um cenário de teste executável
    @DisplayName("Deve retornar uma lista de formações acadêmicas ordenadas quando houver registros ativos") // Fornece uma descrição amigável para o relatório de testes
    void shouldReturnEducationResponseListWhenActiveEducationsExist() {
        // Arrange (Configuração do Cenário)
        // Cria uma entidade falsa de Education preenchida via Builder simulando um registro retornado pelo banco
        Education eduMock = Education.builder()
                .id(1L)
                .institution("Universidade Exemplo")
                .degree("Bacharelado")
                .fieldOfStudy("Ciência da Computação")
                .startedAt(LocalDate.of(2022, 1, 1))
                .sortOrder(1)
                .active(true)
                .build();

        // Cria o DTO de resposta correspondente que o mapper deve devolver após a conversão
        EducationResponse responseMock = EducationResponse.builder()
                .id(1L)
                .institution("Universidade Exemplo")
                .degree("Bacharelado")
                .fieldOfStudy("Ciência da Computação")
                .startedAt(LocalDate.of(2022, 1, 1))
                .sortOrder(1)
                .build();

        // Configura o comportamento do repositório simulado: ao chamar o método ajustado, retorna a lista com o eduMock
        when(eduRepo.findByActiveTrueOrderBySortOrderAscAndStartedAtDesc()).thenReturn(List.of(eduMock));

        // Configura o comportamento do mapper: quando converter o eduMock, retorna o responseMock
        when(mapper.toResponse(eduMock)).thenReturn(responseMock);

        // Act (Execução da Ação)
        // Executa o método real de listagem de formações acadêmicas dentro do serviço
        List<EducationResponse> result = eduService.getEducations();

        // Assert (Validação das Expectativas)
        assertNotNull(result); // Garante que a lista retornada não é nula
        assertEquals(1, result.size()); // Confirma se a lista possui exatamente 1 elemento
        assertEquals("Universidade Exemplo", result.get(0).getInstitution()); // Valida se o nome da instituição está correto
        assertEquals("Bacharelado", result.get(0).getDegree()); // Valida se o grau acadêmico está correto

        // Verifica se o método do repositório foi chamado exatamente 1 vez com a query correta
        verify(eduRepo, times(1)).findByActiveTrueOrderBySortOrderAscAndStartedAtDesc();
        // Verifica se o mapper foi acionado exatamente 1 vez para converter a entidade no DTO
        verify(mapper, times(1)).toResponse(eduMock);
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando não houver nenhuma formação acadêmica ativa")
    void shouldReturnEmptyListWhenNoActiveEducationsExist() {
        // Arrange
        // Configura o repositório para retornar uma lista vazia, simulando que nenhuma formação ativa foi encontrada
        when(eduRepo.findByActiveTrueOrderBySortOrderAscAndStartedAtDesc()).thenReturn(Collections.emptyList());

        // Act
        // Executa o método de busca de formações no serviço
        List<EducationResponse> result = eduService.getEducations();

        // Assert
        assertNotNull(result); // Garante que o objeto retornado não é nulo
        assertTrue(result.isEmpty()); // Valida se a lista está vazia

        // Garante que o repositório foi consultado exatamente 1 vez
        verify(eduRepo, times(1)).findByActiveTrueOrderBySortOrderAscAndStartedAtDesc();

        // Garante que o mapper nunca foi acionado, pois se a lista veio vazia do banco, não há o que converter
        verifyNoInteractions(mapper);
    }
}

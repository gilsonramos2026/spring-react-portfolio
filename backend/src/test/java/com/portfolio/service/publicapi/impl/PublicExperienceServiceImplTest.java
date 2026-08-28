package com.portfolio.service.publicapi.impl;

import com.portfolio.dto.response.ExperienceResponse;
import com.portfolio.entity.Experience;
import com.portfolio.mapper.ExperienceMapper;
import com.portfolio.repository.ExperienceRepository;
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

@ExtendWith(MockitoExtension.class) // Habilita a integração do Mockito com o JUnit 5 para inicializar e gerenciar os mocks
class PublicExperienceServiceImplTest {

    @Mock
    private ExperienceRepository expRepo; // Cria um mock do repositório de experiências profissionais para isolar o teste do banco de dados

    @Mock
    private ExperienceMapper mapper; // Cria um mock do conversor responsável por traduzir a entidade Experience para o DTO ExperienceResponse

    @InjectMocks
    private PublicExperienceServiceImpl expService; // Instancia a classe de serviço real e injeta os mocks de repositório e mapper dentro dela

    @Test // Informa ao JUnit que este método representa um cenário de teste executável
    @DisplayName("Deve retornar uma lista de experiências profissionais ordenadas quando houver registros ativos") // Descrição amigável exibida nos relatórios de testes
    void shouldReturnExperienceResponseListWhenActiveExperiencesExist() {
        // Arrange (Configuração do Cenário)
        // Cria uma entidade falsa de Experience preenchida via Builder simulando um registro retornado pelo banco
        Experience expMock = Experience.builder()
                .id(1L)
                .company("Tech Company")
                .role("Desenvolvedor Back-End")
                .description("Desenvolvimento de APIs robustas")
                .startedAt(LocalDate.of(2024, 1, 1))
                .sortOrder(1)
                .active(true)
                .build();

        // Cria o DTO de resposta correspondente que o mapper deve devolver após a conversão
        ExperienceResponse responseMock = ExperienceResponse.builder()
                .id(1L)
                .company("Tech Company")
                .role("Desenvolvedor Back-End")
                .description("Desenvolvimento de APIs robustas")
                .startedAt(LocalDate.of(2024, 1, 1))
                .sortOrder(1)
                .build();

        // Configura o comportamento do repositório simulado: ao chamar o método de busca, retorna a lista com o expMock
        when(expRepo.findByActiveTrueOrderBySortOrderAscAndStartedAtDesc()).thenReturn(List.of(expMock));

        // Configura o comportamento do mapper: quando converter o expMock, retorna o responseMock
        when(mapper.toResponse(expMock)).thenReturn(responseMock);

        // Act (Execução da Ação)
        // Executa o método real de listagem de experiências dentro do serviço
        List<ExperienceResponse> result = expService.getExperiences();

        // Assert (Validação das Expectativas)
        assertNotNull(result); // Garante que a lista retornada não é nula
        assertEquals(1, result.size()); // Confirma se a lista possui exatamente 1 elemento
        assertEquals("Tech Company", result.get(0).getCompany()); // Valida se o nome da empresa está correto
        assertEquals("Desenvolvedor Back-End", result.get(0).getRole()); // Valida se o cargo está correto

        // Verifica se o método do repositório foi chamado exatamente 1 vez com a query esperada
        verify(expRepo, times(1)).findByActiveTrueOrderBySortOrderAscAndStartedAtDesc();
        // Verifica se o mapper foi acionado exatamente 1 vez para converter a entidade no DTO
        verify(mapper, times(1)).toResponse(expMock);
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando não houver nenhuma experiência profissional ativa")
    void shouldReturnEmptyListWhenNoActiveExperiencesExist() {
        // Arrange
        // Configura o repositório para retornar uma lista vazia, simulando que nenhuma experiência ativa foi encontrada no banco
        when(expRepo.findByActiveTrueOrderBySortOrderAscAndStartedAtDesc()).thenReturn(Collections.emptyList());

        // Act
        // Executa o método de busca de experiências no serviço
        List<ExperienceResponse> result = expService.getExperiences();

        // Assert
        assertNotNull(result); // Garante que o objeto retornado não é nulo (deve vir uma lista instanciada vazia)
        assertTrue(result.isEmpty()); // Valida se a lista está vazia

        // Garante que o repositório foi consultado exatamente 1 vez
        verify(expRepo, times(1)).findByActiveTrueOrderBySortOrderAscAndStartedAtDesc();

        // Garante que o mapper nunca foi acionado, pois se a lista veio vazia do banco, não há entidades para converter
        verifyNoInteractions(mapper);
    }
}
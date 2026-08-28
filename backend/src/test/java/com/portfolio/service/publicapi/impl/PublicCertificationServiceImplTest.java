package com.portfolio.service.publicapi.impl;

import com.portfolio.dto.response.CertificationResponse;
import com.portfolio.entity.Certification;
import com.portfolio.mapper.CertificationMapper;
import com.portfolio.repository.CertificationRepository;
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
class PublicCertificationServiceImplTest {

    @Mock
    private CertificationRepository certRepo; // Cria um mock do repositório de certificações para isolar o teste do banco de dados

    @Mock
    private CertificationMapper mapper; // Cria um mock do conversor de entidade Certification para o DTO CertificationResponse

    @InjectMocks
    private PublicCertificationServiceImpl certService; // Instancia o serviço real e injeta os mocks criados acima dentro dele

    @Test // Indica ao JUnit que este método representa um caso de teste executável
    @DisplayName("Deve retornar uma lista de CertificationResponse ordenada quando houver certificações ativas") // Descrição legível exibida nos relatórios de teste
    void shouldReturnCertificationResponseListWhenActiveCertificationsExist() {
        // Arrange (Configuração do Cenário)
        // Cria uma entidade falsa de Certification preenchida via Builder simulando um registro retornado do banco
        Certification certMock = Certification.builder()
                .id(1L)
                .name("Spring Boot Expert")
                .issuer("Pivotal")
                .issuedAt(LocalDate.of(2026, 1, 1))
                .sortOrder(1)
                .active(true)
                .build();

        // Cria o DTO de resposta correspondente que o mapper deve retornar após a conversão
        CertificationResponse responseMock = CertificationResponse.builder()
                .id(1L)
                .name("Spring Boot Expert")
                .issuer("Pivotal")
                .issuedAt(LocalDate.of(2026, 1, 1))
                .sortOrder(1)
                .build();

        // Configura o comportamento simulado do repositório: ao buscar as certificações ativas e ordenadas, retorna uma lista com o certMock
        when(certRepo.findByActiveTrueOrderBySortOrderAscAndIssuedAtDesc()).thenReturn(List.of(certMock));

        // Configura o comportamento do mapper: quando converter o certMock, retorna o responseMock
        when(mapper.toResponse(certMock)).thenReturn(responseMock);

        // Act (Execução da Ação)
        // Executa o método real que estamos testando dentro da classe de serviço
        List<CertificationResponse> result = certService.getCertifications();

        // Assert (Validação das Expectativas)
        assertNotNull(result); // Garante que a lista retornada não é nula
        assertEquals(1, result.size()); // Confirma se a lista possui exatamente 1 elemento
        assertEquals("Spring Boot Expert", result.get(0).getName()); // Valida se o nome da certificação está correto
        assertEquals("Pivotal", result.get(0).getIssuer()); // Valida se o emissor está correto

        // Verifica se o método do repositório foi chamado exatamente 1 vez
        verify(certRepo, times(1)).findByActiveTrueOrderBySortOrderAscAndIssuedAtDesc();
        // Verifica se o mapper foi acionado exatamente 1 vez para converter a entidade
        verify(mapper, times(1)).toResponse(certMock);
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando não houver nenhuma certificação ativa cadastrada")
    void shouldReturnEmptyListWhenNoActiveCertificationsExist() {
        // Arrange
        // Configura o repositório para retornar uma lista vazia, simulando que nenhuma certificação ativa foi encontrada no banco
        when(certRepo.findByActiveTrueOrderBySortOrderAscAndIssuedAtDesc()).thenReturn(Collections.emptyList());

        // Act
        // Executa o método de busca no serviço
        List<CertificationResponse> result = certService.getCertifications();

        // Assert
        assertNotNull(result); // Garante que o retorno não é nulo (mesmo vazio, deve retornar uma lista instanciada)
        assertTrue(result.isEmpty()); // Valida se a lista está vazia

        // Garante que o repositório foi consultado 1 vez
        verify(certRepo, times(1)).findByActiveTrueOrderBySortOrderAscAndIssuedAtDesc();

        // Garante que o mapper nunca foi chamado, já que a lista de entidades veio vazia e não há o que converter
        verifyNoInteractions(mapper);
    }
}
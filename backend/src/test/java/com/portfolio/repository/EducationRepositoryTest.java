package com.portfolio.repository;

import com.portfolio.entity.Education;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class EducationRepositoryTest {

    @Autowired
    private EducationRepository educationRepository;

    @Test
    @DisplayName("Deve retornar apenas registros acadêmicos ativos ordenados por sortOrder ASC e startedAt DESC")
    void shouldFindOnlyActiveEducationsWithProperSorting() {
        // Arrange (Configuração do Cenário com dados para testar a ordenação dupla)
        Education edu1 = Education.builder()
                .institution("Universidade A")
                .degree("Bacharelado")
                .fieldOfStudy("Engenharia")
                .startedAt(LocalDate.of(2020, 1, 1))
                .sortOrder(2) // Maior sortOrder, deve vir por último
                .active(true)
                .build();

        Education edu2 = Education.builder()
                .institution("Universidade B")
                .degree("Pós-Graduação")
                .fieldOfStudy("TI")
                .startedAt(LocalDate.of(2023, 6, 1)) // Mais recente, mesmo sortOrder de edu3
                .sortOrder(1)
                .active(true)
                .build();

        Education edu3 = Education.builder()
                .institution("Universidade C")
                .degree("Certificação")
                .fieldOfStudy("Cloud")
                .startedAt(LocalDate.of(2022, 1, 1)) // Mais antiga, mesmo sortOrder de edu2
                .sortOrder(1)
                .active(true)
                .build();

        Education eduInactive = Education.builder()
                .institution("Universidade Inativa")
                .degree("Nenhum")
                .fieldOfStudy("Nenhum")
                .startedAt(LocalDate.of(2019, 1, 1))
                .sortOrder(0)
                .active(false) // Deve ser completamente ignorada pelo filtro active = true
                .build();

        educationRepository.saveAll(List.of(edu1, edu2, edu3, eduInactive));

        // Act (Execução da consulta customizada)
        List<Education> result = educationRepository.findByActiveTrueOrderBySortOrderAscAndStartedAtDesc();

        // Assert (Validação rigorosa do filtro e dos critérios de ordenação)
        assertNotNull(result);
        assertEquals(3, result.size()); // Garante o descarte do registro inativo

        // Validação da ordenação primária (sortOrder ASC) e secundária (startedAt DESC)
        assertEquals("Universidade B", result.get(0).getInstitution()); // sortOrder = 1, iniciado em 2023 (mais recente)
        assertEquals("Universidade C", result.get(1).getInstitution()); // sortOrder = 1, iniciado em 2022 (mais antigo)
        assertEquals("Universidade A", result.get(2).getInstitution()); // sortOrder = 2
    }
}

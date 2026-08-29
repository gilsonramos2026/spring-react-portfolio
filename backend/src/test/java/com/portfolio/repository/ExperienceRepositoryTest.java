package com.portfolio.repository;

import com.portfolio.entity.Experience;
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
class ExperienceRepositoryTest {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Test
    @DisplayName("Deve retornar apenas experiências profissionais ativas ordenadas por sortOrder ASC e startedAt DESC")
    void shouldFindOnlyActiveExperiencesWithProperSorting() {
        // Arrange
        Experience exp1 = Experience.builder()
                .company("Empresa A")
                .role("Desenvolvedor")
                .startedAt(LocalDate.of(2021, 1, 1))
                .sortOrder(2) // Maior sortOrder, deve vir por último
                .active(true)
                .build();

        Experience exp2 = Experience.builder()
                .company("Empresa B")
                .role("Tech Lead")
                .startedAt(LocalDate.of(2024, 6, 1)) // Mais recente, mesmo sortOrder de exp3
                .sortOrder(1)
                .active(true)
                .build();

        Experience exp3 = Experience.builder()
                .company("Empresa C")
                .role("Senior Dev")
                .startedAt(LocalDate.of(2023, 1, 1)) // Mais antiga, mesmo sortOrder de exp2
                .sortOrder(1)
                .active(true)
                .build();

        Experience expInactive = Experience.builder()
                .company("Empresa Inativa")
                .role("Nenhum")
                .startedAt(LocalDate.of(2020, 1, 1))
                .sortOrder(0)
                .active(false) // Deve ser completamente ignorada
                .build();

        experienceRepository.saveAll(List.of(exp1, exp2, exp3, expInactive));

        // Act
        List<Experience> result = experienceRepository.findByActiveTrueOrderBySortOrderAscAndStartedAtDesc();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size()); // Garante o descarte da inativa

        // Validação da ordenação primária (sortOrder ASC) e secundária (startedAt DESC)
        assertEquals("Empresa B", result.get(0).getCompany()); // sortOrder = 1, iniciado em 2024 (mais recente)
        assertEquals("Empresa C", result.get(1).getCompany()); // sortOrder = 1, iniciado em 2023 (mais antigo)
        assertEquals("Empresa A", result.get(2).getCompany()); // sortOrder = 2
    }
}

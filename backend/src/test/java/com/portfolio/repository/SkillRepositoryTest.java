package com.portfolio.repository;

import com.portfolio.entity.Skill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class SkillRepositoryTest {

    @Autowired
    private SkillRepository skillRepository;

    @BeforeEach
    void setUp() {
        // Garante o isolamento completo limpando qualquer dado residual antes de cada teste
        skillRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve retornar apenas habilidades ativas ordenadas por sortOrder ASC e name ASC")
    void shouldFindOnlyActiveSkillsWithProperSorting() {
        // Arrange - Configuração do cenário para testar a ordenação alfabética em caso de empate de sortOrder
        Skill s1 = Skill.builder()
                .name("React")
                .category("Frontend")
                .proficiency(85)
                .sortOrder(2) // Maior sortOrder, deve vir por último
                .active(true)
                .build();

        Skill s2 = Skill.builder()
                .name("Java")
                .category("Backend")
                .proficiency(90)
                .sortOrder(1) // Mesmo sortOrder de s3, mas começa com 'J' (vem primeiro no alfabeto)
                .active(true)
                .build();

        Skill s3 = Skill.builder()
                .name("Python")
                .category("Backend")
                .proficiency(80)
                .sortOrder(1) // Mesmo sortOrder de s2, mas começa com 'P' (vem depois de 'J')
                .active(true)
                .build();

        Skill sInactive = Skill.builder()
                .name("Docker Inativo")
                .category("DevOps")
                .proficiency(70)
                .sortOrder(0)
                .active(false) // Deve ser completamente ignorada
                .build();

        skillRepository.saveAll(List.of(s1, s2, s3, sInactive));

        // Act
        List<Skill> result = skillRepository.findByActiveTrueOrderBySortOrderAscAndNameAsc();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size()); // Garante o descarte da inativa

        // Validação da ordenação dupla: primeiro sortOrder ASC, depois name ASC (Alfabética)
        assertEquals("Java", result.get(0).getName());   // sortOrder = 1, nome começa com 'J'
        assertEquals("Python", result.get(1).getName()); // sortOrder = 1, nome começa com 'P'
        assertEquals("React", result.get(2).getName());  // sortOrder = 2
    }
}

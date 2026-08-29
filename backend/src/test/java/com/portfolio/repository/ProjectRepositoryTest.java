package com.portfolio.repository;

import com.portfolio.entity.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
        // Garante o isolamento completo limpando qualquer dado residual antes de cada teste
        projectRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve retornar todos os projetos ativos ordenados por sortOrder ASC e createdAt DESC via JPQL")
    void shouldFindAllActiveProjectsWithProperSorting() {
        // Arrange - CALIBRADO: p1 recebe a data mais recente e p2 a mais antiga, alinhando com a concorrência de ID do banco
        Project p1 = Project.builder()
                .title("Projeto Novo")
                .slug("projeto-novo")
                .shortDesc("Descrição curta")
                .sortOrder(1)
                .active(true)
                .createdAt(LocalDate.now()) // Mais recente, vem primeiro no DESC
                .build();

        Project p2 = Project.builder()
                .title("Projeto Antigo")
                .slug("projeto-antigo")
                .shortDesc("Descrição curta")
                .sortOrder(1) // Mesmo sortOrder de p1, mas criado antes
                .active(true)
                .createdAt(LocalDate.now().minusDays(1)) // Mais antigo
                .build();

        Project p3 = Project.builder()
                .title("Projeto Maior Ordem")
                .slug("projeto-maior-ordem")
                .shortDesc("Descrição curta")
                .sortOrder(2) // Maior sortOrder, deve vir por último
                .active(true)
                .createdAt(LocalDate.now().plusDays(1))
                .build();

        Project pInactive = Project.builder()
                .title("Projeto Inativo")
                .slug("projeto-inativo")
                .shortDesc("Descrição curta")
                .sortOrder(0)
                .active(false) // Deve ser ignorado
                .build();

        projectRepository.saveAll(List.of(p1, p2, p3, pInactive));

        // Act
        List<Project> result = projectRepository.findByActiveTrueOrderBySortOrderAscAndCreatedAtDesc();

        // Assert
        assertEquals(3, result.size());
        assertEquals("Projeto Novo", result.get(0).getTitle());   // sortOrder = 1, mais recente
        assertEquals("Projeto Antigo", result.get(1).getTitle());  // sortOrder = 1, mais antigo
        assertEquals("Projeto Maior Ordem", result.get(2).getTitle()); // sortOrder = 2
    }


    @Test
    @DisplayName("Deve filtrar apenas os projetos ativos que estão destacados (featured = true) por sortOrder ASC")
    void shouldFindOnlyActiveAndFeaturedProjects() {
        // Arrange
        Project pFeatured1 = Project.builder().title("P1").slug("p-1").shortDesc("Desc").sortOrder(2).active(true).featured(true).build();
        Project pFeatured2 = Project.builder().title("P2").slug("p-2").shortDesc("Desc").sortOrder(1).active(true).featured(true).build();
        Project pCommon = Project.builder().title("P3").slug("p-3").shortDesc("Desc").sortOrder(0).active(true).featured(false).build();

        projectRepository.saveAll(List.of(pFeatured1, pFeatured2, pCommon));

        // Act
        List<Project> result = projectRepository.findByActiveTrueAndFeaturedTrueOrderBySortOrderAsc();

        // Assert
        assertEquals(2, result.size());
        assertEquals("P2", result.get(0).getTitle()); // sortOrder = 1 vem antes do sortOrder = 2
        assertEquals("P1", result.get(1).getTitle());
    }

    @Test
    @DisplayName("Deve buscar um projeto ativo pelo slug de texto único com sucesso")
    void shouldFindActiveProjectBySlug() {
        // Arrange
        String targetSlug = "api-portfolio-spring";
        Project p = Project.builder()
                .title("API Spring")
                .slug(targetSlug)
                .shortDesc("Desc")
                .active(true)
                .build();
        projectRepository.save(p);

        // Act
        Optional<Project> result = projectRepository.findBySlugAndActiveTrue(targetSlug);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("API Spring", result.get().getTitle());
    }

    @Test
    @DisplayName("Deve retornar Optional vazio ao buscar um slug pertencente a um projeto inativo")
    void shouldReturnEmptyOptionalWhenProjectWithSlugIsInactive() {
        // Arrange
        String targetSlug = "api-portfolio-inativa";
        Project p = Project.builder()
                .title("API Inativa")
                .slug(targetSlug)
                .shortDesc("Desc")
                .active(false) // Inativo
                .build();
        projectRepository.save(p);

        // Act
        Optional<Project> result = projectRepository.findBySlugAndActiveTrue(targetSlug);

        // Assert
        assertTrue(result.isEmpty());
    }
}

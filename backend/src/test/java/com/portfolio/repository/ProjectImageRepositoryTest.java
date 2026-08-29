package com.portfolio.repository;

import com.portfolio.entity.Project;
import com.portfolio.entity.ProjectImage;
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
class ProjectImageRepositoryTest {

    @Autowired
    private ProjectImageRepository projectImageRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private Project targetProject;

    @BeforeEach
    void setUp() {
        // Limpa dados residuais para garantir o isolamento completo do teste
        projectImageRepository.deleteAll();
        projectRepository.deleteAll();

        // CORRIGIDO: Inclusão do campo short_desc obrigatório para satisfazer a constraint NOT NULL do banco
        Project project = Project.builder()
                .title("E-commerce Core")
                .slug("e-commerce-core")
                .shortDesc("Uma descrição curta obrigatória para o projeto.")
                .active(true)
                .build();
        targetProject = projectRepository.save(project);
    }

    @Test
    @DisplayName("Deve listar as imagens de um projeto específico ordenadas pelo sortOrder de forma ascendente")
    void shouldFindImagesByProjectIdOrderedBySortOrderAsc() {
        // Arrange
        ProjectImage img1 = ProjectImage.builder().project(targetProject).url("/img2.jpg").sortOrder(2).build();
        ProjectImage img2 = ProjectImage.builder().project(targetProject).url("/img1.jpg").sortOrder(1).build();

        projectImageRepository.saveAll(List.of(img1, img2));

        // Act
        List<ProjectImage> result = projectImageRepository.findByProjectIdOrderBySortOrderAsc(targetProject.getId());

        // Assert
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getSortOrder()); // O sortOrder 1 deve vir antes do 2
        assertEquals("/img1.jpg", result.get(0).getUrl());
    }

    @Test
    @DisplayName("Deve validar a propriedade (ownership) e retornar true apenas se a imagem pertencer ao ID do projeto correto")
    void shouldVerifyExistsByIdAndProjectIdCorrectly() {
        // Arrange
        ProjectImage img = ProjectImage.builder().project(targetProject).url("/img.jpg").sortOrder(0).build();
        ProjectImage savedImg = projectImageRepository.save(img);

        // Act & Assert
        assertTrue(projectImageRepository.existsByIdAndProjectId(savedImg.getId(), targetProject.getId()));
        assertFalse(projectImageRepository.existsByIdAndProjectId(savedImg.getId(), 999L)); // ID de projeto fantasma
    }

    @Test
    @DisplayName("Deve calcular dinamicamente o próximo sortOrder incremental (Max + 1) e retornar 0 se a lista estiver vazia")
    void shouldCalculateNextSortOrderSuccessfully() {
        Long projectId = targetProject.getId();

        // Cenário 1: Tabela vazia deve retornar 0 (COALESCE transformando NULL em -1, e somando 1)
        int orderEmpty = projectImageRepository.nextSortOrder(projectId);
        assertEquals(0, orderEmpty);

        // Cenário 2: Adicionando imagem com sortOrder = 5, o próximo deve ser 6
        ProjectImage img = ProjectImage.builder().project(targetProject).url("/screenshot.jpg").sortOrder(5).build();
        projectImageRepository.save(img);

        int orderPopulated = projectImageRepository.nextSortOrder(projectId);
        assertEquals(6, orderPopulated);
    }

    @Test
    @DisplayName("Deve retornar a quantidade exata de imagens atreladas a um projeto")
    void shouldCountImagesByProjectIdCorrectly() {
        // Arrange
        ProjectImage img1 = ProjectImage.builder().project(targetProject).url("/thumb.jpg").sortOrder(0).build();
        ProjectImage img2 = ProjectImage.builder().project(targetProject).url("/banner.jpg").sortOrder(1).build();

        projectImageRepository.saveAll(List.of(img1, img2));

        // Act
        long count = projectImageRepository.countByProjectId(targetProject.getId());

        // Assert
        assertEquals(2L, count);
    }
}

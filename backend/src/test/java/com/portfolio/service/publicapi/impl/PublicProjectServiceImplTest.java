package com.portfolio.service.publicapi.impl;

import com.portfolio.dto.response.ProjectResponse;
import com.portfolio.entity.Project;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.mapper.ProjectMapper;
import com.portfolio.repository.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita o Mockito e o JUnit 5 para inicializar e gerenciar os mocks da classe
class PublicProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepo; // Cria um mock do repositório de projetos para isolar as consultas ao banco

    @Mock
    private ProjectMapper mapper; // Cria um mock do conversor responsável por traduzir a entidade Project para o DTO ProjectResponse

    @InjectMocks
    private PublicProjectServiceImpl projectService; // Instancia o serviço real e injeta os mocks de repositório e mapper dentro dele

    @Test // Informa ao JUnit que este é um método de teste executável
    @DisplayName("Deve retornar apenas projetos destacados quando a flag featured for TRUE") // Descrição amigável do comportamento testado
    void shouldReturnOnlyFeaturedProjectsWhenFeaturedIsTrue() {
        // Arrange (Configuração do Cenário)
        // Cria uma entidade falsa de Project com active=true e featured=true usando o Builder
        Project p = Project.builder().id(1L).title("Projeto 1").featured(true).active(true).build();
        // Cria o DTO de resposta correspondente que o mapper deve retornar
        ProjectResponse r = ProjectResponse.builder().id(1L).title("Projeto 1").featured(true).build();

        // Configura o repositório simulado: quando o método de busca de destacados for chamado, retorna a lista com o projeto 'p'
        when(projectRepo.findByActiveTrueAndFeaturedTrueOrderBySortOrderAsc()).thenReturn(List.of(p));
        // Configura o mapper para retornar o DTO 'r' ao receber a entidade 'p'
        when(mapper.toResponse(p)).thenReturn(r);

        // Act (Execução da Ação)
        // Chama o método do serviço passando 'true' para o parâmetro featured
        List<ProjectResponse> result = projectService.getProjects(true);

        // Assert (Validação das Expectativas)
        assertNotNull(result); // Garante que a lista não é nula
        assertEquals(1, result.size()); // Confirma que veio apenas 1 item
        assertTrue(result.get(0).getFeatured()); // Valida se o projeto retornado é realmente destacado

        // Verifica se o repositório correto de destacados foi acionado exatamente 1 vez
        verify(projectRepo, times(1)).findByActiveTrueAndFeaturedTrueOrderBySortOrderAsc();
        // Garante que o método de listagem geral NÃO foi chamado por engano
        verify(projectRepo, never()).findByActiveTrueOrderBySortOrderAscAndCreatedAtDesc();
    }

    @Test
    @DisplayName("Deve retornar todos os projetos ativos quando a flag featured for null ou FALSE")
    void shouldReturnAllActiveProjectsWhenFeaturedIsFalseOrNull() {
        // Arrange
        // Cria um projeto comum (não destacado, featured=false)
        Project p = Project.builder().id(2L).title("Projeto 2").featured(false).active(true).build();
        ProjectResponse r = ProjectResponse.builder().id(2L).title("Projeto 2").featured(false).build();

        // Configura o repositório para retornar a listagem geral e ordenada ao buscar sem foco exclusivo em destacados
        when(projectRepo.findByActiveTrueOrderBySortOrderAscAndCreatedAtDesc()).thenReturn(List.of(p));
        when(mapper.toResponse(p)).thenReturn(r);

        // Act
        // Chama o método passando 'false'
        List<ProjectResponse> result = projectService.getProjects(false);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        // Verifica se o repositório de listagem geral foi acionado
        verify(projectRepo, times(1)).findByActiveTrueOrderBySortOrderAscAndCreatedAtDesc();
        // Garante que o repositório de destacados nunca foi chamado neste cenário
        verify(projectRepo, never()).findByActiveTrueAndFeaturedTrueOrderBySortOrderAsc();
    }

    @Test
    @DisplayName("Deve retornar ProjectResponse com sucesso ao buscar por um slug existente")
    void shouldReturnProjectWhenSlugExists() {
        // Arrange
        String targetSlug = "meu-projeto-incrivel";
        // Cria o projeto simulado com o slug específico
        Project p = Project.builder().id(1L).title("Projeto").slug(targetSlug).active(true).build();
        ProjectResponse r = ProjectResponse.builder().id(1L).title("Projeto").slug(targetSlug).build();

        // Configura o repositório para retornar o Optional preenchido quando buscar por esse slug
        when(projectRepo.findBySlugAndActiveTrue(targetSlug)).thenReturn(Optional.of(p));
        when(mapper.toResponse(p)).thenReturn(r);

        // Act
        // Executa a busca por slug no serviço
        ProjectResponse result = projectService.getProjectBySlug(targetSlug);

        // Assert
        assertNotNull(result);
        assertEquals(targetSlug, result.getSlug()); // Valida se o slug do resultado confere

        // Verifica se o repositório foi consultado 1 vez com o slug correto
        verify(projectRepo, times(1)).findBySlugAndActiveTrue(targetSlug);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao buscar por um slug inexistente")
    void shouldThrowExceptionWhenSlugDoesNotExist() {
        // Arrange
        String targetSlug = "slug-fantasma";
        // Configura o repositório para retornar um Optional vazio, simulando que o projeto não foi encontrado
        when(projectRepo.findBySlugAndActiveTrue(targetSlug)).thenReturn(Optional.empty());

        // Act & Assert
        // Valida que ao buscar pelo slug fantasma, a exceção ResourceNotFoundException é lançada
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            projectService.getProjectBySlug(targetSlug);
        });

        // Valida se a mensagem de erro gerada pela exceção está correta
        assertEquals("Projeto com o slug 'slug-fantasma' não foi encontrado", exception.getMessage());
        // Verifica se o repositório foi consultado 1 vez
        verify(projectRepo, times(1)).findBySlugAndActiveTrue(targetSlug);
        // Garante que o mapper nunca foi chamado, já que a entidade não existia para ser convertida
        verifyNoInteractions(mapper);
    }
}
package com.portfolio.controller.publicapi;

import com.portfolio.dto.response.ProjectResponse;
import com.portfolio.security.AdminAuthFilter;
import com.portfolio.security.RateLimitFilter;
import com.portfolio.security.SecurityErrorResolver;
import com.portfolio.service.publicapi.PublicProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PublicProjectController.class)
@AutoConfigureMockMvc(addFilters = false) // Isola o escopo do teste desativando filtros de rede globais
class PublicProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicProjectService projectService;

    // Beans de infraestrutura de rede obrigatórios para subir o ApplicationContext do Spring
    @MockBean
    private SecurityErrorResolver securityErrorResolver;

    @MockBean
    private AdminAuthFilter adminAuthFilter;

    @MockBean
    private RateLimitFilter rateLimitFilter;

    @Test
    @DisplayName("Deve retornar status 200 e a lista de projetos quando a rota raiz for chamada sem filtros")
    void shouldListAllPublicProjectsSuccessfully() throws Exception {
        // Arrange
        ProjectResponse resp = ProjectResponse.builder()
                .id(1L)
                .title("E-commerce API")
                .slug("e-commerce-api")
                .featured(false)
                .build();
        when(projectService.getProjects(null)).thenReturn(List.of(resp));

        // Act & Assert
        mockMvc.perform(get("/public/projects"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("E-commerce API"))
                .andExpect(jsonPath("$[0].slug").value("e-commerce-api"));

        verify(projectService, times(1)).getProjects(null);
    }

    @Test
    @DisplayName("Deve retornar status 200 e filtrar por destaque quando o parâmetro featured for TRUE")
    void shouldListFeaturedProjectsSuccessfully() throws Exception {
        // Arrange
        ProjectResponse resp = ProjectResponse.builder()
                .id(2L)
                .title("Portfolio Core")
                .slug("portfolio-core")
                .featured(true)
                .build();
        when(projectService.getProjects(true)).thenReturn(List.of(resp));

        // Act & Assert
        mockMvc.perform(get("/public/projects")
                        .param("featured", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2L))
                .andExpect(jsonPath("$[0].featured").value(true));

        verify(projectService, times(1)).getProjects(true);
    }

    @Test
    @DisplayName("Deve retornar status 200 e um objeto JSON único ao buscar um projeto válido pelo slug")
    void shouldGetProjectBySlugSuccessfully() throws Exception {
        // Arrange
        String targetSlug = "sistema-de-vendas";
        ProjectResponse resp = ProjectResponse.builder()
                .id(5L)
                .title("Sistema de Vendas")
                .slug(targetSlug)
                .build();
        when(projectService.getProjectBySlug(targetSlug)).thenReturn(resp);

        // Act & Assert
        mockMvc.perform(get("/public/projects/{slug}", targetSlug))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L)) // Resposta em objeto único (utiliza $. direto na raiz)
                .andExpect(jsonPath("$.title").value("Sistema de Vendas"))
                .andExpect(jsonPath("$.slug").value(targetSlug));

        verify(projectService, times(1)).getProjectBySlug(targetSlug);
    }
}

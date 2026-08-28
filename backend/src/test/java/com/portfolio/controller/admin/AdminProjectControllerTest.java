package com.portfolio.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.dto.request.ProjectRequest;
import com.portfolio.dto.response.ProjectImageResponse;
import com.portfolio.dto.response.ProjectResponse;
import com.portfolio.security.AdminAuthFilter;
import com.portfolio.security.RateLimitFilter;
import com.portfolio.security.SecurityErrorResolver;
import com.portfolio.service.admin.AdminProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminProjectController.class)
@AutoConfigureMockMvc(addFilters = false) // Desabilita os filtros de segurança globais para o teste focar estritamente nas rotas
class AdminProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminProjectService projectService;

    // Beans de infraestrutura de rede obrigatórios para carregar o ApplicationContext sem vazamento de dependências
    @MockBean
    private SecurityErrorResolver securityErrorResolver;

    @MockBean
    private AdminAuthFilter adminAuthFilter;

    @MockBean
    private RateLimitFilter rateLimitFilter;

    @Test
    @DisplayName("Deve retornar status 200 e a lista de todos os projetos cadastrados ao efetuar GET")
    void shouldListAllProjectsSuccessfully() throws Exception {
        // Arrange
        ProjectResponse resp = ProjectResponse.builder().id(1L).title("Meu E-commerce").slug("meu-e-commerce").build();
        when(projectService.getAllProjects()).thenReturn(List.of(resp));

        // Act & Assert
        mockMvc.perform(get("/admin/projects"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Meu E-commerce"));

        verify(projectService, times(1)).getAllProjects();
    }

    @Test
    @DisplayName("Deve retornar status 201 e o payload mapeado ao cadastrar um projeto válido via POST")
    void shouldCreateProjectSuccessfully() throws Exception {
        // Arrange - Fornece os campos do DTO para passar pelas validações @Valid sem dar Bad Request
        ProjectRequest req = ProjectRequest.builder().title("Novo Projeto").shortDesc("Uma descrição curta").build();
        ProjectResponse resp = ProjectResponse.builder().id(1L).title("Novo Projeto").build();

        when(projectService.createProject(any(ProjectRequest.class))).thenReturn(resp);

        // Act & Assert
        mockMvc.perform(post("/admin/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Novo Projeto"));

        verify(projectService, times(1)).createProject(any(ProjectRequest.class));
    }

    @Test
    @DisplayName("Deve retornar status 200 ao atualizar um projeto existente com dados válidos")
    void shouldUpdateProjectSuccessfully() throws Exception {
        // Arrange
        Long targetId = 1L;
        ProjectRequest req = ProjectRequest.builder().title("Projeto Alterado").shortDesc("Nova descrição").build();
        ProjectResponse resp = ProjectResponse.builder().id(targetId).title("Projeto Alterado").build();

        when(projectService.updateProject(eq(targetId), any(ProjectRequest.class))).thenReturn(resp);

        // Act & Assert
        mockMvc.perform(put("/admin/projects/{id}", targetId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(targetId))
                .andExpect(jsonPath("$.title").value("Projeto Alterado"));

        verify(projectService, times(1)).updateProject(eq(targetId), any(ProjectRequest.class));
    }

    @Test
    @DisplayName("Deve retornar status 24 no-content ao efetuar exclusão lógica de um projeto")
    void shouldDeleteProjectSuccessfully() throws Exception {
        // Arrange
        Long targetId = 1L;
        doNothing().when(projectService).deleteProject(targetId);

        // Act & Assert
        mockMvc.perform(delete("/admin/projects/{id}", targetId))
                .andExpect(status().isNoContent());

        verify(projectService, times(1)).deleteProject(targetId);
    }

    @Test
    @DisplayName("Deve capturar o binário multipart e o parâmetro altText e retornar status 201 ao vincular imagem")
    void shouldUploadProjectImageSuccessfully() throws Exception {
        // Arrange
        Long projectId = 1L;
        MockMultipartFile fileMock = new MockMultipartFile(
                "file", "screenshot.jpg", MediaType.IMAGE_JPEG_VALUE, "mock-bytes".getBytes()
        );
        ProjectImageResponse resp = ProjectImageResponse.builder().id(10L).url("/media/screenshot.jpg").altText("Tela Inicial").build();

        when(projectService.addProjectImage(eq(projectId), any(), eq("Tela Inicial"))).thenReturn(resp);

        // Act & Assert
        mockMvc.perform(multipart("/admin/projects/{id}/images", projectId)
                        .file(fileMock)
                        .param("altText", "Tela Inicial"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.url").value("/media/screenshot.jpg"))
                .andExpect(jsonPath("$.altText").value("Tela Inicial"));

        verify(projectService, times(1)).addProjectImage(eq(projectId), any(), eq("Tela Inicial"));
    }

    @Test
    @DisplayName("Deve retornar status 24 no-content ao remover uma imagem específica vinculada ao projeto")
    void shouldDeleteProjectImageSuccessfully() throws Exception {
        // Arrange
        Long projectId = 1L;
        Long imageId = 10L;
        doNothing().when(projectService).deleteProjectImage(projectId, imageId);

        // Act & Assert
        mockMvc.perform(delete("/admin/projects/{id}/images/{imgId}", projectId, imageId))
                .andExpect(status().isNoContent());

        verify(projectService, times(1)).deleteProjectImage(projectId, imageId);
    }

    @Test
    @DisplayName("Deve aceitar a lista ordenada de IDs via corpo JSON e retornar status 24 no-content ao reordenar portfólio")
    void shouldReorderProjectImagesSuccessfully() throws Exception {
        // Arrange
        Long projectId = 1L;
        List<Long> orderedIds = List.of(30L, 10L, 20L); // Simula a nova sequência posicional gerada pelo React DnD
        doNothing().when(projectService).reorderProjectImages(eq(projectId), eq(orderedIds));

        // Act & Assert
        mockMvc.perform(put("/admin/projects/{id}/images/reorder", projectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderedIds)))
                .andExpect(status().isNoContent());

        verify(projectService, times(1)).reorderProjectImages(eq(projectId), eq(orderedIds));
    }
}

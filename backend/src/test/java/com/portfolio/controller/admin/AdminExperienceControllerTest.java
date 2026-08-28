package com.portfolio.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.dto.request.ExperienceRequest;
import com.portfolio.dto.response.ExperienceResponse;
import com.portfolio.security.AdminAuthFilter;
import com.portfolio.security.RateLimitFilter;
import com.portfolio.security.SecurityErrorResolver;
import com.portfolio.service.admin.AdminExperienceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminExperienceController.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminExperienceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminExperienceService expService;

    @MockBean
    private SecurityErrorResolver securityErrorResolver;

    @MockBean
    private AdminAuthFilter adminAuthFilter;

    @MockBean
    private RateLimitFilter rateLimitFilter;

    @Test
    @DisplayName("Deve retornar status 200 e a lista de experiências profissionais ao efetuar GET")
    void shouldListExperiencesSuccessfully() throws Exception {
        // Arrange
        ExperienceResponse resp = ExperienceResponse.builder()
                .id(1L)
                .company("Big Tech X")
                .role("Senior Software Engineer")
                .build();
        when(expService.getAllExperiences()).thenReturn(List.of(resp));

        // Act & Assert - CORRIGIDO: Modificado de $.id para $[0].id para ler elementos de um array JSON
        mockMvc.perform(get("/admin/experiences"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].company").value("Big Tech X"))
                .andExpect(jsonPath("$[0].role").value("Senior Software Engineer"));

        verify(expService, times(1)).getAllExperiences();
    }

    @Test
    @DisplayName("Deve retornar status 201 e o payload mapeado ao cadastrar nova experiência válida")
    void shouldCreateExperienceSuccessfully() throws Exception {
        // Arrange
        ExperienceRequest req = ExperienceRequest.builder()
                .company("Startup Y")
                .role("Full Stack Developer")
                .startedAt(LocalDate.now())
                .build();
        ExperienceResponse resp = ExperienceResponse.builder()
                .id(2L)
                .company("Startup Y")
                .role("Full Stack Developer")
                .build();

        when(expService.createExperience(any(ExperienceRequest.class))).thenReturn(resp);

        // Act & Assert
        mockMvc.perform(post("/admin/experiences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.company").value("Startup Y"))
                .andExpect(jsonPath("$.role").value("Full Stack Developer"));

        verify(expService, times(1)).createExperience(any(ExperienceRequest.class));
    }

    @Test
    @DisplayName("Deve retornar status 200 ao atualizar uma experiência profissional existente com dados válidos")
    void shouldUpdateExperienceSuccessfully() throws Exception {
        // Arrange
        Long targetId = 1L;
        ExperienceRequest req = ExperienceRequest.builder()
                .company("Empresa Evoluída")
                .role("Tech Lead")
                .startedAt(LocalDate.now())
                .build();
        ExperienceResponse resp = ExperienceResponse.builder()
                .id(targetId)
                .company("Empresa Evoluída")
                .role("Tech Lead")
                .build();

        when(expService.updateExperience(eq(targetId), any(ExperienceRequest.class))).thenReturn(resp);

        // Act & Assert
        mockMvc.perform(put("/admin/experiences/{id}", targetId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(targetId))
                .andExpect(jsonPath("$.company").value("Empresa Evoluída"))
                .andExpect(jsonPath("$.role").value("Tech Lead"));

        verify(expService, times(1)).updateExperience(eq(targetId), any(ExperienceRequest.class));
    }

    @Test
    @DisplayName("Deve retornar status 204 no-content ao remover uma experiência profissional por ID")
    void shouldDeleteExperienceSuccessfully() throws Exception {
        // Arrange
        Long targetId = 1L;
        doNothing().when(expService).deleteExperience(targetId);

        // Act & Assert
        mockMvc.perform(delete("/admin/experiences/{id}", targetId))
                .andExpect(status().isNoContent());

        verify(expService, times(1)).deleteExperience(targetId);
    }
}

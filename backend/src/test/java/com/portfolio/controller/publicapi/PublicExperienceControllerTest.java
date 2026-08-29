package com.portfolio.controller.publicapi;

import com.portfolio.dto.response.ExperienceResponse;
import com.portfolio.security.AdminAuthFilter;
import com.portfolio.security.SecurityErrorResolver;
import com.portfolio.service.publicapi.PublicExperienceService;
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

@WebMvcTest(PublicExperienceController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PublicExperienceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicExperienceService expService;

    // Beans de infraestrutura de rede e segurança obrigatórios para subir o ApplicationContext do Spring
    @MockBean
    private SecurityErrorResolver securityErrorResolver;

    @MockBean
    private AdminAuthFilter adminAuthFilter;

    @Test
    @DisplayName("Deve retornar status 200 e a listagem pública de experiências profissionais em formato de array JSON")
    void shouldReturnPublicExperiencesSuccessfully() throws Exception {
        // Arrange
        ExperienceResponse resp = ExperienceResponse.builder()
                .id(1L)
                .company("Empresa de Tecnologia")
                .role("Desenvolvedor Back-end") // CORRIGIDO: "Back-end" para coincidir com a resposta real do mock
                .location("Remoto")
                .type("CLT")
                .build();

        when(expService.getExperiences()).thenReturn(List.of(resp));

        // Act & Assert
        mockMvc.perform(get("/public/experiences"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].company").value("Empresa de Tecnologia"))
                .andExpect(jsonPath("$[0].role").value("Desenvolvedor Back-end")) // CORRIGIDO: Ajustado para minúscula
                .andExpect(jsonPath("$[0].location").value("Remoto"))
                .andExpect(jsonPath("$[0].type").value("CLT"));

        verify(expService, times(1)).getExperiences();
    }
}
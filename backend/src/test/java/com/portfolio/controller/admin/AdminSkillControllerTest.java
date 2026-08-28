package com.portfolio.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.dto.request.SkillRequest;
import com.portfolio.dto.response.SkillResponse;
import com.portfolio.security.AdminAuthFilter;
import com.portfolio.security.RateLimitFilter;
import com.portfolio.security.SecurityErrorResolver;
import com.portfolio.service.admin.AdminSkillService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminSkillController.class)
@AutoConfigureMockMvc(addFilters = false) // Isola o escopo de teste das rotas da API desativando os filtros de rede
class AdminSkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminSkillService skillService;

    // Injeções de segurança e infraestrutura exigidas no ciclo de vida do ApplicationContext
    @MockBean
    private SecurityErrorResolver securityErrorResolver;

    @MockBean
    private AdminAuthFilter adminAuthFilter;

    @MockBean
    private RateLimitFilter rateLimitFilter;

    @Test
    @DisplayName("Deve retornar status 200 e a listagem ordinal de competências técnicas cadastrados ao efetuar GET")
    void shouldListSkillsSuccessfully() throws Exception {
        // Arrange
        SkillResponse resp = SkillResponse.builder().id(1L).name("React").category("Frontend").build();
        when(skillService.getAllSkills()).thenReturn(List.of(resp));

        // Act & Assert
        mockMvc.perform(get("/admin/skills"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("React"));

        verify(skillService, times(1)).getAllSkills();
    }

    @Test
    @DisplayName("Deve retornar status 201 e persistir o payload mapeado ao fornecer uma tecnologia válida")
    void shouldCreateSkillSuccessfully() throws Exception {
        // Arrange
        SkillRequest req = SkillRequest.builder().name("Java").category("Backend").proficiency(90).build();
        SkillResponse resp = SkillResponse.builder().id(2L).name("Java").build();

        when(skillService.createSkill(any(SkillRequest.class))).thenReturn(resp);

        // Act & Assert
        mockMvc.perform(post("/admin/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("Java"));

        verify(skillService, times(1)).createSkill(any(SkillRequest.class));
    }

    @Test
    @DisplayName("Deve retornar status 200 e consolidar as alterações cadastrais ao atualizar tecnologia existente")
    void shouldUpdateSkillSuccessfully() throws Exception {
        // Arrange
        Long targetId = 1L;
        SkillRequest req = SkillRequest.builder().name("TypeScript").category("Frontend").proficiency(95).build();
        SkillResponse resp = SkillResponse.builder().id(targetId).name("TypeScript").build();

        when(skillService.updateSkill(eq(targetId), any(SkillRequest.class))).thenReturn(resp);

        // Act & Assert
        mockMvc.perform(put("/admin/skills/{id}", targetId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(targetId))
                .andExpect(jsonPath("$.name").value("TypeScript"));

        verify(skillService, times(1)).updateSkill(eq(targetId), any(SkillRequest.class));
    }

    @Test
    @DisplayName("Deve efetuar o desligamento lógico de uma habilidade técnica e responder com status 204 no-content")
    void shouldDeleteSkillSuccessfully() throws Exception {
        // Arrange
        Long targetId = 1L;
        doNothing().when(skillService).deleteSkill(targetId);

        // Act & Assert
        mockMvc.perform(delete("/admin/skills/{id}", targetId))
                .andExpect(status().isNoContent());

        verify(skillService, times(1)).deleteSkill(targetId);
    }
}

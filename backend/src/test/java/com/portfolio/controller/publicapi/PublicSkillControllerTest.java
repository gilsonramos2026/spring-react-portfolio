package com.portfolio.controller.publicapi;

import com.portfolio.dto.response.SkillResponse;
import com.portfolio.security.AdminAuthFilter;
import com.portfolio.security.RateLimitFilter;
import com.portfolio.security.SecurityErrorResolver;
import com.portfolio.service.publicapi.PublicSkillService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PublicSkillController.class)
@AutoConfigureMockMvc(addFilters = false) // Desabilita os filtros de segurança para focar puramente nas rotas do Controller
class PublicSkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicSkillService skillService;

    // Beans de infraestrutura de rede obrigatórios para o contexto do Spring Boot carregar sem quebras
    @MockBean
    private SecurityErrorResolver securityErrorResolver;

    @MockBean
    private AdminAuthFilter adminAuthFilter;

    @MockBean
    private RateLimitFilter rateLimitFilter;

    @Test
    @DisplayName("Deve retornar status 200 e as chaves de categoria no nó raiz contendo os arrays de habilidades")
    void shouldReturnGroupedSkillsMapSuccessfully() throws Exception {
        // Arrange - Monta a estrutura de Map idêntica à gerada pela Stream de agrupamento do banco
        SkillResponse frontendSkill = SkillResponse.builder().id(1L).name("React").category("Frontend").build();
        SkillResponse backendSkill = SkillResponse.builder().id(2L).name("Java").category("Backend").build();

        Map<String, List<SkillResponse>> mockGroupedSkills = new LinkedHashMap<>();
        mockGroupedSkills.put("Frontend", List.of(frontendSkill));
        mockGroupedSkills.put("Backend", List.of(backendSkill));

        when(skillService.getSkillsGrouped()).thenReturn(mockGroupedSkills);

        // Act & Assert
        mockMvc.perform(get("/public/skills"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                // Valida o mapeamento de chaves dinâmicas no nó raiz do objeto JSON ($)
                .andExpect(jsonPath("$.Frontend").exists())
                .andExpect(jsonPath("$.Backend").exists())
                // Acessa o índice posicional interno do array contido na chave do dicionário
                .andExpect(jsonPath("$.Frontend[0].id").value(1L))
                .andExpect(jsonPath("$.Frontend[0].name").value("React"))
                .andExpect(jsonPath("$.Backend[0].id").value(2L))
                .andExpect(jsonPath("$.Backend[0].name").value("Java"));

        verify(skillService, times(1)).getSkillsGrouped();
    }
}

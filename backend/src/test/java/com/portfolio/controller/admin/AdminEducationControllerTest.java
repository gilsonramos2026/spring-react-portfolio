package com.portfolio.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.dto.request.EducationRequest;
import com.portfolio.dto.response.EducationResponse;
import com.portfolio.security.AdminAuthFilter;
import com.portfolio.security.RateLimitFilter;
import com.portfolio.security.SecurityErrorResolver;
import com.portfolio.service.admin.AdminEducationService;
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

@WebMvcTest(AdminEducationController.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminEducationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminEducationService eduService;

    @MockBean
    private SecurityErrorResolver securityErrorResolver;

    @MockBean
    private AdminAuthFilter adminAuthFilter;

    @MockBean
    private RateLimitFilter rateLimitFilter;

    @Test
    @DisplayName("Deve retornar status 200 e a lista de registros acadêmicos ao efetuar GET")
    void shouldListEducationsSuccessfully() throws Exception {
        EducationResponse resp = EducationResponse.builder()
                .id(1L)
                .institution("Universidade Tecnológica")
                .degree("Graduação")
                .build();
        when(eduService.getAllEducations()).thenReturn(List.of(resp));

        mockMvc.perform(get("/admin/educations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].institution").value("Universidade Tecnológica"))
                .andExpect(jsonPath("$[0].degree").value("Graduação"));

        verify(eduService, times(1)).getAllEducations();
    }

    @Test
    @DisplayName("Deve retornar status 201 e o payload mapeado ao cadastrar nova formação válida")
    void shouldCreateEducationSuccessfully() throws Exception {
        // CORRIGIDO: Adicionado startedAt exigido pelas validações do DTO
        EducationRequest req = EducationRequest.builder()
                .institution("Universidade Federal")
                .degree("Mestrado")
                .startedAt(LocalDate.now())
                .build();

        EducationResponse resp = EducationResponse.builder()
                .id(2L)
                .institution("Universidade Federal")
                .degree("Mestrado")
                .build();

        when(eduService.createEducation(any(EducationRequest.class))).thenReturn(resp);

        mockMvc.perform(post("/admin/educations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.institution").value("Universidade Federal"))
                .andExpect(jsonPath("$.degree").value("Mestrado"));

        verify(eduService, times(1)).createEducation(any(EducationRequest.class));
    }

    @Test
    @DisplayName("Deve retornar status 200 ao atualizar uma formação acadêmica com dados válidos")
    void shouldUpdateEducationSuccessfully() throws Exception {
        Long targetId = 1L;
        // CORRIGIDO: Adicionado startedAt exigido pelas validações do DTO
        EducationRequest req = EducationRequest.builder()
                .institution("Nova Universidade")
                .degree("Doutorado")
                .startedAt(LocalDate.now())
                .build();

        EducationResponse resp = EducationResponse.builder()
                .id(targetId)
                .institution("Nova Universidade")
                .degree("Doutorado")
                .build();

        when(eduService.updateEducation(eq(targetId), any(EducationRequest.class))).thenReturn(resp);

        mockMvc.perform(put("/admin/educations/{id}", targetId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(targetId))
                .andExpect(jsonPath("$.institution").value("Nova Universidade"))
                .andExpect(jsonPath("$.degree").value("Doutorado"));

        verify(eduService, times(1)).updateEducation(eq(targetId), any(EducationRequest.class));
    }

    @Test
    @DisplayName("Deve retornar status 204 no-content ao deletar um registro acadêmico")
    void shouldDeleteEducationSuccessfully() throws Exception {
        Long targetId = 1L;
        doNothing().when(eduService).deleteEducation(targetId);

        mockMvc.perform(delete("/admin/educations/{id}", targetId))
                .andExpect(status().isNoContent());

        verify(eduService, times(1)).deleteEducation(targetId);
    }
}

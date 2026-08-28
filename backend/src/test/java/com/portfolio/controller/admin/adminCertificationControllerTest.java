package com.portfolio.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.dto.request.CertificationRequest;
import com.portfolio.dto.response.CertificationResponse;
import com.portfolio.security.AdminAuthFilter;
import com.portfolio.security.RateLimitFilter;
import com.portfolio.security.SecurityErrorResolver;
import com.portfolio.service.admin.AdminCertificationService;
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

@WebMvcTest(AdminCertificationController.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminCertificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminCertificationService certService;

    @MockBean
    private SecurityErrorResolver securityErrorResolver;

    @MockBean
    private AdminAuthFilter adminAuthFilter;

    @MockBean
    private RateLimitFilter rateLimitFilter;

    @Test
    @DisplayName("Deve retornar status 200 e a lista de certificações ao efetuar GET")
    void shouldListCertificationsSuccessfully() throws Exception {
        CertificationResponse resp = CertificationResponse.builder().id(1L).name("Spring Boot Expert").build();
        when(certService.getAllCertifications()).thenReturn(List.of(resp));

        mockMvc.perform(get("/admin/certifications"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Spring Boot Expert"));

        verify(certService, times(1)).getAllCertifications();
    }

    @Test
    @DisplayName("Deve retornar status 201 e o payload mapeado ao cadastrar nova certificação válida")
    void shouldCreateCertificationSuccessfully() throws Exception {
        // CORRIGIDO: Inseridos os campos obrigatórios exigidos pelas validações do seu DTO
        CertificationRequest req = CertificationRequest.builder()
                .name("AWS Cloud Practitioner")
                .issuer("Amazon Web Services")
                .issuedAt(LocalDate.now())
                .build();

        CertificationResponse resp = CertificationResponse.builder()
                .id(2L)
                .name("AWS Cloud Practitioner")
                .build();

        when(certService.createCertification(any(CertificationRequest.class))).thenReturn(resp);

        mockMvc.perform(post("/admin/certifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("AWS Cloud Practitioner"));

        verify(certService, times(1)).createCertification(any(CertificationRequest.class));
    }

    @Test
    @DisplayName("Deve retornar status 200 ao atualizar uma certificação existente com dados válidos")
    void shouldUpdateCertificationSuccessfully() throws Exception {
        Long targetId = 1L;
        // CORRIGIDO: Inseridos os campos obrigatórios exigidos pelas validações do seu DTO
        CertificationRequest req = CertificationRequest.builder()
                .name("Java Professional")
                .issuer("Oracle")
                .issuedAt(LocalDate.now())
                .build();

        CertificationResponse resp = CertificationResponse.builder()
                .id(targetId)
                .name("Java Professional")
                .build();

        when(certService.updateCertification(eq(targetId), any(CertificationRequest.class))).thenReturn(resp);

        mockMvc.perform(put("/admin/certifications/{id}", targetId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(targetId))
                .andExpect(jsonPath("$.name").value("Java Professional"));

        verify(certService, times(1)).updateCertification(eq(targetId), any(CertificationRequest.class));
    }

    @Test
    @DisplayName("Deve retornar status 204 no-content ao remover uma certificação por ID")
    void shouldDeleteCertificationSuccessfully() throws Exception {
        Long targetId = 1L;
        doNothing().when(certService).deleteCertification(targetId);

        mockMvc.perform(delete("/admin/certifications/{id}", targetId))
                .andExpect(status().isNoContent());

        verify(certService, times(1)).deleteCertification(targetId);
    }
}

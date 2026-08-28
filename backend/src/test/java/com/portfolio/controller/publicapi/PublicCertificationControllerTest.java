package com.portfolio.controller.publicapi;

import com.portfolio.dto.response.CertificationResponse;
import com.portfolio.security.AdminAuthFilter;
import com.portfolio.security.RateLimitFilter;
import com.portfolio.security.SecurityErrorResolver;
import com.portfolio.service.publicapi.PublicCertificationService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PublicCertificationController.class)
@AutoConfigureMockMvc(addFilters = false) // Isola o comportamento das rotas desativando os filtros de rede globais
class PublicCertificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicCertificationService certService;

    // Beans de infraestrutura de rede obrigatórios para o contexto do Spring Boot carregar sem quebras
    @MockBean
    private SecurityErrorResolver securityErrorResolver;

    @MockBean
    private AdminAuthFilter adminAuthFilter;

    @MockBean
    private RateLimitFilter rateLimitFilter;

    @Test
    @DisplayName("Deve retornar status 200 e a listagem pública de certificados em formato de array JSON")
    void shouldReturnPublicCertificationsSuccessfully() throws Exception {
        // Arrange
        CertificationResponse resp = CertificationResponse.builder()
                .id(10L)
                .name("Spring Boot Expert")
                .issuer("Pivotal")
                .issuedAt(LocalDate.of(2026, 1, 1))
                .build();

        // Como o endpoint retorna uma lista, usamos a sintaxe posicional do array JSON ($[0]) para ler as chaves
        when(certService.getCertifications()).thenReturn(List.of(resp));

        // Act & Assert
        mockMvc.perform(get("/public/certifications"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id").value(10L))
                .andExpect(jsonPath("$[0].name").value("Spring Boot Expert"))
                .andExpect(jsonPath("$[0].issuer").value("Pivotal"));

        verify(certService, times(1)).getCertifications();
    }
}

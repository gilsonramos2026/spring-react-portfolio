package com.portfolio.controller.publicapi;

import com.portfolio.dto.response.EducationResponse;
import com.portfolio.security.AdminAuthFilter;
import com.portfolio.security.RateLimitFilter;
import com.portfolio.security.SecurityErrorResolver;
import com.portfolio.service.publicapi.PublicEducationService;
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

@WebMvcTest(PublicEducationController.class)
@AutoConfigureMockMvc(addFilters = false) // Desabilita os filtros de segurança globais para o isolamento do teste
class PublicEducationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicEducationService eduService;

    // Beans de infraestrutura de rede e segurança obrigatórios para subir o ApplicationContext do Spring
    @MockBean
    private SecurityErrorResolver securityErrorResolver;

    @MockBean
    private AdminAuthFilter adminAuthFilter;

    @MockBean
    private RateLimitFilter rateLimitFilter;

    @Test
    @DisplayName("Deve retornar status 200 e a listagem pública de formação acadêmica em formato de array JSON")
    void shouldReturnPublicEducationsSuccessfully() throws Exception {
        // Arrange
        EducationResponse resp = EducationResponse.builder()
                .id(1L)
                .institution("Universidade Federal")
                .degree("Bacharelado")
                .fieldOfStudy("Ciência da Computação")
                .build();

        when(eduService.getEducations()).thenReturn(List.of(resp));

        // Act & Assert
        mockMvc.perform(get("/public/educations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].institution").value("Universidade Federal"))
                .andExpect(jsonPath("$[0].degree").value("Bacharelado"))
                .andExpect(jsonPath("$[0].fieldOfStudy").value("Ciência da Computação"));

        verify(eduService, times(1)).getEducations();
    }
}

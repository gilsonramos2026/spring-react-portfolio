package com.portfolio.controller.publicapi;

import com.portfolio.dto.response.ProfileResponse;
import com.portfolio.security.AdminAuthFilter;
import com.portfolio.security.RateLimitFilter;
import com.portfolio.security.SecurityErrorResolver;
import com.portfolio.service.publicapi.PublicProfileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PublicProfileController.class)
@AutoConfigureMockMvc(addFilters = false) // Isola o comportamento das rotas desativando os filtros de rede globais
class PublicProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicProfileService profileService;

    // Beans de infraestrutura de rede obrigatórios para o contexto do Spring Boot carregar sem quebras
    @MockBean
    private SecurityErrorResolver securityErrorResolver;

    @MockBean
    private AdminAuthFilter adminAuthFilter;

    @MockBean
    private RateLimitFilter rateLimitFilter;

    @Test
    @DisplayName("Deve retornar status 200 e os dados do perfil profissional público formatados em JSON")
    void shouldReturnPublicProfileSuccessfully() throws Exception {
        // Arrange
        ProfileResponse resp = ProfileResponse.builder()
                .id(1L)
                .name("João Silva")
                .title("Desenvolvedor Full Stack")
                .bio("Especialista em Java e ecossistemas web.")
                .build();

        when(profileService.getProfile()).thenReturn(resp);

        // Act & Assert
        mockMvc.perform(get("/public/profile"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("João Silva"))
                .andExpect(jsonPath("$.title").value("Desenvolvedor Full Stack"))
                .andExpect(jsonPath("$.bio").value("Especialista em Java e ecossistemas web."));

        verify(profileService, times(1)).getProfile();
    }
}

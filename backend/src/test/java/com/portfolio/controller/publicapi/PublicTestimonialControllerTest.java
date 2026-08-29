package com.portfolio.controller.publicapi;

import com.portfolio.dto.response.TestimonialResponse;
import com.portfolio.security.AdminAuthFilter;
import com.portfolio.security.RateLimitFilter;
import com.portfolio.security.SecurityErrorResolver;
import com.portfolio.service.publicapi.PublicTestimonialService;
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

@WebMvcTest(PublicTestimonialController.class)
@AutoConfigureMockMvc(addFilters = false) // Isola o escopo do teste desativando filtros de rede globais
class PublicTestimonialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicTestimonialService testimonialService;

    // Beans de infraestrutura de rede obrigatórios para subir o ApplicationContext do Spring
    @MockBean
    private SecurityErrorResolver securityErrorResolver;

    @MockBean
    private AdminAuthFilter adminAuthFilter;

    @MockBean
    private RateLimitFilter rateLimitFilter;

    @Test
    @DisplayName("Deve retornar status 200 e a listagem ordinal de depoimentos ao chamar a rota raiz sem filtros")
    void shouldListAllPublicTestimonialsSuccessfully() throws Exception {
        // Arrange
        TestimonialResponse resp = TestimonialResponse.builder()
                .id(1L)
                .name("Carlos Eduardo")
                .role("Gerente de Projetos")
                .company("Global Tech")
                .featured(false)
                .build();
        when(testimonialService.getTestimonials(null)).thenReturn(List.of(resp));

        // Act & Assert
        mockMvc.perform(get("/public/testimonials"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Carlos Eduardo"))
                .andExpect(jsonPath("$[0].role").value("Gerente de Projetos"));

        verify(testimonialService, times(1)).getTestimonials(null);
    }

    @Test
    @DisplayName("Deve retornar status 200 e filtrar por destaque quando o parâmetro featured for TRUE")
    void shouldListFeaturedTestimonialsSuccessfully() throws Exception {
        // Arrange
        TestimonialResponse resp = TestimonialResponse.builder()
                .id(2L)
                .name("Mariana Costa")
                .role("Diretora de Arte")
                .company("Creative Studio")
                .featured(true)
                .build();
        when(testimonialService.getTestimonials(true)).thenReturn(List.of(resp));

        // Act & Assert
        mockMvc.perform(get("/public/testimonials")
                        .param("featured", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2L))
                .andExpect(jsonPath("$[0].name").value("Mariana Costa"))
                .andExpect(jsonPath("$[0].featured").value(true));

        verify(testimonialService, times(1)).getTestimonials(true);
    }
}

package com.portfolio.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.dto.request.TestimonialRequest;
import com.portfolio.dto.response.TestimonialResponse;
import com.portfolio.security.AdminAuthFilter;
import com.portfolio.security.RateLimitFilter;
import com.portfolio.security.SecurityErrorResolver;
import com.portfolio.service.admin.AdminTestimonialService;
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

@WebMvcTest(AdminTestimonialController.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminTestimonialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminTestimonialService testimonialService;

    @MockBean
    private SecurityErrorResolver securityErrorResolver;

    @MockBean
    private AdminAuthFilter adminAuthFilter;

    @MockBean
    private RateLimitFilter rateLimitFilter;

    @Test
    @DisplayName("Deve retornar status 200 e a listagem de depoimentos cadastrados ao efetuar GET")
    void shouldListTestimonialsSuccessfully() throws Exception {
        TestimonialResponse resp = TestimonialResponse.builder()
                .id(1L)
                .name("Alex Souza")
                .role("CTO")
                .company("Tech Enterprise")
                .build();

        when(testimonialService.getAllTestimonials()).thenReturn(List.of(resp));

        // CORRIGIDO: Adicionado $[0] para capturar elementos de uma lista JSON
        mockMvc.perform(get("/admin/testimonials"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Alex Souza"))
                .andExpect(jsonPath("$[0].role").value("CTO"));

        verify(testimonialService, times(1)).getAllTestimonials();
    }

    @Test
    @DisplayName("Deve retornar status 201 e persistir o payload mapeado ao fornecer um depoimento válido")
    void shouldCreateTestimonialSuccessfully() throws Exception {
        TestimonialRequest req = TestimonialRequest.builder()
                .name("Alex Souza")
                .role("CTO")
                .company("Tech Enterprise")
                .content("Excelente profissional, entrega no prazo.")
                .rating(5)
                .build();

        TestimonialResponse resp = TestimonialResponse.builder().id(1L).name("Alex Souza").build();

        when(testimonialService.createTestimonial(any(TestimonialRequest.class))).thenReturn(resp);

        mockMvc.perform(post("/admin/testimonials")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Alex Souza"));

        verify(testimonialService, times(1)).createTestimonial(any(TestimonialRequest.class));
    }

    @Test
    @DisplayName("Deve retornar status 200 e consolidar as alterações cadastrais ao atualizar depoimento existente")
    void shouldUpdateTestimonialSuccessfully() throws Exception {
        Long targetId = 1L;
        TestimonialRequest req = TestimonialRequest.builder()
                .name("Alex Alterado")
                .role("VPE")
                .company("Tech Enterprise")
                .content("Excelente profissional, super recomendável.")
                .rating(5)
                .build();

        TestimonialResponse resp = TestimonialResponse.builder().id(targetId).name("Alex Alterado").build();

        when(testimonialService.updateTestimonial(eq(targetId), any(TestimonialRequest.class))).thenReturn(resp);

        mockMvc.perform(put("/admin/testimonials/{id}", targetId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(targetId))
                .andExpect(jsonPath("$.name").value("Alex Alterado"));

        verify(testimonialService, times(1)).updateTestimonial(eq(targetId), any(TestimonialRequest.class));
    }

    @Test
    @DisplayName("Deve efetuar o desligamento lógico de um depoimento e responder com status 204 no-content")
    void shouldDeleteTestimonialSuccessfully() throws Exception {
        Long targetId = 1L;
        doNothing().when(testimonialService).deleteTestimonial(targetId);

        mockMvc.perform(delete("/admin/testimonials/{id}", targetId))
                .andExpect(status().isNoContent());

        verify(testimonialService, times(1)).deleteTestimonial(targetId);
    }
}

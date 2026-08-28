package com.portfolio.controller.publicapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.dto.request.ContactRequest;
import com.portfolio.security.AdminAuthFilter;
import com.portfolio.security.RateLimitFilter;
import com.portfolio.security.SecurityErrorResolver;
import com.portfolio.service.publicapi.PublicContactService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PublicContactController.class)
@AutoConfigureMockMvc(addFilters = false) // Isola o comportamento das rotas desativando os filtros de rede globais
class PublicContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PublicContactService contactService;

    // Beans de infraestrutura de rede obrigatórios para o contexto do Spring Boot carregar sem quebras
    @MockBean
    private SecurityErrorResolver securityErrorResolver;

    @MockBean
    private AdminAuthFilter adminAuthFilter;

    @MockBean
    private RateLimitFilter rateLimitFilter;

    @Test
    @DisplayName("Deve receber o payload de contato, capturar o IP do cliente e retornar status 201 com mensagem de sucesso")
    void shouldSendContactSuccessfully() throws Exception {
        // Arrange
        ContactRequest req = ContactRequest.builder()
                .name("Bruno Souza")
                .email("bruno@example.com")
                .subject("Proposta de Projeto")
                .message("Olá, gostaria de solicitar um orçamento para o desenvolvimento de um sistema.")
                .phone("11988888888")
                .build();

        String simulatedIp = "200.150.50.10";

        // Como o método do serviço retorna void, configuramos o doNothing
        doNothing().when(contactService).sendContact(any(ContactRequest.class), eq(simulatedIp));

        // Act & Assert
        mockMvc.perform(post("/public/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))
                        .remoteAddress(simulatedIp)) // Simula o preenchimento do IP remoto na requisição HTTP
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message").value("Mensagem enviada!"));

        verify(contactService, times(1)).sendContact(any(ContactRequest.class), eq(simulatedIp));
    }
}


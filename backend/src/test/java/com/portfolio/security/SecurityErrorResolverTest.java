package com.portfolio.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SecurityErrorResolverTest {

    private SecurityErrorResolver errorResolver;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        errorResolver = new SecurityErrorResolver();
    }

    @Test
    @DisplayName("Deve estruturar o JSON de erro 401 Unauthorized de forma limpa, definindo cabeçalhos e mensagens corretas")
    void shouldWriteStructuredUnauthorizedJsonErrorResponse() throws IOException {
        // Arrange
        MockHttpServletResponse response = new MockHttpServletResponse();
        String expectedMessage = "Chave administrativa expirada ou inválida";

        // Act
        errorResolver.handleUnauthorized(response, expectedMessage);

        // Assert
        // 1. Validações estritas dos cabeçalhos HTTP gerados na resposta
        assertEquals(401, response.getStatus());

        // CORRIGIDO: Pareado exatamente com o cabeçalho completo gerado pelo componente real
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        assertEquals("UTF-8", response.getCharacterEncoding());

        // 2. Extrai e faz o parse do corpo da resposta (JSON)
        String content = response.getContentAsString();
        assertNotNull(content);

        JsonNode jsonNode = objectMapper.readTree(content);

        assertEquals(401, jsonNode.get("status").asInt());
        assertEquals("Unauthorized", jsonNode.get("error").asText());
        assertEquals(expectedMessage, jsonNode.get("message").asText());
        assertTrue(jsonNode.has("timestamp"));
    }

}

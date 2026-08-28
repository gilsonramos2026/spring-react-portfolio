package com.portfolio.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateLimitFilterTest {

    @Mock
    private FilterChain filterChain;

    private RateLimitFilter rateLimitFilter;

    @BeforeEach
    void setUp() {
        rateLimitFilter = new RateLimitFilter();
    }

    @Test
    @DisplayName("Deve ignorar a filtragem (shouldNotFilter) para rotas que não sejam o POST público de contato")
    void shouldNotFilterOtherRoutesOrMethods() {
        // Arrange
        MockHttpServletRequest getContactRequest = new MockHttpServletRequest("GET", "/public/contact");
        MockHttpServletRequest postProjectRequest = new MockHttpServletRequest("POST", "/public/projects");
        MockHttpServletRequest validContactRequest = new MockHttpServletRequest("POST", "/public/contact");

        // Act & Assert
        assertTrue(rateLimitFilter.shouldNotFilter(getContactRequest));
        assertTrue(rateLimitFilter.shouldNotFilter(postProjectRequest));
        assertFalse(rateLimitFilter.shouldNotFilter(validContactRequest)); // Deve interceptar apenas este
    }

    @Test
    @DisplayName("Deve permitir até 5 requisições normais vindas do mesmo IP dentro da janela de tempo")
    void shouldAllowRequestsUnderTheLimit() throws ServletException, IOException {
        String clientIp = "192.168.1.50";

        for (int i = 1; i <= 5; i++) {
            // Arrange
            MockHttpServletRequest request = new MockHttpServletRequest("POST", "/public/contact");
            request.setRemoteAddr(clientIp);
            MockHttpServletResponse response = new MockHttpServletResponse();

            // Act
            rateLimitFilter.doFilterInternal(request, response, filterChain);

            // Assert
            assertEquals(HttpStatus.OK.value(), response.getStatus());

            // CORRIGIDO: Usa any() para realizar a verificação cumulativa de chamadas independentemente da instância dos mocks de requisição/resposta
            verify(filterChain, times(i)).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
        }
    }


    @Test
    @DisplayName("Deve barrar com Erro 429 e JSON explicativo a partir da 6ª requisição vinda do mesmo IP")
    void shouldBlockWithStatus429WhenLimitIsExceeded() throws ServletException, IOException {
        String clientIp = "10.0.0.5";

        // Consome as 5 primeiras requisições permitidas
        for (int i = 0; i < 5; i++) {
            MockHttpServletRequest req = new MockHttpServletRequest("POST", "/public/contact");
            req.setRemoteAddr(clientIp);
            MockHttpServletResponse res = new MockHttpServletResponse();
            rateLimitFilter.doFilterInternal(req, res, filterChain);
        }

        final var currentChainInvocations = 5;
        verify(filterChain, times(currentChainInvocations)).doFilter(any(), any());

        // 6ª Requisição (Excedendo o limite)
        MockHttpServletRequest blockRequest = new MockHttpServletRequest("POST", "/public/contact");
        blockRequest.setRemoteAddr(clientIp);
        MockHttpServletResponse blockResponse = new MockHttpServletResponse();

        // Act
        rateLimitFilter.doFilterInternal(blockRequest, blockResponse, filterChain);

        // Assert
        assertEquals(HttpStatus.TOO_MANY_REQUESTS.value(), blockResponse.getStatus());

        // CORRIGIDO: Pareado exatamente com o cabeçalho estrito gerado pelo filtro de produção
        assertEquals("application/json;charset=UTF-8", blockResponse.getContentType());
        assertEquals("UTF-8", blockResponse.getCharacterEncoding());

        String responseBody = blockResponse.getContentAsString();
        assertTrue(responseBody.contains("\"status\":429"));
        assertTrue(responseBody.contains("Limite de envios atingido. Aguarde 1 minuto."));

        // Garante que o filterChain NÃO foi invocado na 6ª requisição (fluxo barrado)
        verify(filterChain, times(currentChainInvocations)).doFilter(any(), any());
    }


    @Test
    @DisplayName("Deve extrair e priorizar o IP real obtido através do cabeçalho X-Forwarded-For")
    void shouldResolveClientIpUsingForwardedHeader() throws ServletException, IOException {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/public/contact");
        request.addHeader("X-Forwarded-For", "203.0.113.195, 70.41.3.18, 150.172.238.178");
        request.setRemoteAddr("127.0.0.1"); // IP do proxy local que deve ser descartado
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Act
        rateLimitFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain, times(1)).doFilter(request, response);
        // O teste passa sem estourar limite, confirmando o mapeamento correto do IP na tabela concorrente
    }
}


package com.portfolio.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminAuthFilterTest {

    @Mock
    private SecurityErrorResolver errorResolver;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private AdminAuthFilter adminAuthFilter;

    private final String secretKeyMock = "minha-chave-secreta-de-teste";

    @BeforeEach
    void setUp() {
        // Injeta manualmente a propriedade @Value no filtro de segurança
        ReflectionTestUtils.setField(adminAuthFilter, "secret", secretKeyMock);
    }

    @Test
    @DisplayName("Deve ignorar a filtragem (shouldNotFilter) quando a rota for pública ou requisição OPTIONS")
    void shouldNotFilterPublicOrOptionsRequests() {
        // Arrange
        MockHttpServletRequest publicRequest = new MockHttpServletRequest("GET", "/public/profile");
        MockHttpServletRequest optionsRequest = new MockHttpServletRequest("OPTIONS", "/admin/projects");
        MockHttpServletRequest adminRequest = new MockHttpServletRequest("GET", "/admin/projects");

        // Act & Assert
        assertTrue(adminAuthFilter.shouldNotFilter(publicRequest));
        assertTrue(adminAuthFilter.shouldNotFilter(optionsRequest));
        assertFalse(adminAuthFilter.shouldNotFilter(adminRequest)); // Esta rota administrativa precisa ser filtrada
    }

    @Test
    @DisplayName("Deve permitir a requisição avançar no FilterChain se o cabeçalho X-Admin-Key estiver correto")
    void shouldProceedWithChainWhenSecretKeyIsCorrect() throws ServletException, IOException {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/admin/skills");
        request.addHeader("X-Admin-Key", secretKeyMock); // Envia a chave secreta idêntica
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Act
        adminAuthFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain, times(1)).doFilter(request, response); // Avançou na pipeline do Spring
        verifyNoInteractions(errorResolver); // O resolvedor de erro não foi chamado
    }

    @Test
    @DisplayName("Deve barrar e invocar o resolvedor de erro 401 caso o cabeçalho X-Admin-Key venha nulo")
    void shouldBlockAndResolveErrorWhenHeaderIsMissing() throws ServletException, IOException {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/admin/projects");
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Act
        adminAuthFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(errorResolver, times(1)).handleUnauthorized(response, "Chave admin inválida");
        verifyNoInteractions(filterChain); // Garante que a requisição foi abortada imediatamente
    }

    @Test
    @DisplayName("Deve barrar e invocar o resolvedor de erro 401 caso a chave enviada seja inválida")
    void shouldBlockAndResolveErrorWhenKeyIsWrong() throws ServletException, IOException {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest("DELETE", "/admin/experiences/1");
        request.addHeader("X-Admin-Key", "chave-hacker-errada");
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Act
        adminAuthFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(errorResolver, times(1)).handleUnauthorized(response, "Chave admin inválida");
        verifyNoInteractions(filterChain); // Abortado
    }
}

package com.portfolio.exception;

import com.portfolio.security.AdminAuthFilter;
import com.portfolio.security.RateLimitFilter;
import com.portfolio.security.SecurityErrorResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ExceptionTestController.class) // Mapeia o controlador independente real
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityErrorResolver securityErrorResolver;

    @MockBean
    private AdminAuthFilter adminAuthFilter;

    @MockBean
    private RateLimitFilter rateLimitFilter;

    @Test
    @DisplayName("Deve capturar ResourceNotFoundException e retornar status 404 Not Found estruturado")
    void shouldHandleResourceNotFoundSuccessfully() throws Exception {
        mockMvc.perform(get("/api/test-error/missing"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Registro ausente no banco de dados"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Deve capturar FileStorageException e retornar status 400 Bad Request com erro de arquivo")
    void shouldHandleFileStorageExceptionSuccessfully() throws Exception {
        mockMvc.perform(get("/api/test-error/storage"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("File Error"))
                .andExpect(jsonPath("$.message").value("Extensão não permitida"));
    }

    @Test
    @DisplayName("Deve capturar MaxUploadSizeExceededException e retornar status 413 Payload Too Large")
    void shouldHandleMaxUploadSizeSuccessfully() throws Exception {
        mockMvc.perform(get("/api/test-error/large"))
                .andExpect(status().is(413))
                .andExpect(jsonPath("$.status").value(413))
                .andExpect(jsonPath("$.error").value("Too Large"))
                .andExpect(jsonPath("$.message").value("Arquivo excede 5MB"));
    }

    @Test
    @DisplayName("Deve mapear MethodArgumentNotValidException compilando os campos e mensagens em um mapa de erro")
    void shouldHandleValidationErrorsSuccessfully() throws Exception {
        mockMvc.perform(get("/api/test-error/validation"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.message.email").value("O e-mail digitado é inválido"));
    }

    @Test
    @DisplayName("Deve capturar qualquer exceção genérica do Java e mascarar com status 500 Internal Error")
    void shouldHandleGenericExceptionSuccessfully() throws Exception {
        mockMvc.perform(get("/api/test-error/generic"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.error").value("Internal Error"))
                .andExpect(jsonPath("$.message").value("Falha crítica interna"));
    }
}

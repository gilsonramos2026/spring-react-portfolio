package com.portfolio.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileStorageExceptionTest {

    @Test
    @DisplayName("Deve inicializar a exceção apenas com a mensagem de erro fornecida")
    void shouldInitializeWithMessageOnly() {
        // Arrange & Act
        String expectedMessage = "Falha ao gravar arquivo em disco";
        FileStorageException exception = new FileStorageException(expectedMessage);

        // Assert
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Deve inicializar a exceção contendo a mensagem de erro e a causa original do problema")
    void shouldInitializeWithSubsequentMessageAndCause() {
        // Arrange
        String expectedMessage = "Erro na pasta de destino";
        java.io.IOException rootCause = new java.io.IOException("Permissão negada no sistema operacional");

        // Act
        FileStorageException exception = new FileStorageException(expectedMessage, rootCause);

        // Assert
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
        assertNotNull(exception.getCause());
        assertEquals(rootCause, exception.getCause());
        assertEquals("Permissão negada no sistema operacional", exception.getCause().getMessage());
    }
}

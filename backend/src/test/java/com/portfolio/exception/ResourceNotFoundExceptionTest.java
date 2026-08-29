package com.portfolio.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {

    @Test
    @DisplayName("Deve inicializar a exceção de recurso não encontrado contendo a mensagem de erro literal informada")
    void shouldInitializeWithDirectMessage() {
        // Arrange & Act
        String expectedMessage = "Projeto com o slug 'portfolio-invalido' não foi encontrado";
        ResourceNotFoundException exception = new ResourceNotFoundException(expectedMessage);

        // Assert
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Deve inicializar a exceção formatando corretamente a entidade e o ID do registro ausente")
    void shouldInitializeWithEntityAndIdFormat() {
        // Arrange & Act
        ResourceNotFoundException exception = new ResourceNotFoundException("Habilidade", 15L);

        // Assert
        assertNotNull(exception);
        assertEquals("Habilidade não encontrado: id=15", exception.getMessage());
    }
}

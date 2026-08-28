package com.portfolio.service.impl; // Define o pacote do teste unitário para o serviço de armazenamento de arquivos

import com.portfolio.exception.FileStorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileStorageServiceImplTest {

    private FileStorageServiceImpl storageService; // Instância real do serviço de armazenamento a ser testada

    @TempDir
    Path tempDir; // O JUnit 5 cria e limpa uma pasta temporária automaticamente para cada teste de forma isolada

    private final String publicPath = "/uploads"; // Define a URL base pública simulada para os arquivos

    @BeforeEach
    void setUp() {
        storageService = new FileStorageServiceImpl();
        // Injeta os valores das propriedades anotadas com @Value manualmente via reflexão para o ambiente de teste
        ReflectionTestUtils.setField(storageService, "dir", tempDir.toString());
        ReflectionTestUtils.setField(storageService, "pub", publicPath);
    }

    @Test
    @DisplayName("Deve salvar um arquivo válido com sucesso e retornar a URL normalizada")
    void shouldStoreFileSuccessfully() throws IOException {
        // Arrange
        // Cria um arquivo simulado (MultipartFile) com conteúdo de teste
        MockMultipartFile file = new MockMultipartFile(
                "file", "avatar.png", "image/png", "conteudo-falso".getBytes()
        );

        // Act
        String url = storageService.store(file, "avatars");

        // Assert
        assertNotNull(url);
        assertTrue(url.startsWith("/uploads/avatars/")); // Confirma que a URL pública segue o padrão esperado
        assertTrue(url.endsWith(".png")); // Garante que a extensão foi preservada
        assertFalse(url.contains("//")); // Valida que a higienização de barras duplas funcionou perfeitamente

        // Valida se o arquivo físico foi realmente gravado na nossa pasta temporária do JUnit
        String filename = url.substring(url.lastIndexOf("/") + 1);
        Path expectedFile = tempDir.resolve("avatars").resolve(filename);
        assertTrue(Files.exists(expectedFile));
    }

    @Test
    @DisplayName("Deve lançar FileStorageException ao tentar enviar um arquivo vazio ou nulo")
    void shouldThrowExceptionWhenFileIsEmpty() {
        // Arrange
        MockMultipartFile emptyFile = new MockMultipartFile(
                "file", "avatar.jpg", "image/jpeg", new byte[0]
        );

        // Act & Assert
        // Garante que enviar arquivo vazio ou nulo dispara a exceção de armazenamento com segurança
        assertThrows(FileStorageException.class, () -> storageService.store(emptyFile, "avatars"));
        assertThrows(FileStorageException.class, () -> storageService.store(null, "avatars"));
    }

    @Test
    @DisplayName("Deve lançar FileStorageException ao tentar enviar um arquivo maior que 5MB")
    void shouldThrowExceptionWhenFileExceedsMaxSize() {
        // Arrange
        byte[] largeContent = new byte[(5 * 1024 * 1024) + 1]; // Cria um array de bytes ultrapassando o limite: 5MB + 1 byte
        MockMultipartFile largeFile = new MockMultipartFile(
                "file", "large.png", "image/png", largeContent
        );

        // Act & Assert
        FileStorageException exception = assertThrows(FileStorageException.class, () -> {
            storageService.store(largeFile, "projects");
        });
        assertEquals("Arquivo excede 5MB.", exception.getMessage()); // Valida a mensagem exata de erro de limite de tamanho
    }

    @Test
    @DisplayName("Deve lançar FileStorageException ao tentar enviar um arquivo com extensão proibida")
    void shouldThrowExceptionWhenExtensionIsNotAllowed() {
        // Arrange
        MockMultipartFile scriptFile = new MockMultipartFile(
                "file", "malicioso.exe", "application/octet-stream", "hacker".getBytes()
        );

        // Act & Assert
        FileStorageException exception = assertThrows(FileStorageException.class, () -> {
            storageService.store(scriptFile, "avatars"); // CORRIGIDO: Removida a linha com o método inexistente
        });
        assertTrue(exception.getMessage().contains("Tipo não permitido")); // Valida o bloqueio de extensões perigosas
    }


    @Test
    @DisplayName("Deve deletar o arquivo físico do disco com sucesso através da URL pública")
    void shouldDeleteFileSuccessfully() throws IOException {
        // Arrange
        // Cria manualmente um arquivo fictício na pasta temporária para simular um arquivo já existente no disco
        Path avatarsFolder = tempDir.resolve("avatars");
        Files.createDirectories(avatarsFolder);
        Path testFile = avatarsFolder.resolve("test-image.jpg");
        Files.writeString(testFile, "data");

        assertTrue(Files.exists(testFile));
        String urlToDelete = "/uploads/avatars/test-image.jpg";

        // Act
        // Solicita a exclusão do arquivo com base na URL pública
        storageService.delete(urlToDelete);

        // Assert
        assertFalse(Files.exists(testFile)); // Confirma que o arquivo foi limpo e removido do disco rígido com sucesso
    }
}
package com.portfolio.service.impl;

import com.portfolio.exception.FileStorageException;
import com.portfolio.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Set;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final Set<String> ALLOWED = Set.of("jpg", "jpeg", "png", "webp", "gif");
    private static final long MAX = 5L * 1024 * 1024;

    @Value("${app.upload.dir:./uploads}")
    private String dir;

    // Garanta que no seu yml isso aponte para /api/uploads para alinhar com o context-path
    @Value("${app.upload.public-path:/api/uploads}")
    private String pub;

    @Override
    public String store(MultipartFile file, String sub) {
        if (file == null || file.isEmpty()) {
            throw new FileStorageException("Arquivo vazio.");
        }
        if (file.getSize() > MAX) {
            throw new FileStorageException("Arquivo excede 5MB.");
        }

        String originalName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "file";
        String ext = ext(originalName);
        if (!ALLOWED.contains(ext.toLowerCase())) {
            throw new FileStorageException("Tipo não permitido: " + ext);
        }

        String name = UUID.randomUUID() + "." + ext.toLowerCase();

        try {
            // CORRIGIDO: Garante que o diretório base exista antes de concatenar a subpasta com segurança
            Path baseDir = Paths.get(dir).toAbsolutePath().normalize();

            // Remove barras iniciais ou finais do 'sub' para não quebrar a montagem do Path
            String cleanSub = sub.replaceAll("^/+", "").replaceAll("/+$", "");
            Path targetDir = baseDir.resolve(cleanSub).normalize();

            // Cria todas as pastas físicas necessárias de forma recursiva
            Files.createDirectories(targetDir);

            // Copia o arquivo para a pasta final
            Files.copy(file.getInputStream(), targetDir.resolve(name), StandardCopyOption.REPLACE_EXISTING);

            // CORRIGIDO: Normaliza as barras garantindo que comece com o prefixo correto da API
            String rawUrl = pub + "/" + cleanSub + "/" + name;
            return rawUrl.replaceAll("/+", "/");

        } catch (IOException e) {
            throw new FileStorageException("Erro ao salvar arquivo no disco: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(String url) {
        if (url == null || !url.startsWith(pub)) {
            return;
        }
        try {
            // Remove o prefixo público de forma segura
            String relativePath = url.substring(pub.length()).replaceAll("^/+", "");
            Path baseDir = Paths.get(dir).toAbsolutePath().normalize();
            Path filePath = baseDir.resolve(relativePath).normalize();

            Files.deleteIfExists(filePath);
        } catch (IOException ignored) {}
    }

    private String ext(String n) {
        int i = n.lastIndexOf('.');
        if (i < 0 || i == n.length() - 1) {
            throw new FileStorageException("Sem extensão válida.");
        }
        return n.substring(i + 1);
    }
}

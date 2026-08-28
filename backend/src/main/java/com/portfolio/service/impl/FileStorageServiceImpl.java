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
    private static final Set<String> ALLOWED = Set.of("jpg","jpeg","png","webp","gif");
    private static final long MAX = 5L*1024*1024;

    @Value("${app.upload.dir:./uploads}") private String dir;
    @Value("${app.upload.public-path:/uploads}") private String pub;

    @Override
    public String store(MultipartFile file, String sub) {
        if(file==null||file.isEmpty()) throw new FileStorageException("Arquivo vazio.");
        if(file.getSize()>MAX) throw new FileStorageException("Arquivo excede 5MB.");
        String ext=ext(file.getOriginalFilename()!=null?file.getOriginalFilename():"file");
        if(!ALLOWED.contains(ext.toLowerCase())) throw new FileStorageException("Tipo não permitido: "+ext);
        String name=UUID.randomUUID()+"."+ext.toLowerCase();
        try {
            Path d=Paths.get(dir,sub).normalize().toAbsolutePath();
            Files.createDirectories(d);
            Files.copy(file.getInputStream(),d.resolve(name),StandardCopyOption.REPLACE_EXISTING);
            return pub+"/"+sub+"/"+name;
        } catch(IOException e){ throw new FileStorageException("Erro ao salvar: "+e.getMessage(),e); }
    }

    @Override
    public void delete(String url) {
        if(url==null||!url.startsWith(pub)) return;
        try { Files.deleteIfExists(Paths.get(dir,url.substring(pub.length())).normalize().toAbsolutePath()); }
        catch(IOException ignored){}
    }

    private String ext(String n){
        int i=n.lastIndexOf('.');
        if(i<0||i==n.length()-1) throw new FileStorageException("Sem extensão válida.");
        return n.substring(i+1);
    }
}

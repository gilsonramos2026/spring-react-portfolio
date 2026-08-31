package com.portfolio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${spring.web.cors.allowed-origins:http://localhost:3000}")
    private String origins;

    @Value("${app.upload.dir:./uploads}")
    private String uploadDir;

    @Override
    public void addCorsMappings(CorsRegistry r){
        r.addMapping("/**")
                .allowedOriginPatterns(origins.split(","))
                .allowedMethods("GET","POST","PUT","DELETE","PATCH","OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry r){
        // Converte o caminho para absoluto e normalizado, evitando falhas do Tomcat/Spring
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        // CORRIGIDO: toUri().toString() garante o formato válido 'file:///' com barras normais (/) no Windows
        String loc = uploadPath.toUri().toString();

        // CORRIGIDO: Adicionado o prefixo /api para herdar as diretivas de roteamento do context-path
        r.addResourceHandler("/api/uploads/**")
                .addResourceLocations(loc)
                .setCachePeriod(0);

        r.addResourceHandler("/api/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/");

        r.addResourceHandler("/api/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
    }
}

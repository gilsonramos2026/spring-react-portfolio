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

    @Value("${app.upload.dir:./uploads}")
    private String uploadDir;

    @Override
    public void addCorsMappings(CorsRegistry r){
        // LIBERAÇÃO TOTAL DE CORS: Permite que a Vercel e o ambiente local consumam a API sem bloqueios
        r.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET","POST","PUT","DELETE","PATCH","OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry r){
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        String loc = uploadPath.toUri().toString();

        r.addResourceHandler("/api/uploads/**")
                .addResourceLocations(loc)
                .setCachePeriod(0);

        r.addResourceHandler("/api/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/");

        r.addResourceHandler("/api/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
    }
}

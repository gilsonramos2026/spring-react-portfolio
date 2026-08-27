package com.portfolio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${spring.web.cors.allowed-origins:http://localhost:3000}") private String origins;
    @Value("${app.upload.dir:./uploads}") private String uploadDir;

    @Override public void addCorsMappings(CorsRegistry r){
        r.addMapping("/**").allowedOriginPatterns(origins.split(","))
                .allowedMethods("GET","POST","PUT","DELETE","PATCH","OPTIONS")
                .allowedHeaders("*").allowCredentials(false).maxAge(3600);
    }
    @Override public void addResourceHandlers(ResourceHandlerRegistry r){
        String loc=uploadDir.endsWith("/")?uploadDir:uploadDir+"/";
        r.addResourceHandler("/uploads/**").addResourceLocations("file:"+loc);
    }
}

package com.portfolio.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.*;
import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info().title("Portfolio API").version("2.0.0")
                        .description("API do Portfólio Profissional. Admin: header X-Admin-Key.")
                        .contact(new Contact().name("Dev").email("dev@example.com")))
                .servers(List.of(
                        new Server().url("http://localhost:8080/api").description("Local"),
                        new Server().url("https://api.seudominio.com/api").description("Produção")))
                .components(new Components().addSecuritySchemes("AdminKeyAuth",
                        new SecurityScheme().type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER).name("X-Admin-Key")))
                .tags(List.of(
                        new Tag().name("Público - Perfil"), new Tag().name("Público - Projetos"),
                        new Tag().name("Público - Skills"), new Tag().name("Público - Experiências"),
                        new Tag().name("Público - Educação"), new Tag().name("Público - Certificações"),
                        new Tag().name("Público - Testemunhos"), new Tag().name("Público - Contato"),
                        new Tag().name("Admin - Perfil"), new Tag().name("Admin - Projetos"),
                        new Tag().name("Admin - Skills"), new Tag().name("Admin - Experiências"),
                        new Tag().name("Admin - Educação"), new Tag().name("Admin - Certificações"),
                        new Tag().name("Admin - Testemunhos"), new Tag().name("Admin - Contatos")));
    }
}
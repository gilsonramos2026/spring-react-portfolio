package com.portfolio.controller.publicapi;

import com.portfolio.dto.response.ProjectResponse;
import com.portfolio.service.publicapi.PublicProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/public/projects")
@RequiredArgsConstructor
@Tag(name = "Público - Projetos", description = "Endpoints para visualização de projetos desenvolvidos")
public class PublicProjectController {

    private final PublicProjectService svc;

    @GetMapping
    @Operation(summary = "Listar projetos públicos (com opção de filtrar por destaque)")
    public ResponseEntity<List<ProjectResponse>> projects(@RequestParam(required = false) Boolean featured) {
        return ResponseEntity.ok(svc.getProjects(featured));
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Buscar detalhes de um projeto específico pelo slug")
    public ResponseEntity<ProjectResponse> project(@PathVariable String slug) {
        return ResponseEntity.ok(svc.getProjectBySlug(slug));
    }
}
package com.portfolio.controller.admin;

import com.portfolio.dto.request.ProjectRequest;
import com.portfolio.dto.response.ProjectImageResponse;
import com.portfolio.dto.response.ProjectResponse;
import com.portfolio.service.AdminProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin/projects")
@RequiredArgsConstructor
@SecurityRequirement(name = "AdminKeyAuth")
@Tag(name = "Admin - Projetos", description = "Gerenciamento de projetos expostos no portfólio")
public class AdminProjectController {

    private final AdminProjectService svc;

    @GetMapping
    @Operation(summary = "Listar todos os projetos do portfólio")
    public ResponseEntity<List<ProjectResponse>> listProjects() {
        return ResponseEntity.ok(svc.getAllProjects());
    }

    @PostMapping
    @Operation(summary = "Criar um novo projeto")
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest r) {
        return ResponseEntity.status(201).body(svc.createProject(r));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um projeto existente")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectRequest r) {
        return ResponseEntity.ok(svc.updateProject(id, r));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um projeto")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        svc.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Adicionar imagem a um projeto (multipart/form-data)")
    public ResponseEntity<ProjectImageResponse> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "altText", required = false) String alt) {
        return ResponseEntity.status(201).body(svc.addProjectImage(id, file, alt));
    }

    @DeleteMapping("/{id}/images/{imgId}")
    @Operation(summary = "Excluir uma imagem específica do projeto")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id, @PathVariable Long imgId) {
        svc.deleteProjectImage(id, imgId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/images/reorder")
    @Operation(summary = "Reordenar as imagens de um projeto")
    public ResponseEntity<Void> reorder(@PathVariable Long id, @RequestBody List<Long> ids) {
        svc.reorderProjectImages(id, ids);
        return ResponseEntity.noContent().build();
    }
}
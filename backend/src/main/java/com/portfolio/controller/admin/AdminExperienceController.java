package com.portfolio.controller.admin;

import com.portfolio.dto.request.ExperienceRequest;
import com.portfolio.dto.response.ExperienceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/experiences")
@RequiredArgsConstructor
@SecurityRequirement(name = "AdminKeyAuth")
@Tag(name = "Admin - Experiências", description = "Gerenciamento do histórico profissional")
public class AdminExperienceController {

    private final AdminExperienceService svc;

    @GetMapping
    @Operation(summary = "Listar todas as experiências profissionais")
    public ResponseEntity<List<ExperienceResponse>> listExp() {
        return ResponseEntity.ok(svc.getAllExperiences());
    }

    @PostMapping
    @Operation(summary = "Cadastrar uma nova experiência profissional")
    public ResponseEntity<ExperienceResponse> createExp(@Valid @RequestBody ExperienceRequest r) {
        return ResponseEntity.status(201).body(svc.createExperience(r));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma experiência profissional existente")
    public ResponseEntity<ExperienceResponse> updateExp(@PathVariable Long id, @Valid @RequestBody ExperienceRequest r) {
        return ResponseEntity.ok(svc.updateExperience(id, r));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma experiência profissional")
    public ResponseEntity<Void> deleteExp(@PathVariable Long id) {
        svc.deleteExperience(id);
        return ResponseEntity.noContent().build();
    }
}

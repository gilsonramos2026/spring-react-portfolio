package com.portfolio.controller.admin;

import com.portfolio.dto.request.SkillRequest;
import com.portfolio.dto.response.SkillResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/skills")
@RequiredArgsConstructor
@SecurityRequirement(name = "AdminKeyAuth")
@Tag(name = "Admin - Skills", description = "Gerenciamento de habilidades e competências")
public class AdminSkillController {

    private final AdminSkillService svc;

    @GetMapping
    @Operation(summary = "Listar todas as habilidades cadastras")
    public ResponseEntity<List<SkillResponse>> listSkills() {
        return ResponseEntity.ok(svc.getAllSkills());
    }

    @PostMapping
    @Operation(summary = "Cadastrar uma nova habilidade")
    public ResponseEntity<SkillResponse> createSkill(@Valid @RequestBody SkillRequest r) {
        return ResponseEntity.status(201).body(svc.createSkill(r));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma habilidade existente")
    public ResponseEntity<SkillResponse> updateSkill(@PathVariable Long id, @Valid @RequestBody SkillRequest r) {
        return ResponseEntity.ok(svc.updateSkill(id, r));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma habilidade")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        svc.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }
}
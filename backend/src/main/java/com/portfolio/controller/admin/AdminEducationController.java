package com.portfolio.controller.admin;

import com.portfolio.dto.request.EducationRequest;
import com.portfolio.dto.response.EducationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/educations")
@RequiredArgsConstructor
@SecurityRequirement(name = "AdminKeyAuth")
@Tag(name = "Admin - Educação", description = "Gerenciamento do histórico acadêmico")
public class AdminEducationController {

    private final AdminEducationService svc;

    @GetMapping
    @Operation(summary = "Listar todo o histórico acadêmico")
    public ResponseEntity<List<EducationResponse>> listEdu() {
        return ResponseEntity.ok(svc.getAllEducations());
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo registro acadêmico")
    public ResponseEntity<EducationResponse> createEdu(@Valid @RequestBody EducationRequest r) {
        return ResponseEntity.status(201).body(svc.createEducation(r));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um registro acadêmico existente")
    public ResponseEntity<EducationResponse> updateEdu(@PathVariable Long id, @Valid @RequestBody EducationRequest r) {
        return ResponseEntity.ok(svc.updateEducation(id, r));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um registro acadêmico")
    public ResponseEntity<Void> deleteEdu(@PathVariable Long id) {
        svc.deleteEducation(id);
        return ResponseEntity.noContent().build();
    }
}
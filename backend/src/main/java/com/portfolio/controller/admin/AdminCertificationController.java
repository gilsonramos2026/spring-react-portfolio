package com.portfolio.controller.admin;

import com.portfolio.dto.request.CertificationRequest;
import com.portfolio.dto.response.CertificationResponse;
import com.portfolio.service.admin.AdminCertificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/certifications")
@RequiredArgsConstructor
@SecurityRequirement(name = "AdminKeyAuth")
@Tag(name = "Admin - Certificações", description = "Gerenciamento de certificados obtidos")
public class AdminCertificationController {

    private final AdminCertificationService svc;

    @GetMapping
    @Operation(summary = "Listar todas as certificações")
    public ResponseEntity<List<CertificationResponse>> listCerts() {
        return ResponseEntity.ok(svc.getAllCertifications());
    }

    @PostMapping
    @Operation(summary = "Cadastrar uma nova certificação")
    public ResponseEntity<CertificationResponse> createCert(@Valid @RequestBody CertificationRequest r) {
        return ResponseEntity.status(201).body(svc.createCertification(r));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma certificação existente")
    public ResponseEntity<CertificationResponse> updateCert(@PathVariable Long id, @Valid @RequestBody CertificationRequest r) {
        return ResponseEntity.ok(svc.updateCertification(id, r));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma certificação")
    public ResponseEntity<Void> deleteCert(@PathVariable Long id) {
        svc.deleteCertification(id);
        return ResponseEntity.noContent().build();
    }
}
package com.portfolio.controller.publicapi;

import com.portfolio.dto.response.CertificationResponse;
import com.portfolio.service.publicapi.PublicCertificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/public/certifications")
@RequiredArgsConstructor
@Tag(name = "Público - Certificações", description = "Endpoints para consulta de certificados obtidos")
public class PublicCertificationController {

    private final PublicCertificationService svc;

    @GetMapping
    @Operation(summary = "Listar certificações públicas obtidas")
    public ResponseEntity<List<CertificationResponse>> certifications() {
        return ResponseEntity.ok(svc.getCertifications());
    }
}
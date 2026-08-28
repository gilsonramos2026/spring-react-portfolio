package com.portfolio.controller.publicapi;

import com.portfolio.dto.response.ExperienceResponse;
import com.portfolio.service.publicapi.PublicExperienceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/public/experiences")
@RequiredArgsConstructor
@Tag(name = "Público - Experiências", description = "Endpoints para consulta do histórico profissional")
public class PublicExperienceController {

    private final PublicExperienceService svc;

    @GetMapping
    @Operation(summary = "Listar histórico profissional público")
    public ResponseEntity<List<ExperienceResponse>> experiences() {
        return ResponseEntity.ok(svc.getExperiences());
    }
}
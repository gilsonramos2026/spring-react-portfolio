package com.portfolio.controller.publicapi;

import com.portfolio.dto.response.EducationResponse;
import com.portfolio.service.publicapi.PublicEducationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/public/educations")
@RequiredArgsConstructor
@Tag(name = "Público - Educação", description = "Endpoints para consulta de histórico acadêmico")
public class PublicEducationController {

    private final PublicEducationService svc;

    @GetMapping
    @Operation(summary = "Listar histórico acadêmico público")
    public ResponseEntity<List<EducationResponse>> educations() {
        return ResponseEntity.ok(svc.getEducations());
    }
}
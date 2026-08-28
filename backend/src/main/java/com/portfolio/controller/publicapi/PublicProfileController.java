package com.portfolio.controller.publicapi;

import com.portfolio.dto.response.ProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/profile")
@RequiredArgsConstructor
@Tag(name = "Público - Perfil", description = "Endpoints para consulta do perfil público")
public class PublicProfileController {

    private final com.portfolio.service.publicapi.PublicProfileService svc;

    @GetMapping
    @Operation(summary = "Obter dados do perfil público")
    public ResponseEntity<ProfileResponse> profile() {
        return ResponseEntity.ok(svc.getProfile());
    }
}
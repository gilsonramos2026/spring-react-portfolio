package com.portfolio.controller.admin;

import com.portfolio.dto.request.ProfileRequest;
import com.portfolio.dto.response.ProfileResponse;
import com.portfolio.service.PortfolioAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/profile")
@RequiredArgsConstructor
@SecurityRequirement(name = "AdminKeyAuth")
@Tag(name = "Admin - Perfil", description = "Gerenciamento do perfil profissional do proprietário")
public class AdminProfileController {

    private final PortfolioAdminService svc;

    @PutMapping
    @Operation(summary = "Criar ou atualizar os dados do perfil profissional")
    public ResponseEntity<ProfileResponse> upsertProfile(@Valid @RequestBody ProfileRequest request) {
        return ResponseEntity.ok(svc.upsertProfile(request));
    }

    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload da foto de perfil (multipart/form-data)")
    public ResponseEntity<ProfileResponse> uploadAvatar(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(svc.uploadAvatar(file));
    }
}
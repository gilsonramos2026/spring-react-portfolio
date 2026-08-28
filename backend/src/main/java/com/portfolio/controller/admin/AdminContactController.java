package com.portfolio.controller.admin;

import com.portfolio.dto.request.ContactStatusRequest;
import com.portfolio.dto.response.ContactResponse;
import com.portfolio.service.admin.AdminContactService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/contacts")
@RequiredArgsConstructor
@SecurityRequirement(name = "AdminKeyAuth")
@Tag(name = "Admin - Contatos", description = "Monitoramento e controle de mensagens recebidas")
public class AdminContactController {

    private final AdminContactService svc;

    @GetMapping
    public ResponseEntity<List<ContactResponse>> listContacts(@RequestParam(required = false) String status) {
        return ResponseEntity.ok(svc.getContacts(status));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ContactResponse> updateStatus(@PathVariable Long id, @Valid @RequestBody ContactStatusRequest r) {
        return ResponseEntity.ok(svc.updateContactStatus(id, r.getStatus()));
    }

    @GetMapping("/count-new")
    public ResponseEntity<Map<String, Long>> countNew() {
        return ResponseEntity.ok(Map.of("count", svc.countNewContacts()));
    }
}


package com.portfolio.controller.publicapi;

import com.portfolio.dto.request.ContactRequest;
import com.portfolio.dto.response.MessageResponse;
import com.portfolio.service.publicapi.PublicContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/contact")
@RequiredArgsConstructor
@Tag(name = "Público - Contato", description = "Endpoint para envio do formulário de mensagens")
public class PublicContactController {

    private final PublicContactService svc;

    @PostMapping
    @Operation(summary = "Enviar mensagem pelo formulário de contato público")
    public ResponseEntity<MessageResponse> contact(@Valid @RequestBody ContactRequest req, HttpServletRequest http) {
        svc.sendContact(req, http.getRemoteAddr());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.builder().message("Mensagem enviada!").build());
    }
}
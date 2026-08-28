package com.portfolio.controller.publicapi;

import com.portfolio.dto.response.TestimonialResponse;
import com.portfolio.service.publicapi.PublicTestimonialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/public/testimonials")
@RequiredArgsConstructor
@Tag(name = "Público - Testemunhos", description = "Endpoints para listagem de depoimentos")
public class PublicTestimonialController {

    private final PublicTestimonialService svc;

    @GetMapping
    @Operation(summary = "Listar depoimentos públicos (com opção de filtrar por destaque)")
    public ResponseEntity<List<TestimonialResponse>> testimonials(@RequestParam(required = false) Boolean featured) {
        return ResponseEntity.ok(svc.getTestimonials(featured));
    }
}
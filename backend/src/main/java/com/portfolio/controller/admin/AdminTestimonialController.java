package com.portfolio.controller.admin;

import com.portfolio.dto.request.TestimonialRequest;
import com.portfolio.dto.response.TestimonialResponse;
import com.portfolio.service.admin.AdminTestimonialService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/testimonials")
@RequiredArgsConstructor
@SecurityRequirement(name = "AdminKeyAuth")
@Tag(name = "Admin - Testemunhos", description = "Gerenciamento de recomendações enviadas")
public class AdminTestimonialController {

    private final AdminTestimonialService svc;

    @GetMapping
    public ResponseEntity<List<TestimonialResponse>> listTest() {
        return ResponseEntity.ok(svc.getAllTestimonials());
    }

    @PostMapping
    public ResponseEntity<TestimonialResponse> createTest(@Valid @RequestBody TestimonialRequest r) {
        return ResponseEntity.status(201).body(svc.createTestimonial(r));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestimonialResponse> updateTest(@PathVariable Long id, @Valid @RequestBody TestimonialRequest r) {
        return ResponseEntity.ok(svc.updateTestimonial(id, r));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable Long id) {
        svc.deleteTestimonial(id);
        return ResponseEntity.noContent().build();
    }
}

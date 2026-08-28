package com.portfolio.controller.publicapi;

import com.portfolio.dto.response.SkillResponse;
import com.portfolio.service.publicapi.PublicSkillService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/public/skills")
@RequiredArgsConstructor
@Tag(name = "Público - Skills", description = "Endpoints para consulta de competências técnicas estruturadas")
public class PublicSkillController {

    private final PublicSkillService svc;

    @GetMapping
    public ResponseEntity<Map<String, List<SkillResponse>>> skills() {
        return ResponseEntity.ok(svc.getSkillsGrouped());
    }
}

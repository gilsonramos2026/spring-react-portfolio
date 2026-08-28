package com.portfolio.service;

import com.portfolio.dto.request.SkillRequest;
import com.portfolio.dto.response.SkillResponse;
import jakarta.validation.Valid;

import java.util.List;

public class AdminSkillService {
    public List<SkillResponse> getAllSkills() {
        return null;
    }

    public SkillResponse createSkill(@Valid SkillRequest r) {
        return null;
    }

    public SkillResponse updateSkill(Long id, @Valid SkillRequest r) {
        return null;
    }

    public void deleteSkill(Long id) {
        return;
    }
}

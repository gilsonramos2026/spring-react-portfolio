package com.portfolio.service;

import com.portfolio.dto.request.ExperienceRequest;
import com.portfolio.dto.response.ExperienceResponse;
import jakarta.validation.Valid;

import java.util.List;

public class AdminExperienceService {
    public List<ExperienceResponse> getAllExperiences() {
        return null;
    }

    public ExperienceResponse createExperience(@Valid ExperienceRequest r) {
        return null;
    }

    public ExperienceResponse updateExperience(Long id, @Valid ExperienceRequest r) {
        return null;
    }

    public void deleteExperience(Long id) {
        return;
    }
}

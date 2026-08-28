package com.portfolio.service.admin;

import com.portfolio.dto.request.ExperienceRequest;
import com.portfolio.dto.response.ExperienceResponse;
import java.util.List;

public interface AdminExperienceService {
    List<ExperienceResponse> getAllExperiences();
    ExperienceResponse createExperience(ExperienceRequest req);
    ExperienceResponse updateExperience(Long id, ExperienceRequest req);
    void deleteExperience(Long id);
}

package com.portfolio.service.publicapi;

import com.portfolio.dto.response.ExperienceResponse;
import java.util.List;

public interface PublicExperienceService {
    List<ExperienceResponse> getExperiences();
}

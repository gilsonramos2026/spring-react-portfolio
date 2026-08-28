package com.portfolio.service.publicapi;

import com.portfolio.dto.response.EducationResponse;
import java.util.List;

public interface PublicEducationService {
    List<EducationResponse> getEducations();
}

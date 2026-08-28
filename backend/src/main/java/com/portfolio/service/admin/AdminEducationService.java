package com.portfolio.service.admin;

import com.portfolio.dto.request.EducationRequest;
import com.portfolio.dto.response.EducationResponse;
import java.util.List;

public interface AdminEducationService {
    List<EducationResponse> getAllEducations();
    EducationResponse createEducation(EducationRequest req);
    EducationResponse updateEducation(Long id, EducationRequest req);
    void deleteEducation(Long id);
}

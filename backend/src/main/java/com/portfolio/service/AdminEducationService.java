package com.portfolio.service;

import com.portfolio.dto.request.EducationRequest;
import com.portfolio.dto.response.EducationResponse;
import jakarta.validation.Valid;

import java.util.List;

public class AdminEducationService {
    public List<EducationResponse> getAllEducations() {
        return null;
    }

    public EducationResponse createEducation(@Valid EducationRequest r) {
        return null;
    }

    public EducationResponse updateEducation(Long id, @Valid EducationRequest r) {
        return null;
    }

    public void deleteEducation(Long id) {
        return;
    }
}

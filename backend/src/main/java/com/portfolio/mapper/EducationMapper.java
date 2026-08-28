package com.portfolio.mapper;

import com.portfolio.dto.request.EducationRequest;
import com.portfolio.dto.response.EducationResponse;
import com.portfolio.entity.Education;
import org.springframework.stereotype.Component;

@Component
public class EducationMapper {

    public EducationResponse toResponse(Education e) {
        if (e == null) return null;
        return EducationResponse.builder()
                .id(e.getId())
                .institution(e.getInstitution())
                .degree(e.getDegree())
                .fieldOfStudy(e.getFieldOfStudy())
                .description(e.getDescription())
                .logoUrl(e.getLogoUrl())
                .grade(e.getGrade())
                .startedAt(e.getStartedAt())
                .endedAt(e.getEndedAt())
                .current(e.getCurrent())
                .sortOrder(e.getSortOrder())
                .build();
    }

    public void applyRequest(Education e, EducationRequest r) {
        if (e == null || r == null) return;
        if (r.getInstitution() != null) e.setInstitution(r.getInstitution());
        if (r.getDegree() != null) e.setDegree(r.getDegree());
        if (r.getFieldOfStudy() != null) e.setFieldOfStudy(r.getFieldOfStudy());
        if (r.getDescription() != null) e.setDescription(r.getDescription());
        if (r.getLogoUrl() != null) e.setLogoUrl(r.getLogoUrl());
        if (r.getGrade() != null) e.setGrade(r.getGrade());
        if (r.getStartedAt() != null) e.setStartedAt(r.getStartedAt());
        if (r.getEndedAt() != null) e.setEndedAt(r.getEndedAt());
        if (r.getCurrent() != null) e.setCurrent(r.getCurrent());
        if (r.getSortOrder() != null) e.setSortOrder(r.getSortOrder());
        if (r.getActive() != null) e.setActive(r.getActive());
    }
}

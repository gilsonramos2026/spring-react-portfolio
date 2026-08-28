package com.portfolio.mapper;

import com.portfolio.dto.request.ExperienceRequest;
import com.portfolio.dto.response.ExperienceResponse;
import com.portfolio.entity.Experience;
import org.springframework.stereotype.Component;

@Component
public class ExperienceMapper {

    public ExperienceResponse toResponse(Experience e) {
        if (e == null) return null;
        return ExperienceResponse.builder()
                .id(e.getId())
                .company(e.getCompany())
                .role(e.getRole())
                .description(e.getDescription())
                .logoUrl(e.getLogoUrl())
                .location(e.getLocation())
                .type(e.getType())
                .startedAt(e.getStartedAt())
                .endedAt(e.getEndedAt())
                .current(e.getCurrent())
                .sortOrder(e.getSortOrder())
                .technologies(e.getTechnologies())
                .build();
    }

    public void applyRequest(Experience e, ExperienceRequest r) {
        if (e == null || r == null) return;
        if (r.getCompany() != null) e.setCompany(r.getCompany());
        if (r.getRole() != null) e.setRole(r.getRole());
        if (r.getDescription() != null) e.setDescription(r.getDescription());
        if (r.getLogoUrl() != null) e.setLogoUrl(r.getLogoUrl());
        if (r.getLocation() != null) e.setLocation(r.getLocation());
        if (r.getType() != null) e.setType(r.getType());
        if (r.getStartedAt() != null) e.setStartedAt(r.getStartedAt());
        if (r.getEndedAt() != null) e.setEndedAt(r.getEndedAt());
        if (r.getCurrent() != null) e.setCurrent(r.getCurrent());
        if (r.getSortOrder() != null) e.setSortOrder(r.getSortOrder());
        if (r.getActive() != null) e.setActive(r.getActive());
        if (r.getTechnologies() != null) e.setTechnologies(r.getTechnologies());
    }
}


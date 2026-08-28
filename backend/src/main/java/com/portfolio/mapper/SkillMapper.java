package com.portfolio.mapper;

import com.portfolio.dto.request.SkillRequest;
import com.portfolio.dto.response.SkillResponse;
import com.portfolio.entity.Skill;
import org.springframework.stereotype.Component;

@Component
public class SkillMapper {

    public SkillResponse toResponse(Skill s) {
        if (s == null) return null;
        return SkillResponse.builder()
                .id(s.getId())
                .name(s.getName())
                .category(s.getCategory())
                .proficiency(s.getProficiency())
                .iconName(s.getIconName())
                .sortOrder(s.getSortOrder())
                .build();
    }

    public void applyRequest(Skill s, SkillRequest r) {
        if (s == null || r == null) return;
        if (r.getName() != null) s.setName(r.getName());
        if (r.getCategory() != null) s.setCategory(r.getCategory());
        if (r.getProficiency() != null) s.setProficiency(r.getProficiency());
        if (r.getIconName() != null) s.setIconName(r.getIconName());
        if (r.getSortOrder() != null) s.setSortOrder(r.getSortOrder());
        if (r.getActive() != null) s.setActive(r.getActive());
    }
}


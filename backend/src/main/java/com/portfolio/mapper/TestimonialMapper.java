package com.portfolio.mapper;

import com.portfolio.dto.request.TestimonialRequest;
import com.portfolio.dto.response.TestimonialResponse;
import com.portfolio.entity.Testimonial;
import org.springframework.stereotype.Component;

@Component
public class TestimonialMapper {

    public TestimonialResponse toResponse(Testimonial t) {
        if (t == null) return null;
        return TestimonialResponse.builder()
                .id(t.getId())
                .name(t.getName())
                .role(t.getRole())
                .company(t.getCompany())
                .content(t.getContent())
                .avatarUrl(t.getAvatarUrl())
                .rating(t.getRating())
                .featured(t.getFeatured())
                .sortOrder(t.getSortOrder())
                .active(t.getActive())
                .build();
    }

    public void applyRequest(Testimonial t, TestimonialRequest r) {
        if (t == null || r == null) return;
        if (r.getName() != null) t.setName(r.getName());
        if (r.getRole() != null) t.setRole(r.getRole());
        if (r.getCompany() != null) t.setCompany(r.getCompany());
        if (r.getContent() != null) t.setContent(r.getContent());
        if (r.getAvatarUrl() != null) t.setAvatarUrl(r.getAvatarUrl());
        if (r.getRating() != null) t.setRating(r.getRating());
        if (r.getFeatured() != null) t.setFeatured(r.getFeatured());
        if (r.getActive() != null) t.setActive(r.getActive());
        if (r.getSortOrder() != null) t.setSortOrder(r.getSortOrder());
    }
}

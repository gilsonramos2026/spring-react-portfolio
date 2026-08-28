package com.portfolio.mapper;

import com.portfolio.dto.request.ProfileRequest;
import com.portfolio.dto.response.ProfileResponse;
import com.portfolio.entity.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public ProfileResponse toResponse(Profile p) {
        if (p == null) return null;
        return ProfileResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .title(p.getTitle())
                .tagline(p.getTagline())
                .bio(p.getBio())
                .email(p.getEmail())
                .phone(p.getPhone())
                .location(p.getLocation())
                .avatarUrl(p.getAvatarUrl())
                .resumeUrl(p.getResumeUrl())
                .githubUrl(p.getGithubUrl())
                .linkedinUrl(p.getLinkedinUrl())
                .instagramUrl(p.getInstagramUrl())
                .websiteUrl(p.getWebsiteUrl())
                .yearsExp(p.getYearsExp())
                .available(p.getAvailable())
                .updateAt(p.getUpdateAt())
                .build();
    }

    public void applyRequest(Profile p, ProfileRequest r) {
        if (p == null || r == null) return;
        if (r.getName() != null) p.setName(r.getName());
        if (r.getTitle() != null) p.setTitle(r.getTitle());
        if (r.getTagline() != null) p.setTagline(r.getTagline());
        if (r.getBio() != null) p.setBio(r.getBio());
        if (r.getEmail() != null) p.setEmail(r.getEmail());
        if (r.getPhone() != null) p.setPhone(r.getPhone());
        if (r.getLocation() != null) p.setLocation(r.getLocation());
        if (r.getAvatarUrl() != null) p.setAvatarUrl(r.getAvatarUrl());
        if (r.getResumeUrl() != null) p.setResumeUrl(r.getResumeUrl());
        if (r.getGithubUrl() != null) p.setGithubUrl(r.getGithubUrl());
        if (r.getLinkedinUrl() != null) p.setLinkedinUrl(r.getLinkedinUrl());
        if (r.getInstagramUrl() != null) p.setInstagramUrl(r.getInstagramUrl());
        if (r.getWebsiteUrl() != null) p.setWebsiteUrl(r.getWebsiteUrl());
        if (r.getYearsExp() != null) p.setYearsExp(r.getYearsExp());
        if (r.getAvailable() != null) p.setAvailable(r.getAvailable());
    }
}


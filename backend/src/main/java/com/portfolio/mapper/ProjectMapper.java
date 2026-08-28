package com.portfolio.mapper;

import com.portfolio.dto.request.ProjectRequest;
import com.portfolio.dto.response.ProjectImageResponse;
import com.portfolio.dto.response.ProjectResponse;
import com.portfolio.entity.Project;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ProjectMapper {

    public ProjectResponse toResponse(Project p) {
        if (p == null) return null;

        List<ProjectImageResponse> imgs = p.getImages().stream()
                .map(i -> ProjectImageResponse.builder()
                        .id(i.getId())
                        .url(i.getUrl())
                        .altText(i.getAltText())
                        .sortOrder(i.getSortOrder())
                        .build())
                .toList();

        return ProjectResponse.builder()
                .id(p.getId())
                .title(p.getTitle())
                .slug(p.getSlug())
                .shortDesc(p.getShortDesc())
                .description(p.getDescription())
                .thumbnailUrl(p.getThumbnailUrl())
                .demoUrl(p.getDemoUrl())
                .githubUrl(p.getGithubUrl())
                .featured(p.getFeatured())
                .status(p.getStatus())
                .sortOrder(p.getSortOrder())
                .tags(p.getTags())
                .images(imgs)
                .startedAt(p.getStartedAt())
                .finishedAt(p.getFinishedAt())
                .createdAt(p.getCreatedAt())
                .build();
    }

    public void applyRequest(Project p, ProjectRequest r) {
        if (p == null || r == null) return;
        if (r.getTitle() != null) p.setTitle(r.getTitle());
        if (r.getShortDesc() != null) p.setShortDesc(r.getShortDesc());
        if (r.getDescription() != null) p.setDescription(r.getDescription());
        if (r.getThumbnailUrl() != null) p.setThumbnailUrl(r.getThumbnailUrl());
        if (r.getDemoUrl() != null) p.setDemoUrl(r.getDemoUrl());
        if (r.getGithubUrl() != null) p.setGithubUrl(r.getGithubUrl());
        if (r.getFeatured() != null) p.setFeatured(r.getFeatured());
        if (r.getStatus() != null) p.setStatus(r.getStatus());
        if (r.getSortOrder() != null) p.setSortOrder(r.getSortOrder());
        if (r.getTags() != null) p.setTags(r.getTags());
        if (r.getStartedAt() != null) p.setStartedAt(r.getStartedAt());
        if (r.getFinishedAt() != null) p.setFinishedAt(r.getFinishedAt());
    }
}

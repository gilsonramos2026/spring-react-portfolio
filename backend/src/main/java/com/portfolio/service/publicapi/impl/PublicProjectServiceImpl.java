package com.portfolio.service.publicapi.impl;

import com.portfolio.dto.response.ProjectResponse;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.mapper.ProjectMapper;
import com.portfolio.repository.ProjectRepository;
import com.portfolio.service.publicapi.PublicProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicProjectServiceImpl implements PublicProjectService {

    private final ProjectRepository projectRepo;
    private final ProjectMapper mapper;

    @Override
    public List<ProjectResponse> getProjects(Boolean featured) {
        var list = Boolean.TRUE.equals(featured)
                ? projectRepo.findByActiveTrueAndFeaturedTrueOrderBySortOrderAsc()
                : projectRepo.findByActiveTrueOrderBySortOrderAscAndCreatedAtDesc();
        return list.stream().map(mapper::toResponse).toList();
    }

    @Override
    public ProjectResponse getProjectBySlug(String slug) {
        return projectRepo.findBySlugAndActiveTrue(slug)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto com o slug '" + slug + "' não foi encontrado"));
    }
}


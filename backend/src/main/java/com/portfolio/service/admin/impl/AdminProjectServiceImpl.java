package com.portfolio.service.admin.impl;

import com.portfolio.dto.request.ProjectRequest;
import com.portfolio.dto.response.ProjectImageResponse;
import com.portfolio.dto.response.ProjectResponse;
import com.portfolio.entity.Project;
import com.portfolio.entity.ProjectImage;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.mapper.ProjectMapper;
import com.portfolio.repository.ProjectImageRepository;
import com.portfolio.repository.ProjectRepository;
import com.portfolio.service.FileStorageService;
import com.portfolio.service.admin.AdminProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.Normalizer;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminProjectServiceImpl implements AdminProjectService {

    private final ProjectRepository projectRepo;
    private final ProjectImageRepository imageRepo;
    private final ProjectMapper mapper;
    private final FileStorageService storage;

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getAllProjects() {
        return projectRepo.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public ProjectResponse createProject(ProjectRequest r) {
        Project p = new Project();
        mapper.applyRequest(p, r);
        p.setSlug(slug(r.getTitle()));
        p.setActive(true);
        return mapper.toResponse(projectRepo.save(p));
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest r) {
        Project p = projectRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto com ID " + id + " não encontrado"));

        // Mantém o slug atualizado caso o título mude no painel admin
        if (r.getTitle() != null && !r.getTitle().equalsIgnoreCase(p.getTitle())) {
            p.setSlug(slug(r.getTitle()));
        }

        mapper.applyRequest(p, r);
        return mapper.toResponse(projectRepo.save(p));
    }

    @Override
    public void deleteProject(Long id) {
        Project p = projectRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto com ID " + id + " não encontrado"));
        p.setActive(false);
        projectRepo.save(p);
    }

    @Override
    public ProjectImageResponse addProjectImage(Long pid, MultipartFile file, String alt) {
        Project p = projectRepo.findById(pid)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto com ID " + pid + " não encontrado"));
        String url = storage.store(file, "projects/" + pid);
        int nextOrder = projectRepo.findById(pid).isPresent() ? imageRepo.nextSortOrder(pid) : 0;

        ProjectImage img = ProjectImage.builder()
                .project(p)
                .url(url)
                .altText(alt)
                .sortOrder(nextOrder)
                .build();

        ProjectImage saved = imageRepo.save(img);
        return ProjectImageResponse.builder()
                .id(saved.getId())
                .url(saved.getUrl())
                .altText(saved.getAltText())
                .sortOrder(saved.getSortOrder())
                .build();
    }

    @Override
    public void deleteProjectImage(Long pid, Long iid) {
        ProjectImage img = imageRepo.findById(iid)
                .orElseThrow(() -> new ResourceNotFoundException("Imagem com ID " + iid + " não encontrada"));

        if (!imageRepo.existsByIdAndProjectId(iid, pid)) {
            throw new ResourceNotFoundException("A imagem informada não pertence a este projeto");
        }

        storage.delete(img.getUrl());
        imageRepo.delete(img);
    }

    @Override
    public void reorderProjectImages(Long pid, List<Long> ids) {
        List<ProjectImage> images = imageRepo.findByProjectIdOrderBySortOrderAsc(pid);
        images.forEach(img -> {
            int i = ids.indexOf(img.getId());
            if (i >= 0) img.setSortOrder(i);
        });
        imageRepo.saveAll(images);
    }

    private String slug(String title) {
        return Normalizer.normalize(title, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "").toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "").trim()
                .replaceAll("\\s+", "-")
                + "-" + System.currentTimeMillis();
    }
}

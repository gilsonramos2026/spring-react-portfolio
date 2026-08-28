package com.portfolio.service;

import com.portfolio.dto.request.ProjectRequest;
import com.portfolio.dto.response.ProjectImageResponse;
import com.portfolio.dto.response.ProjectResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class AdminProjectService {
    public List<ProjectResponse> getAllProjects() {
        return null;
    }

    public ProjectResponse createProject(ProjectRequest r) {
        return null;
    }

    public ProjectResponse updateProject(Long id, ProjectRequest r) {
        return null;
    }

    public void deleteProject(Long id) {
        return;
    }

    public ProjectImageResponse addProjectImage(Long id, MultipartFile file, String alt) {
        return null;
    }

    public void deleteProjectImage(Long id, Long imgId) {
        return;
    }

    public void reorderProjectImages(Long id, List<Long> ids) {
        return;
    }
}

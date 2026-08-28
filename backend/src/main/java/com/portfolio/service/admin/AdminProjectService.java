package com.portfolio.service.admin;

import com.portfolio.dto.request.ProjectRequest;
import com.portfolio.dto.response.ProjectImageResponse;
import com.portfolio.dto.response.ProjectResponse;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface AdminProjectService {
    List<ProjectResponse> getAllProjects();
    ProjectResponse createProject(ProjectRequest req);
    ProjectResponse updateProject(Long id, ProjectRequest req);
    void deleteProject(Long id);
    ProjectImageResponse addProjectImage(Long projectId, MultipartFile file, String altText);
    void deleteProjectImage(Long projectId, Long imageId);
    void reorderProjectImages(Long projectId, List<Long> ids);
}

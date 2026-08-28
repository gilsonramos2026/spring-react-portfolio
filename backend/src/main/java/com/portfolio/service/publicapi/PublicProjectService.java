package com.portfolio.service.publicapi;

import com.portfolio.dto.response.ProjectResponse;
import java.util.List;

public interface PublicProjectService {
    List<ProjectResponse> getProjects(Boolean featured);
    ProjectResponse getProjectBySlug(String slug);
}

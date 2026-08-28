package com.portfolio.dto.response;

import lombok.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {

    private Long id;
    private String title;
    private String slug;
    private String shortDesc;
    private String description;
    private String thumbnailUrl;
    private String demoUrl;
    private String githubUrl;
    private Boolean featured;
    private String status;
    private Integer sortOrder;
    private Set<String> tags;
    private List<ProjectImageResponse> images;
    private LocalDate startedAt;
    private LocalDate finishedAt;
    private LocalDate createdAt;
}


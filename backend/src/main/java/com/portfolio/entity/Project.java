package com.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="projects")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable=false,unique=true,length=200)
    private String slug;

    @Column(name="short_desc",nullable=false,length=300)
    private String shortDesc;

    @Column(columnDefinition="TEXT")
    private String description;

    @Column(name="thumbnail_url",length=500)
    private String thumbnailUrl;

    @Column(name="demo_url",length=300)
    private String demoUrl;

    @Column(name="github_url",length=300)
    private String githubUrl;

    @Builder.Default
    private Boolean featured = false;

    @Builder.Default
    private String status = "Completed";

    @Column(name="sort_order")
    @Builder.Default
    private Integer sortOrder = 0;

    @Builder.Default
    private Boolean active = true;

    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="project_tags",joinColumns=@JoinColumn(name="project_id"))
    @Column(name="tag")
    @Builder.Default
    private Set<String> tags = new HashSet<>();

    @OneToMany(mappedBy="project",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
    @OrderBy("sortOrder ASC")
    @Builder.Default
    private List<ProjectImage> images = new ArrayList<>();

    @Column(name="started_at")
    private LocalDate startedAt;

    @Column(name="finished_at")
    private LocalDate finishedAt;

    @Column(name="created_at")
    private LocalDate createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }
}

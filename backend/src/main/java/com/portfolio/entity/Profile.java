package com.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false,length = 150)
    private String title;

    @Column(length = 255)
    private String tagline;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 30)
    private String phone;

    @Column(length = 100)
    private String location;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Column(name = "resume_url",  length = 500)
    private String resumeUrl;

    @Column(name = "github_url", length = 300)
    private String githubUrl;

    @Column(name = "linkedin_url", length = 300)
    private String linkedinUrl;

    @Column(name = "instagram_url", length = 300)
    private String instagramUrl;

    @Column(name = "website_url", length = 300)
    private String websiteUrl;

    @Column(name = "years_exp")
    private Integer  yearsExp;

    @Builder.Default
    private Boolean available = true;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.updateAt = LocalDateTime.now();
    }
}

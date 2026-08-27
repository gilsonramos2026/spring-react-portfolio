package com.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "educations")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false,length=150)
    private String institution;

    @Column(nullable=false,length=100)
    private String degree;

    @Column(name="field_of_study",length=150)
    private String fieldOfStudy;

    @Column(columnDefinition="TEXT")
    private String description;

    @Column(name="logo_url",length=500)
    private String logoUrl;

    @Column(length=30)
    private String grade;

    @Column(name="started_at",nullable=false)
    private LocalDate startedAt;

    @Column(name="ended_at")
    private LocalDate endedAt;

    @Builder.Default
    private Boolean current = false;

    @Column(name="sort_order") @Builder.Default
    Integer sortOrder=0;

    @Builder.Default
    private Boolean active=true;
}

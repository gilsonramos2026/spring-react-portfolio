package com.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "experiences")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false,length=150)
    private String company;

    @Column(nullable=false,length=150)
    private String role;

    @Column(columnDefinition="TEXT")
    private String description;

    @Column(name="logo_url",length=500)
    private String logoUrl;

    @Column(length=100)
    private String location;

    @Builder.Default
    private String type = "full_time";

    @Column(name="started_at",nullable=false)
    private LocalDate startedAt;

    @Column(name="ended_at")
    private LocalDate endedAt;

    @Builder.Default
    private Boolean current=false;

    @Column(name="sort_order") @Builder.Default
    private Integer sortOrder=0;

    @Builder.Default
    private Boolean active=true;

    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="experience_technologies",joinColumns=@JoinColumn(name="experience_id"))
    @Column(name="technology")
    @Builder.Default
    private List<String> technologies=new ArrayList<>();
}

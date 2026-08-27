package com.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "skills")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false,length=80)
    private String name;

    @Column(nullable=false,length=60)
    private String category;

    @Column(nullable=false)
    private Integer proficiency;

    @Column(name="icon_name",length=80)
    private String iconName;

    @Column(name="sort_order")
    @Builder.Default
    private Integer  sortOrder = 0;

    @Builder.Default
    private Boolean active = true;
}

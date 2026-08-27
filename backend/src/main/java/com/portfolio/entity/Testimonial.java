package com.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="testimonials")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Testimonial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false,length=100)
    private String name;

    @Column(nullable=false,length=150)
    private String role;

    @Column(length=150)
    private String company;

    @Column(columnDefinition="TEXT",nullable=false)
    private String content;

    @Column(name="avatar_url",length=500)
    private String avatarUrl;

    @Builder.Default
    private Integer  rating= 5;

    @Builder.Default
    private Boolean featured= false;

    @Column(name="sort_order") @Builder.Default
    private Integer sortOrder=0;

    @Builder.Default
    private Boolean active= true;
}

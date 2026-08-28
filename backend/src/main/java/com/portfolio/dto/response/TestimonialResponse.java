package com.portfolio.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestimonialResponse {

    private Long id;
    private String name;
    private String role;
    private String company;
    private String content;
    private String avatarUrl;
    private Integer rating;
    private Boolean featured;
    private Integer sortOrder;
    private Boolean active;
}

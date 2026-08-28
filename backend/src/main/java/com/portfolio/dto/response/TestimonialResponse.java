package com.portfolio.dto.response;

import lombok.*;

@Data
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

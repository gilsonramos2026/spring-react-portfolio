package com.portfolio.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillResponse {
    private Long id;
    private String name;
    private String category;
    private String iconName;
    private Integer proficiency;
    private Integer sortOrder;
}


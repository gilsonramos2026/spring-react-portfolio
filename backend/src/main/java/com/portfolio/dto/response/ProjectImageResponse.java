package com.portfolio.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectImageResponse {
    private Long id;
    private String url;
    private String altText;
    private Integer sortOrder;
}

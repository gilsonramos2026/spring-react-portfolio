package com.portfolio.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectImageResponse {
    private Long id;
    private String url;
    private String altText;
    private Integer sortOrder;
}

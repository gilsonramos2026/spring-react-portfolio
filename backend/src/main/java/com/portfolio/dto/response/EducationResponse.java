package com.portfolio.dto.response;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationResponse {
    private Long id;
    private String institution;
    private String degree;
    private String fieldOfStudy;
    private String description;
    private String logoUrl;
    private String grade;
    private LocalDate startedAt;
    private LocalDate endedAt;
    private Boolean current;
    private Integer sortOrder;
}

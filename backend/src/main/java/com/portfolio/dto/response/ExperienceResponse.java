package com.portfolio.dto.response;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceResponse {
    private Long id;
    private String company;
    private String role;
    private String description;
    private String logoUrl;
    private String location;
    private String type;
    private LocalDate startedAt;
    private LocalDate endedAt;
    private Boolean current;
    private Integer sortOrder;
    private List<String> technologies;
}


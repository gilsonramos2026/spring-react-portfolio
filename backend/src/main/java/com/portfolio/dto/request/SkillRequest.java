package com.portfolio.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SkillRequest {

    @NotBlank
    @Size(max=80)
    private String name;

    @NotBlank
    @Size(max=60)
    private String category;

    @NotNull
    @Min(1)
    @Max(100)
    private Integer proficiency;

    @Size(max=80)
    private String iconName;

    private Integer sortOrder;

    private Boolean active;
}

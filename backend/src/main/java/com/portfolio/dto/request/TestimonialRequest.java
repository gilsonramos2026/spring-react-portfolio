package com.portfolio.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestimonialRequest {
    @NotBlank
    @Size(max=100)
    private String name;

    @NotBlank
    @Size(max=150)
    private String role;

    @Size(max=150)
    private String company;

    @NotBlank
    private String content;

    @Size(max=500)
    private String avatarUrl;

    @Min(1)
    @Max(5)
    private Integer rating;

    private Boolean featured;

    private Boolean active;

    private Integer sortOrder;
}

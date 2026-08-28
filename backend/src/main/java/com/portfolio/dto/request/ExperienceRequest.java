package com.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceRequest {

    @NotBlank(message = "A empresa é obrigatória")
    @Size(max = 150, message = "A empresa não pode exceder 150 caracteres")
    private String company;

    @NotBlank(message = "O cargo é obrigatório")
    @Size(max = 150, message = "O cargo não pode exceder 150 caracteres")
    private String role;

    private String description;

    @Size(max = 500, message = "A URL do logotipo não pode exceder 500 caracteres")
    private String logoUrl;

    @Size(max = 100, message = "A localização não pode exceder 100 caracteres")
    private String location;

    private String type;

    @NotNull(message = "A data de início é obrigatória")
    private LocalDate startedAt;

    private LocalDate endedAt;

    private Boolean current;

    private Boolean active;

    private Integer sortOrder;

    private List<String> technologies;
}

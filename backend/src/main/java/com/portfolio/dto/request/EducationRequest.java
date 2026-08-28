package com.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationRequest {

    @NotBlank(message = "A instituição é obrigatória")
    @Size(max = 150, message = "A instituição não pode exceder 150 caracteres")
    private String institution;

    @NotBlank(message = "O título/grau acadêmico é obrigatório")
    @Size(max = 100, message = "O grau não pode exceder 100 caracteres")
    private String degree;

    @Size(max = 150, message = "O campo de estudo não pode exceder 150 caracteres")
    private String fieldOfStudy; // CORRIGIDO: Adicionado a letra 'd' que faltava

    private String description;

    @Size(max = 500, message = "A URL do logotipo não pode exceder 500 caracteres")
    private String logoUrl;

    @Size(max = 30, message = "A nota/grade não pode exceder 30 caracteres")
    private String grade;

    @NotNull(message = "A data de início é obrigatória")
    private LocalDate startedAt;

    private LocalDate endedAt;

    private Boolean current;

    private Boolean active;

    private Integer sortOrder;
}

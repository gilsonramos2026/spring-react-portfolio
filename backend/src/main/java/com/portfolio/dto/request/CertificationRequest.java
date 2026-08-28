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
public class CertificationRequest {

    @NotBlank(message = "O nome da certificação é obrigatório")
    @Size(max = 200, message = "O nome não pode exceder 200 caracteres")
    private String name;

    @NotBlank(message = " O emissor da certificação é obrigatório")
    @Size(max = 150, message = "O emissor não pode exceder 150 caracteres")
    private String issuer;

    @Size(max = 200, message = "O ID da credencial não pode exceder 200 caracteres")
    private String credentialId;

    @Size(max = 500, message = "A URL da credencial não pode exceder 500 caracteres")
    private String credentialUrl;

    @Size(max = 500, message = "A URL da imagem não pode exceder 500 caracteres")
    private String imageUrl;

    @NotNull(message = "A data de emissão é obrigatória")
    private LocalDate issuedAt;

    private LocalDate expiresAt;

    private Boolean active;

    private Integer sortOrder;
}

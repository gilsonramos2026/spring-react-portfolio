package com.portfolio.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome não pode exceder 100 caracteres")
    private String name;

    @NotBlank(message = "O título profissional é obrigatório")
    @Size(max = 150, message = "O título não pode exceder 150 caracteres")
    private String title;

    @Size(max = 255, message = "A tagline não pode exceder 255 caracteres")
    private String tagline;

    private String bio;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "O e-mail informado é inválido")
    @Size(max = 100, message = "O e-mail não pode exceder 100 caracteres")
    private String email;

    @Size(max = 30, message = "O telefone não pode exceder 30 caracteres")
    private String phone;

    @Size(max = 100, message = "A localização não pode exceder 100 caracteres")
    private String location;

    @Size(max = 500, message = "A URL do avatar não pode exceder 500 caracteres")
    private String avatarUrl;

    @Size(max = 500, message = "A URL do currículo não pode exceder 500 caracteres")
    private String resumeUrl;

    @Size(max = 300, message = "A URL do GitHub não pode exceder 300 caracteres")
    private String githubUrl;

    @Size(max = 300, message = "A URL do LinkedIn não pode exceder 300 caracteres")
    private String linkedinUrl;

    @Size(max = 300, message = "A URL do Instagram não pode exceder 300 caracteres")
    private String instagramUrl;

    @Size(max = 300, message = "A URL do website não pode exceder 300 caracteres")
    private String websiteUrl;

    @Min(value = 0, message = "Os anos de experiência não podem ser negativos")
    @Max(value = 60, message = "Valor inválido para anos de experiência")
    private Integer yearsExp;

    private Boolean available;
}

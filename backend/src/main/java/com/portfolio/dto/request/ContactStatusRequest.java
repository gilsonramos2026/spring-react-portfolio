package com.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactStatusRequest {

    @NotBlank
    @Pattern(regexp = "new|read|replied|archived")
    private String status;
}

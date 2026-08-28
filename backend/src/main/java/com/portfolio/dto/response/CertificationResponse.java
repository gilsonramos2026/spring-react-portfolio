package com.portfolio.dto.response;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationResponse {
    private Long id;
    private String name;
    private String issuer;
    private String credentialId;
    private String credentialUrl;
    private String imageUrl;
    private LocalDate issuedAt;
    private LocalDate expiresAt;
    private Integer sortOrder;
}

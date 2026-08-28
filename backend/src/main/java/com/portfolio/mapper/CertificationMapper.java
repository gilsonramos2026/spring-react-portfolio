package com.portfolio.mapper;

import com.portfolio.dto.request.CertificationRequest;
import com.portfolio.dto.response.CertificationResponse;
import com.portfolio.entity.Certification;
import org.springframework.stereotype.Component;

@Component
public class CertificationMapper {

    public CertificationResponse toResponse(Certification c) {
        if (c == null) return null;
        return CertificationResponse.builder()
                .id(c.getId())
                .name(c.getName())
                .issuer(c.getIssuer())
                .credentialId(c.getCredentialId())
                .credentialUrl(c.getCredentialUrl())
                .imageUrl(c.getImageUrl())
                .issuedAt(c.getIssuedAt())
                .expiresAt(c.getExpiresAt())
                .sortOrder(c.getSortOrder())
                .build();
    }

    public void applyRequest(Certification c, CertificationRequest r) {
        if (c == null || r == null) return;
        if (r.getName() != null) c.setName(r.getName());
        if (r.getIssuer() != null) c.setIssuer(r.getIssuer());
        if (r.getCredentialId() != null) c.setCredentialId(r.getCredentialId());
        if (r.getCredentialUrl() != null) c.setCredentialUrl(r.getCredentialUrl());
        if (r.getImageUrl() != null) c.setImageUrl(r.getImageUrl());
        if (r.getIssuedAt() != null) c.setIssuedAt(r.getIssuedAt());
        if (r.getExpiresAt() != null) c.setExpiresAt(r.getExpiresAt());
        if (r.getActive() != null) c.setActive(r.getActive());
        if (r.getSortOrder() != null) c.setSortOrder(r.getSortOrder());
    }
}


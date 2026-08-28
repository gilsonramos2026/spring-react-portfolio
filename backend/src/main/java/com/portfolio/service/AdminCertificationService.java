package com.portfolio.service;

import com.portfolio.dto.request.CertificationRequest;
import com.portfolio.dto.response.CertificationResponse;
import jakarta.validation.Valid;

import java.util.List;

public class AdminCertificationService {
    public List<CertificationResponse> getAllCertifications() {
        return null;
    }

    public CertificationResponse createCertification(@Valid CertificationRequest r) {
        return null;
    }

    public CertificationResponse updateCertification(Long id, @Valid CertificationRequest r) {
        return null;
    }

    public void deleteCertification(Long id) {
        return;
    }
}

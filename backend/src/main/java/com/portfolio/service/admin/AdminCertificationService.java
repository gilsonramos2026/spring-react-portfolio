package com.portfolio.service.admin;

import com.portfolio.dto.request.CertificationRequest;
import com.portfolio.dto.response.CertificationResponse;
import java.util.List;

public interface AdminCertificationService {
    List<CertificationResponse> getAllCertifications();
    CertificationResponse createCertification(CertificationRequest req);
    CertificationResponse updateCertification(Long id, CertificationRequest req);
    void deleteCertification(Long id);
}


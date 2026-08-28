package com.portfolio.service.publicapi;

import com.portfolio.dto.response.CertificationResponse;
import java.util.List;

public interface PublicCertificationService {
    List<CertificationResponse> getCertifications();
}

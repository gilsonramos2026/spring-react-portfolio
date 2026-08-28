package com.portfolio.service.publicapi.impl;

import com.portfolio.dto.response.CertificationResponse;
import com.portfolio.mapper.CertificationMapper;
import com.portfolio.repository.CertificationRepository;
import com.portfolio.service.publicapi.PublicCertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicCertificationServiceImpl implements PublicCertificationService {

    private final CertificationRepository certRepo;
    private final CertificationMapper mapper;

    @Override
    public List<CertificationResponse> getCertifications() {
        return certRepo.findByActiveTrueOrderBySortOrderAscAndIssuedAtDesc().stream()
                .map(mapper::toResponse)
                .toList();
    }
}

package com.portfolio.service.admin.impl;

import com.portfolio.dto.request.CertificationRequest;
import com.portfolio.dto.response.CertificationResponse;
import com.portfolio.entity.Certification;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.mapper.CertificationMapper;
import com.portfolio.repository.CertificationRepository;
import com.portfolio.service.admin.AdminCertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCertificationServiceImpl implements AdminCertificationService {

    private final CertificationRepository certRepo;
    private final CertificationMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<CertificationResponse> getAllCertifications() {
        return certRepo.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public CertificationResponse createCertification(CertificationRequest r) {
        Certification c = new Certification();
        mapper.applyRequest(c, r);
        c.setActive(true); // Garante o registro ativo por padrão
        return mapper.toResponse(certRepo.save(c));
    }

    @Override
    public CertificationResponse updateCertification(Long id, CertificationRequest r) {

        Certification c = certRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificação com ID " + id + " não encontrada"));
        mapper.applyRequest(c, r);
        return mapper.toResponse(certRepo.save(c));
    }

    @Override
    public void deleteCertification(Long id) {

        Certification c = certRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificação com ID " + id + " não encontrada"));
        c.setActive(false);
        certRepo.save(c);
    }
}

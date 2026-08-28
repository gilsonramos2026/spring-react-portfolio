package com.portfolio.service.admin.impl;

import com.portfolio.dto.request.EducationRequest;
import com.portfolio.dto.response.EducationResponse;
import com.portfolio.entity.Education;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.mapper.EducationMapper;
import com.portfolio.repository.EducationRepository;
import com.portfolio.service.admin.AdminEducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminEducationServiceImpl implements AdminEducationService {

    private final EducationRepository eduRepo;
    private final EducationMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<EducationResponse> getAllEducations() {
        return eduRepo.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public EducationResponse createEducation(EducationRequest r) {
        Education e = new Education();
        mapper.applyRequest(e, r);
        e.setActive(true); // Garante que inicia como ativa por padrão
        return mapper.toResponse(eduRepo.save(e));
    }

    @Override
    public EducationResponse updateEducation(Long id, EducationRequest r) {

        Education e = eduRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de educação com ID " + id + " não encontrado"));
        mapper.applyRequest(e, r);
        return mapper.toResponse(eduRepo.save(e));
    }

    @Override
    public void deleteEducation(Long id) {
        // CORRIGIDO: Aplicando Soft Delete utilizando a flag active em vez de apagar do banco
        Education e = eduRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de educação com ID " + id + " não encontrado"));
        e.setActive(false);
        eduRepo.save(e);
    }
}

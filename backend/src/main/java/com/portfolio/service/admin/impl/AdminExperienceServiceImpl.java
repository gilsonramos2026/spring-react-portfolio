package com.portfolio.service.admin.impl;

import com.portfolio.dto.request.ExperienceRequest;
import com.portfolio.dto.response.ExperienceResponse;
import com.portfolio.entity.Experience;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.mapper.ExperienceMapper;
import com.portfolio.repository.ExperienceRepository;
import com.portfolio.service.admin.AdminExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminExperienceServiceImpl implements AdminExperienceService {

    private final ExperienceRepository expRepo;
    private final ExperienceMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<ExperienceResponse> getAllExperiences() {
        return expRepo.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public ExperienceResponse createExperience(ExperienceRequest r) {
        Experience e = new Experience();
        mapper.applyRequest(e, r);
        e.setActive(true);
        return mapper.toResponse(expRepo.save(e));
    }

    @Override
    public ExperienceResponse updateExperience(Long id, ExperienceRequest r) {

        Experience e = expRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experiência profissional com ID " + id + " não encontrada"));
        mapper.applyRequest(e, r);
        return mapper.toResponse(expRepo.save(e));
    }

    @Override
    public void deleteExperience(Long id) {

        Experience e = expRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experiência profissional com ID " + id + " não encontrada"));
        e.setActive(false);
        expRepo.save(e);
    }
}

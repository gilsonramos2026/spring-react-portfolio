package com.portfolio.service.publicapi.impl;

import com.portfolio.dto.response.ExperienceResponse;
import com.portfolio.mapper.ExperienceMapper;
import com.portfolio.repository.ExperienceRepository;
import com.portfolio.service.publicapi.PublicExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicExperienceServiceImpl implements PublicExperienceService {

    private final ExperienceRepository expRepo;
    private final ExperienceMapper mapper;

    @Override
    public List<ExperienceResponse> getExperiences() {
        return expRepo.findByActiveTrueOrderBySortOrderAscAndStartedAtDesc().stream()
                .map(mapper::toResponse)
                .toList();
    }
}

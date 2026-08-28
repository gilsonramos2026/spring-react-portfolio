package com.portfolio.service.publicapi.impl;

import com.portfolio.dto.response.EducationResponse;
import com.portfolio.mapper.EducationMapper;
import com.portfolio.repository.EducationRepository;
import com.portfolio.service.publicapi.PublicEducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicEducationServiceImpl implements PublicEducationService {

    private final EducationRepository eduRepo;
    private final EducationMapper mapper;

    @Override
    public List<EducationResponse> getEducations() {
        return eduRepo.findByActiveTrueOrderBySortOrderAscAndStartedAtDesc().stream()
                .map(mapper::toResponse)
                .toList();
    }
}

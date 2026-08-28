package com.portfolio.service.publicapi.impl;

import com.portfolio.dto.response.ProfileResponse;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.mapper.ProfileMapper;
import com.portfolio.repository.ProfileRepository;
import com.portfolio.service.publicapi.PublicProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicProfileServiceImpl implements PublicProfileService {

    private final ProfileRepository profileRepo;
    private final ProfileMapper mapper;

    @Override
    public ProfileResponse getProfile() {
        return profileRepo.findFirstByAvailableTrue()
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil profissional ativo não configurado no sistema"));
    }
}

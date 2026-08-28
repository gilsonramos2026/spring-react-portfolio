package com.portfolio.service.admin.impl;

import com.portfolio.dto.request.ProfileRequest;
import com.portfolio.dto.response.ProfileResponse;
import com.portfolio.entity.Profile;
import com.portfolio.mapper.ProfileMapper;
import com.portfolio.repository.ProfileRepository;
import com.portfolio.service.FileStorageService;
import com.portfolio.service.admin.AdminProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminProfileServiceImpl implements AdminProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper mapper;
    private final FileStorageService storage;

    @Override
    public ProfileResponse upsertProfile(ProfileRequest r) {
        Profile p = profileRepository.findFirstByAvailableTrue().orElse(new Profile());
        mapper.applyRequest(p, r);
        return mapper.toResponse(profileRepository.save(p));
    }

    @Override
    public ProfileResponse uploadAvatar(MultipartFile file) {
        Profile p = profileRepository.findFirstByAvailableTrue()
                .orElseThrow(() -> new com.portfolio.exception.ResourceNotFoundException("Perfil não configurado. Crie o perfil antes de enviar o avatar."));

        // Remove avatar anterior do disco se for um upload local
        if (p.getAvatarUrl() != null && p.getAvatarUrl().startsWith("/uploads/")) {
            storage.delete(p.getAvatarUrl());
        }

        String url = storage.store(file, "avatars");
        p.setAvatarUrl(url);
        return mapper.toResponse(profileRepository.save(p));
    }
}
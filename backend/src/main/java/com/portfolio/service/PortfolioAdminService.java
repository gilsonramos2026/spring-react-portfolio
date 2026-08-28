package com.portfolio.service;

import com.portfolio.dto.request.ProfileRequest;
import com.portfolio.dto.response.ProfileResponse;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

public class PortfolioAdminService {
    public ProfileResponse upsertProfile(@Valid ProfileRequest request) {
        return null;
    }

    public ProfileResponse uploadAvatar(MultipartFile file) {
        return null;
    }
}

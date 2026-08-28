package com.portfolio.service.admin;

import com.portfolio.dto.request.ProfileRequest;
import com.portfolio.dto.response.ProfileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AdminProfileService {
    ProfileResponse upsertProfile(ProfileRequest req);
    ProfileResponse uploadAvatar(MultipartFile file);
}

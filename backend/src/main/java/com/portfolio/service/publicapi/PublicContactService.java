package com.portfolio.service.publicapi;

import com.portfolio.dto.request.ContactRequest;
import jakarta.validation.Valid;

public class PublicContactService {
    public void sendContact(@Valid ContactRequest req, String remoteAddr) {
        return;
    }
}

package com.portfolio.service.publicapi;

import com.portfolio.dto.request.ContactRequest;

public interface PublicContactService {
    void sendContact(ContactRequest req, String ip);
}

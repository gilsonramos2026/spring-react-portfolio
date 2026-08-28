package com.portfolio.service;

import com.portfolio.dto.response.ContactResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public class AdminContactService {
    public List<ContactResponse> getContacts(String status) {
        return null;
    }

    public ContactResponse updateContactStatus(Long id, @NotBlank @Pattern(regexp = "new|read|replied|archived") String status) {
        return null;
    }

    public Long countNewContacts() {
        return null;
    }
}

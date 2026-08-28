package com.portfolio.service.admin;

import com.portfolio.dto.response.ContactResponse;
import java.util.List;

public interface AdminContactService {
    List<ContactResponse> getContacts(String status);
    ContactResponse updateContactStatus(Long id, String status);
    long countNewContacts();
}

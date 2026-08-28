package com.portfolio.service.publicapi.impl;

import com.portfolio.dto.request.ContactRequest;
import com.portfolio.entity.Contact;
import com.portfolio.repository.ContactRepository;
import com.portfolio.service.publicapi.PublicContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PublicContactServiceImpl implements PublicContactService {

    private final ContactRepository contactRepo;

    @Override
    public void sendContact(ContactRequest r, String ip) {
        Contact contact = Contact.builder()
                .name(r.getName())
                .email(r.getEmail())
                .subject(r.getSubject())
                .message(r.getMessage())
                .phone(r.getPhone())
                .ipAddress(ip)
                .status("new")
                .build();

        contactRepo.save(contact);
    }
}

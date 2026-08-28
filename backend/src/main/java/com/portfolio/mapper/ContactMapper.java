package com.portfolio.mapper;

import com.portfolio.dto.response.ContactResponse;
import com.portfolio.entity.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public ContactResponse toResponse(Contact c) {
        if (c == null) return null;
        return ContactResponse.builder()
                .id(c.getId())
                .name(c.getName())
                .email(c.getEmail())
                .subject(c.getSubject())
                .message(c.getMessage())
                .phone(c.getPhone())
                .status(c.getStatus())
                .createdAt(c.getCreatedAt())
                .build();
    }
}


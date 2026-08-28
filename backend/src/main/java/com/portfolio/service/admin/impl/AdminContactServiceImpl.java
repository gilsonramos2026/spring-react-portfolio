package com.portfolio.service.admin.impl;

import com.portfolio.dto.response.ContactResponse;
import com.portfolio.entity.Contact;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.mapper.ContactMapper;
import com.portfolio.repository.ContactRepository;
import com.portfolio.service.admin.AdminContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminContactServiceImpl implements AdminContactService {

    private final ContactRepository contactRepo;
    private final ContactMapper mapper;

    @Override
    @Transactional(readOnly=true)
    public List<ContactResponse> getContacts(String status){
        var l=(status!=null&&!status
                .isBlank())?contactRepo
                .findByStatusOrderByCreatedAtDesc(status):contactRepo.findAllByOrderByCreatedAtDesc();
        return l.stream().map(mapper::toResponse).toList();
    }
    @Override
    public ContactResponse updateContactStatus(Long id, String status){
        Contact c=contactRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Contato",id));
        c.setStatus(status); return mapper.toResponse(contactRepo.save(c));
    }

    @Override
    @Transactional(readOnly=true)
    public long countNewContacts(){
        return contactRepo.countByStatus("new");
    }

}

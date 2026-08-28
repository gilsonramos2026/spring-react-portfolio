package com.portfolio.service.admin.impl;

import com.portfolio.dto.request.TestimonialRequest;
import com.portfolio.dto.response.TestimonialResponse;
import com.portfolio.entity.Testimonial;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.mapper.TestimonialMapper;
import com.portfolio.repository.TestimonialRepository;
import com.portfolio.service.admin.AdminTestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class AdminTestimonialServiceImpl implements AdminTestimonialService {

    private final TestimonialRepository testRepo;
    private final TestimonialMapper mapper;

    @Override
    @Transactional(readOnly=true)
    public List<TestimonialResponse> getAllTestimonials(){
        return testRepo.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public TestimonialResponse createTestimonial(TestimonialRequest r){
        Testimonial t=new Testimonial();
        mapper.applyRequest(t,r);
        return mapper.toResponse(testRepo.save(t));
    }

    @Override
    public TestimonialResponse updateTestimonial(Long id, TestimonialRequest r){
        Testimonial t=testRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Testemunho",id));
        mapper.applyRequest(t,r);
        return mapper.toResponse(testRepo.save(t));
    }

    @Override
    public void deleteTestimonial(Long id){
        testRepo.deleteById(id);
    }

}

package com.portfolio.service;

import com.portfolio.dto.request.TestimonialRequest;
import com.portfolio.dto.response.TestimonialResponse;
import jakarta.validation.Valid;

import java.util.List;

public class AdminTestimonialService {
    public List<TestimonialResponse> getAllTestimonials() {
        return null;
    }

    public TestimonialResponse createTestimonial(@Valid TestimonialRequest r) {
        return null;
    }

    public TestimonialResponse updateTestimonial(Long id, @Valid TestimonialRequest r) {
        return null;
    }

    public void deleteTestimonial(Long id) {
        return;
    }
}

package com.portfolio.service.admin;

import com.portfolio.dto.request.TestimonialRequest;
import com.portfolio.dto.response.TestimonialResponse;
import java.util.List;

public interface AdminTestimonialService {
    List<TestimonialResponse> getAllTestimonials();
    TestimonialResponse createTestimonial(TestimonialRequest req);
    TestimonialResponse updateTestimonial(Long id, TestimonialRequest req);
    void deleteTestimonial(Long id);
}

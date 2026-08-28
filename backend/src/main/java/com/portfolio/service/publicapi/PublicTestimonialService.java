package com.portfolio.service.publicapi;

import com.portfolio.dto.response.TestimonialResponse;
import java.util.List;

public interface PublicTestimonialService {
    List<TestimonialResponse> getTestimonials(Boolean featured);
}

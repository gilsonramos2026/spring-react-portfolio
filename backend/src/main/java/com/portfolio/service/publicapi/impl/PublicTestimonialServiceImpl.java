package com.portfolio.service.publicapi.impl;

import com.portfolio.dto.response.TestimonialResponse;
import com.portfolio.mapper.TestimonialMapper;
import com.portfolio.repository.TestimonialRepository;
import com.portfolio.service.publicapi.PublicTestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicTestimonialServiceImpl implements PublicTestimonialService {

    private final TestimonialRepository testRepo;
    private final TestimonialMapper mapper;

    @Override
    public List<TestimonialResponse> getTestimonials(Boolean featured) {
        var list = Boolean.TRUE.equals(featured)
                ? testRepo.findByActiveTrueAndFeaturedTrueOrderBySortOrderAsc()
                : testRepo.findByActiveTrueOrderBySortOrderAsc();
        return list.stream().map(mapper::toResponse).toList();
    }
}

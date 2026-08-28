package com.portfolio.service.publicapi.impl;

import com.portfolio.dto.response.SkillResponse;
import com.portfolio.entity.Skill;
import com.portfolio.mapper.SkillMapper;
import com.portfolio.repository.SkillRepository;
import com.portfolio.service.publicapi.PublicSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicSkillServiceImpl implements PublicSkillService {

    private final SkillRepository skillRepo;
    private final SkillMapper mapper;

    @Override
    public Map<String, List<SkillResponse>> getSkillsGrouped() {
        return skillRepo.findByActiveTrueOrderBySortOrderAscAndNameAsc().stream()
                .collect(Collectors.groupingBy(
                        Skill::getCategory,
                        LinkedHashMap::new,
                        Collectors.mapping(mapper::toResponse, Collectors.toList())
                ));
    }
}

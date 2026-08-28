package com.portfolio.service.admin.impl;

import com.portfolio.dto.request.SkillRequest;
import com.portfolio.dto.response.SkillResponse;
import com.portfolio.entity.Skill;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.mapper.SkillMapper;
import com.portfolio.repository.SkillRepository;
import com.portfolio.service.admin.AdminSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminSkillServiceImpl implements AdminSkillService {

    private final SkillRepository skillRepo;
    private final SkillMapper mapper;

    @Override
    @Transactional(readOnly=true)
    public List<SkillResponse> getAllSkills(){
        return skillRepo.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public SkillResponse createSkill(SkillRequest r){
        Skill s=new Skill(); mapper.applyRequest(s,r);
        return mapper.toResponse(skillRepo.save(s));
    }

    @Override
    public SkillResponse updateSkill(Long id, SkillRequest r){
        Skill s=skillRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Skill",id));
        mapper.applyRequest(s,r);
        return mapper.toResponse(skillRepo.save(s));
    }

    @Override public void deleteSkill(Long id){
        skillRepo.deleteById(id);
    }

}

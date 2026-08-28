package com.portfolio.service.admin;

import com.portfolio.dto.request.SkillRequest;
import com.portfolio.dto.response.SkillResponse;
import java.util.List;

public interface AdminSkillService {
    List<SkillResponse> getAllSkills();
    SkillResponse createSkill(SkillRequest req);
    SkillResponse updateSkill(Long id, SkillRequest req);
    void deleteSkill(Long id);
}

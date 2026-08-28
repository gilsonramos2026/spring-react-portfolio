package com.portfolio.service.publicapi;

import com.portfolio.dto.response.SkillResponse;
import java.util.List;
import java.util.Map;

public interface PublicSkillService {
    Map<String, List<SkillResponse>> getSkillsGrouped();
}

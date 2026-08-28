package com.portfolio.repository;

import com.portfolio.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {

    @Query("SELECT e FROM Education e WHERE e.active = true ORDER BY e.sortOrder ASC, e.startedAt DESC")
    List<Education> findByActiveTrueOrderBySortOrderAscAndStartedAtDesc();
}
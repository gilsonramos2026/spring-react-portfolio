package com.portfolio.repository;

import com.portfolio.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    @Query("SELECT ex FROM Experience ex WHERE ex.active = true ORDER BY ex.sortOrder ASC, ex.startedAt DESC")
    List<Experience> findByActiveTrueOrderBySortOrderAscAndStartedAtDesc();
}
package com.portfolio.repository;

import com.portfolio.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByActiveTrueOrderBySortOrderAscAndCreatedAtDesc();

    List<Project> findByActiveTrueAndFeaturedTrueOrderBySortOrderAsc();
    Optional<Project> findBySlugAndActiveTrue(String slug);
}

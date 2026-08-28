package com.portfolio.repository;

import com.portfolio.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p WHERE p.active = true ORDER BY p.sortOrder ASC, p.createdAt DESC")
    List<Project> findByActiveTrueOrderBySortOrderAscAndCreatedAtDesc();

    List<Project> findByActiveTrueAndFeaturedTrueOrderBySortOrderAsc();

    Optional<Project> findBySlugAndActiveTrue(String slug);
}
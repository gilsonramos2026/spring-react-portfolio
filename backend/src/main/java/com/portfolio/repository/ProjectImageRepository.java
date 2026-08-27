package com.portfolio.repository;

import com.portfolio.entity.ProjectImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectImageRepository extends JpaRepository<ProjectImage, Long> {

    List<ProjectImage> findByProjectIdOrderBySortOrderAsc(Long projectId);

    /** Safe ownership check — runs as SQL, never touches the LAZY project proxy */
    boolean existsByIdAndProjectId(Long id, Long projectId);

    /** Next sort order without loading the full collection */
    @Query("SELECT COALESCE(MAX(i.sortOrder), -1) + 1 FROM ProjectImage i WHERE i.project.id = :projectId")
    int nextSortOrder(Long projectId);

    long countByProjectId(Long projectId);
}

package com.portfolio.repository;

import com.portfolio.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {

    @Query("SELECT c FROM Certification c WHERE c.active = true ORDER BY c.sortOrder ASC, c.issuedAt DESC")
    List<Certification> findByActiveTrueOrderBySortOrderAscAndIssuedAtDesc();
}
package com.portfolio.repository;

import com.portfolio.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByOrderByCreatedAtDesc();
    List<Contact> findByStatusOrderByCreatedAtDesc(String status);
    long countByStatus(String status);
}


package com.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="contacts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false,length=100)
    private String name;

    @Column(nullable=false,length=150)
    private String email;

    @Column(length=200)
    private String subject;

    @Column(columnDefinition="TEXT",nullable=false)
    private String message;

    @Column(length=30)
    private String phone;

    @Builder.Default
    private String status="new";

    @Column(name="ip_address",length=45)
    private String ipAddress;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @PrePersist protected void onCreate(){
        createdAt=LocalDateTime.now();
    }
}

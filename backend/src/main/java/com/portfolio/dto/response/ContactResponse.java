package com.portfolio.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponse {

    private Long id;
    private String name;
    private String email;
    private String subject;
    private String message;
    private String phone;
    private String status;
    private LocalDateTime createdAt;
}

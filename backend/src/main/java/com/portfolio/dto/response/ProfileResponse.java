package com.portfolio.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    private Long id;
    private String name;
    private String title;
    private String tagline;
    private String bio;
    private String email;
    private String phone;
    private String location;
    private String avatarUrl;
    private String resumeUrl;
    private String githubUrl;
    private String linkedinUrl;
    private String instagramUrl;
    private String websiteUrl;
    private Integer yearsExp;
    private Boolean available;
    private LocalDateTime updateAt;
}

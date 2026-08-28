package com.portfolio.dto.response;

import jakarta.validation.constraints.AssertFalse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {

    private String message;

}

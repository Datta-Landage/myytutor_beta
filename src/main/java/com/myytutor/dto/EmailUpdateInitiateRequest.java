package com.myytutor.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailUpdateInitiateRequest {
    private String newEmail;
}

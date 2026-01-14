package com.myytutor.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailUpdateVerifyRequest {
    private String otp;
}

package com.example.rdk.dto.mobile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MAuthorizeResponseDto {
    private int status;
    private String token;
    private int companyId;
    private String login;
    private String password;
}

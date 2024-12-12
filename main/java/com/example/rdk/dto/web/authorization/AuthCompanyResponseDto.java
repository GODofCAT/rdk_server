package com.example.rdk.dto.web.authorization;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthCompanyResponseDto {
    private String token;
    private int companyId;
    private String companyName;
}

package com.example.rdk.dto.web.authorization;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthAdminResponseDto {
    private String token;
}

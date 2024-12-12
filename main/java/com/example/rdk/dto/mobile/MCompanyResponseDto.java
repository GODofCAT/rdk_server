package com.example.rdk.dto.mobile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MCompanyResponseDto {
    private int status;
    private String login;
    private String password;
    private int isActive;
}

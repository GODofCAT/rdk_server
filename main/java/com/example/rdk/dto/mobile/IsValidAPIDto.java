package com.example.rdk.dto.mobile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IsValidAPIDto {
    private int status;
    private boolean isValid;
}

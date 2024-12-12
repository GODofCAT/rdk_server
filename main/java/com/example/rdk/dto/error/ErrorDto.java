package com.example.rdk.dto.error;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class ErrorDto {
    private String date;
    private String message;
}

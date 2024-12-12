package com.example.rdk.dto.mobile;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MProcessResponseDto {
    private int status;
    private String message;
    private List<ProcessContainer> content;
}

package com.example.rdk.dto.mobile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDto {
    private int status;
    private String content;
}

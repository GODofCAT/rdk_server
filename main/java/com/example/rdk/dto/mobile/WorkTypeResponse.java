package com.example.rdk.dto.mobile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkTypeResponse {
    private int status;
    private int id;
}

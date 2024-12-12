package com.example.rdk.dto.mobile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProcessContainer {
    private String name;
    private int mobileId;
}

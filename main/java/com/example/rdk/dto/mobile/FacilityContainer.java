package com.example.rdk.dto.mobile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FacilityContainer {
    private int id;
    private String name;
    private int isActive;
}

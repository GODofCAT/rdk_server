package com.example.rdk.dto.mobile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DrugContainer {
    private String name;
    private String ingredients;
}

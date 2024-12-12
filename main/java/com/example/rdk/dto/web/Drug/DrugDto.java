package com.example.rdk.dto.web.Drug;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DrugDto {
    private int id;
    private String name;
    private String ingredients;
}

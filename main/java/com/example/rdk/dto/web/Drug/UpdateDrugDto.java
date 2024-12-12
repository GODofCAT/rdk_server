package com.example.rdk.dto.web.Drug;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UpdateDrugDto {
    private int id;
    private String name;
    private String ingredients;
}

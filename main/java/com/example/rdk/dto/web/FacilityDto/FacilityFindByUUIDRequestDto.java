package com.example.rdk.dto.web.FacilityDto;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;

@Data
public class FacilityFindByUUIDRequestDto {
    @NotEmpty(message = "поле uuid должно быть заполнено")
    private String uuid;
}

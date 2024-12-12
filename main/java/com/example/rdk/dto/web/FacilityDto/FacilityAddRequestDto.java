package com.example.rdk.dto.web.FacilityDto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;

@Data
public class FacilityAddRequestDto {
    @NotEmpty(message = "поле с именем должно быть заполнено")
    @Size(max = 100, message = "максимально допустимое число символов 100")
    private String name;
    @NotEmpty(message = "поле адрес должно быть заполнено")
    private String address;
    @Size(max = 10000, message = "максимально допустимое число символов 10000")
    private String note;

    private int isActive;
    private int companyId;
}

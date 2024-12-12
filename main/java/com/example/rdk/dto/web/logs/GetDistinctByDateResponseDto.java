package com.example.rdk.dto.web.logs;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class GetDistinctByDateResponseDto {
    @NotEmpty(message = "поле дата должно быть заполнено")
    private String date;
}

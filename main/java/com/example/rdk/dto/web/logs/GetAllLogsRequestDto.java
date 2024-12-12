package com.example.rdk.dto.web.logs;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
public class GetAllLogsRequestDto {
    @NotEmpty(message = "поле дата должно быть заполнено")
    private String date;
    private int workId;
    private int facilityId;
}

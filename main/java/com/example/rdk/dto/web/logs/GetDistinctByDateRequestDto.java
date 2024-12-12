package com.example.rdk.dto.web.logs;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetDistinctByDateRequestDto {
    private int workId;
    private int facilityId;
}

package com.example.rdk.dto.web.logs;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class LogsDto {
    private int id;
    private int controlNum;
    private String controlNumStatus;
    private String date;
    private String tagNum;
    private int companyId;
    private int facilityId;
    private int workId;
    private String companyName;
    private String workName;
    private String facilityName;
    private String processName;
}

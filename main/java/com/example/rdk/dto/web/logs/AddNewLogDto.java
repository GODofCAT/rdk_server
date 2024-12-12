package com.example.rdk.dto.web.logs;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;

@Data
public class AddNewLogDto {
    @NotEmpty
    private int controlNum;
    @NotEmpty
    private String controlNumStatus;
    @NotEmpty
    private String date;
    @NotEmpty
    private String tagNum;
    @NotEmpty
    private int companyId;
    @NotEmpty
    private int facilityId;
    @NotEmpty
    private int workId;
    @NotEmpty
    private String uuid;
    private String processName;
}

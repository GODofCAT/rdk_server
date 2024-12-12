package com.example.rdk.dto.web.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CompanyDto {
    private int id;
    private String name;
    private String inn;
    private String note;
    private String login;
    private String password;
    private int isActive;
}

package com.example.rdk.dto.mobile;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MLogResponse {
    private int status;
    private List<String> uuids;
}

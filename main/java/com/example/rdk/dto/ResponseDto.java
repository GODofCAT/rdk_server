package com.example.rdk.dto;

import lombok.Builder;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

@Data
@Builder
public class ResponseDto {
    private int status;
    private String msgType;
    private Object content;
}

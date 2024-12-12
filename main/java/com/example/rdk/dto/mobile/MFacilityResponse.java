package com.example.rdk.dto.mobile;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MFacilityResponse{
    private int status;
    private List<FacilityContainer> facilities;
}

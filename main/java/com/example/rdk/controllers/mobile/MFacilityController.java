package com.example.rdk.controllers.mobile;

import com.example.rdk.dto.mobile.MFacilityResponse;
import com.example.rdk.service.mobile.FacilityMobileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping(path = "/mobile/facility")
@AllArgsConstructor
public class MFacilityController {
        private final FacilityMobileService facilityService;

    @GetMapping(value = "/get-all-by-company-id/{id}")
    public MFacilityResponse getAllFacilitiesByCompanyId(@RequestHeader String authorization, @PathVariable int id) throws ParseException {
        return facilityService.getFacilityByCompanyId(authorization,id);
    }
}

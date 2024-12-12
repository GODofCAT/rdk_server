package com.example.rdk.controllers.web;

import com.example.rdk.dto.web.FacilityDto.FacilityAddRequestDto;
import com.example.rdk.dto.web.FacilityDto.FacilityDto;
import com.example.rdk.dto.ResponseDto;
import com.example.rdk.service.web.FacilityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping(path = "/facility")
@AllArgsConstructor
public class FacilityController {
    private final FacilityService facilityService;
    @GetMapping(value = "/get-all-by-company-id/{id}")
    public ResponseDto getAllFacilitiesByCompanyId(@RequestHeader String Authorization, @PathVariable int id) throws ParseException {
        return facilityService.getAllByCompanyId(Authorization,id);
    }

    @PostMapping(value = "/add-new")
    public ResponseDto addNewFacility(@RequestHeader String Authorization, @RequestBody @Valid FacilityAddRequestDto facilityAddRequestDto) throws ParseException {
        return facilityService.addNewFacility(Authorization,facilityAddRequestDto);
    }

    @PutMapping(value = "/update-by-id")
    public ResponseDto updateFacilityById(@RequestHeader String Authorization, @RequestBody @Valid FacilityDto facilityDto) throws ParseException {
        return facilityService.updateFacilityById(Authorization,facilityDto);
    }

    @GetMapping(value = "/get-by-UUID/{uuid}")
    public ResponseDto getFacilityByUUID(@RequestHeader String Authorization, @PathVariable String uuid) throws ParseException {
        return facilityService.getFacilityByUUID(Authorization,uuid);
    }

    @GetMapping(value = "/get-by-id/{id}")
    public ResponseDto getFacilityByUUID(@RequestHeader String Authorization, @PathVariable int id) throws ParseException {
        return facilityService.getById(Authorization,id);
    }

}

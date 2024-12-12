package com.example.rdk.controllers.mobile;

import com.example.rdk.dto.mobile.MDrugResponseDto;
import com.example.rdk.dto.mobile.MProcessResponseDto;
import com.example.rdk.service.mobile.DrugMobileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping(path = "/mobile/drug")
@AllArgsConstructor
public class MDrugController {
    private final DrugMobileService drugMobileService;

    @GetMapping(value = "/get-all")
    public MDrugResponseDto getAllDrugs(@RequestHeader String Authorization) throws ParseException {
        return drugMobileService.getAllDrugs(Authorization);
    }

        @GetMapping(value = "/get-all-process")
    public MProcessResponseDto getAllProcess(@RequestHeader String Authorization) throws ParseException {
        return drugMobileService.getAllProcess(Authorization);
    }

}

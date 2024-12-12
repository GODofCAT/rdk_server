package com.example.rdk.controllers.mobile;


import com.example.rdk.dto.mobile.MCompanyResponseDto;
import com.example.rdk.service.mobile.CompanyMobileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping(path = "/mobile/company")
@AllArgsConstructor
public class MCompanyController {
    private final CompanyMobileService companyMobileService;

    @PostMapping(value = "/getById/{id}")
    public MCompanyResponseDto authorizeMobile(@RequestHeader String Authorization, @PathVariable int id) throws ParseException {
        return companyMobileService.getById(Authorization,id);
    }
}

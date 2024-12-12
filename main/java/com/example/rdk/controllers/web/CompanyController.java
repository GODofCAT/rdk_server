package com.example.rdk.controllers.web;

import com.example.rdk.service.web.CompanyService;
import com.example.rdk.dto.web.company.AddCompanyRequestDto;
import com.example.rdk.dto.ResponseDto;
import com.example.rdk.dto.web.company.UpdateCompanyRequestDto;
import com.example.rdk.repositories.CompanyRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping(path = "/company")
@AllArgsConstructor
public class CompanyController {
    private final CompanyRepository companyRepository;
    private final CompanyService companyService;

    @GetMapping(value = "/get-all")
    public ResponseDto getAllCompanyes(@RequestHeader String Authorization) throws ParseException {
        return companyService.getAll(Authorization);
    }

    @GetMapping(value = "/test")
    public String test(){
        return "";
    }

    @PostMapping(value = "/add-new")
    public ResponseDto addNewCompany(@RequestHeader String Authorization, @RequestBody @Valid AddCompanyRequestDto addCompanyRequestDto) throws ParseException, MethodArgumentNotValidException {
        return companyService.addCompany(Authorization,addCompanyRequestDto);
    }

    @PutMapping(value = "/update-by-id")
    public ResponseDto updateCompanyById(@RequestHeader String Authorization, @RequestBody @Valid UpdateCompanyRequestDto updateCompanyRequestDto) throws ParseException {
        return companyService.updateCompany(Authorization,updateCompanyRequestDto);
    }

    @GetMapping(value = "/get-by-id/{id}")
    public ResponseDto getCompanyById(@RequestHeader String Authorization, @PathVariable int id) throws ParseException {
        return  companyService.getById(Authorization,id);
    }
}

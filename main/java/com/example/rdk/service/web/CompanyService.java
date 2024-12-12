package com.example.rdk.service.web;

import com.example.rdk.controllers.utils.MsgTypes;
import com.example.rdk.dto.error.ErrorDto;
import com.example.rdk.dto.web.company.AddCompanyRequestDto;
import com.example.rdk.dto.web.company.CompanyDto;
import com.example.rdk.dto.ResponseDto;
import com.example.rdk.dto.web.company.UpdateCompanyRequestDto;
import com.example.rdk.models.Company;
import com.example.rdk.models.Facility;
import com.example.rdk.repositories.CompanyRepository;
import com.example.rdk.repositories.FacilityRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyService {
    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;
    private final FacilityRepository facilityRepository;
    private final ApiService apiService;

    public ResponseDto addCompany(String token,AddCompanyRequestDto addCompanyRequestDto) throws ParseException {
        if (apiService.checkToken(token)){
            return ResponseDto.builder()
                    .status(401)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Неверный API токен").build()).build();
        }
        


        if (companyRepository.findByLogin(addCompanyRequestDto.getLogin()).isPresent()){
            return ResponseDto.builder().status(400)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Компания с таким логином уже существует").build()).build();
        }

        if (companyRepository.findByInn(addCompanyRequestDto.getInn()).isPresent()){
            return ResponseDto.builder().status(400)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Компания с таким инн уже существует").build()).build();
        }

        Company company = modelMapper.map(addCompanyRequestDto, Company.class);
        company.isActive = 0;
        companyRepository.saveAndFlush(company);
        CompanyDto companyDto =  CompanyDto.builder().id(company.id).name(company.name).inn(company.inn).note(company.note).login(company.login).password(company.password).isActive(company.isActive).build();
        return ResponseDto.builder()
                .status(200)
                .msgType("normal")
                .content(companyDto).build();
    }

    public ResponseDto updateCompany(String token,UpdateCompanyRequestDto  updateCompanyRequestDto) throws ParseException {
        if (apiService.checkToken(token)){
            return ResponseDto.builder()
                    .status(401)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Неверный API токен").build()).build();
        }
        Optional<Company> findCompany = companyRepository.findByLogin(updateCompanyRequestDto.getLogin());
        if (findCompany.isPresent() && findCompany.get().id != updateCompanyRequestDto.getId()){
            return ResponseDto.builder().status(400)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Компания с таким логином уже существует").build()).build();
        }
        findCompany = companyRepository.findByInn(updateCompanyRequestDto.getInn());
        if (findCompany.isPresent() && findCompany.get().id != updateCompanyRequestDto.getId()){
            return ResponseDto.builder().status(400)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Компания с таким инн уже существует").build()).build();
        }

        List<Facility> findFacility = facilityRepository.findAllByCompanyId(updateCompanyRequestDto.getId());

        if (updateCompanyRequestDto.getIsActive() == 1 && (findFacility.isEmpty() || !findFacility.stream().anyMatch(facility -> facility.isActive > 0))){
            return ResponseDto.builder()
                    .status(400)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Компания не имеет активных объектов").build()).build();
        }

        Company company = modelMapper.map(updateCompanyRequestDto, Company.class);

        if (companyRepository.findById(updateCompanyRequestDto.getId()) == null){
            return ResponseDto.builder()
                    .status(400)
                    .msgType(MsgTypes.error.toString())
                    .content(null).build();
        }

        companyRepository.save(company);
        CompanyDto companyDto =  CompanyDto.builder().id(company.id).name(company.name).inn(company.inn).note(company.note).login(company.login).password(company.password).isActive(company.isActive).build();

        return ResponseDto.builder()
                .status(200)
                .msgType(MsgTypes.normal.toString())
                .content(companyDto).build();
    }

    public ResponseDto getAll(String token) throws ParseException {
        if (apiService.checkToken(token)){
            return ResponseDto.builder()
                    .status(401)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Неверный API токен").build()).build();
        }

        List<CompanyDto> companyDtoList = companyRepository.findAllByOrderByIdAsc().stream().map(company -> CompanyDto.builder().id(company.id).name(company.name).inn(company.inn).note(company.note).login(company.login).password(company.password).isActive(company.isActive).build()).collect(Collectors.toList());
        return ResponseDto.builder()
                .status(200)
                .msgType(MsgTypes.normal.toString())
                .content(companyDtoList).build();
    }

    public ResponseDto getById(String token,int id) throws ParseException {
        if (apiService.checkToken(token)){
            return ResponseDto.builder()
                    .status(401)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Неверный API токен").build()).build();
        }
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty()){
            return ResponseDto.builder()
                    .status(400)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Компания с id = "+id+" не существует").build()).build();
        }
        Company company = optionalCompany.get();

        return ResponseDto.builder().status(200).msgType(MsgTypes.normal.toString()).content(CompanyDto.builder().id(company.getId()).inn(company.inn).note(company.note).password(company.password).login(company.login).name(company.name).build()).build();
    }

}

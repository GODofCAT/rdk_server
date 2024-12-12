package com.example.rdk.service.web;

import com.example.rdk.controllers.utils.MsgTypes;
import com.example.rdk.dto.error.ErrorDto;
import com.example.rdk.dto.web.FacilityDto.FacilityAddRequestDto;
import com.example.rdk.dto.web.FacilityDto.FacilityDto;
import com.example.rdk.dto.ResponseDto;
import com.example.rdk.models.Company;
import com.example.rdk.models.Facility;
import com.example.rdk.repositories.CompanyRepository;
import com.example.rdk.repositories.FacilityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FacilityService {
    private final FacilityRepository facilityRepository;
    private final CompanyRepository companyRepository;
    private final ApiService apiService;

    public ResponseDto getAllByCompanyId(String token,int companyId) throws ParseException {
        if (apiService.checkToken(token)){
            return ResponseDto.builder()
                    .status(401)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Неверный API токен").build()).build();
        }

       Optional<Company> optionalCompany = companyRepository.findById(companyId);

        if (optionalCompany.isEmpty()){
            return ResponseDto.builder().status(400).msgType(MsgTypes.normal.toString()).content(ErrorDto.builder().message("Компании с id = "+companyId+" не существует").build()).build();
        }

        Company company = optionalCompany.get();

        List<Facility> facilityList = facilityRepository.findAllByCompanyIdOrderByIdAsc(companyId);

        List<FacilityDto> facilityDtoList = facilityList.stream().map(facility -> FacilityDto.builder().id(facility.id).name(facility.name).uuid(facility.uuid).note(facility.note).address(facility.address).isActive(facility.isActive).companyId(facility.company.id).build()).collect(Collectors.toList());

        return  ResponseDto.builder()
                .status(200)
                .msgType(MsgTypes.normal.toString())
                .content(facilityDtoList).build();
    }

    public ResponseDto addNewFacility(String token,FacilityAddRequestDto facilityAddRequestDto) throws ParseException {
        if (apiService.checkToken(token)){
            return ResponseDto.builder()
                    .status(401)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Неверный API токен").build()).build();
        }

        Optional<Company> optionalCompany = companyRepository.findById(facilityAddRequestDto.getCompanyId());

        if (optionalCompany.isEmpty()){
            return ResponseDto.builder().status(400).msgType(MsgTypes.normal.toString()).content(ErrorDto.builder().message("Компании с id = "+facilityAddRequestDto.getCompanyId()+" не существует").build()).build();
        }

        Facility facility = new Facility(facilityAddRequestDto.getName(),facilityAddRequestDto.getAddress(),facilityAddRequestDto.getNote(),facilityAddRequestDto.getIsActive(), optionalCompany.get(), UUID.randomUUID().toString());

        facilityRepository.saveAndFlush(facility);
        return  ResponseDto.builder()
                .status(200)
                .msgType(MsgTypes.normal.toString())
                .content(FacilityDto.builder().id(facility.id).name(facility.name).uuid(facility.uuid).address(facility.address).note(facility.note).isActive(facility.isActive).companyId(facility.company.id).build()).build();
    }

    public ResponseDto updateFacilityById(String token,FacilityDto facilityDto) throws ParseException {
        if (apiService.checkToken(token)){
            return ResponseDto.builder()
                    .status(401)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Неверный API токен").build()).build();
        }

        Optional<Facility> optionalFacility = facilityRepository.findById(facilityDto.getId());
        if (optionalFacility.isEmpty()){
            return ResponseDto.builder()
                    .status(400)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Объекта с id = "+facilityDto.getId()+" не существует").build()).build();
        }

        Facility facility = optionalFacility.get();
        facility.setName(facilityDto.getName());
        facility.setNote(facilityDto.getNote());
        facility.setAddress(facilityDto.getAddress());
        facility.setIsActive(facilityDto.getIsActive());
        facility.setUuid(facilityDto.getUuid());

        facilityRepository.save(facility);
        FacilityDto facilityResponseDto = FacilityDto.builder().id(facility.id).name(facility.name).uuid(facility.uuid).address(facility.address).note(facility.note).isActive(facility.isActive).companyId(facility.company.getId()).build();

        List<Facility> findFacility = facilityRepository.findAllByCompanyId(facilityDto.getCompanyId());
        Company company = companyRepository.findById(facilityDto.getCompanyId()).get();
        if (company.getIsActive() == 1 && (findFacility.isEmpty() || !findFacility.stream().anyMatch(curfacility -> curfacility.isActive > 0))){
            company.setIsActive(0);
            companyRepository.save(company);
        }

        return ResponseDto.builder().status(200).msgType(MsgTypes.normal.toString()).content(facilityResponseDto).build();
    }

    public ResponseDto getFacilityByUUID(String token,String uuid) throws ParseException {
        if (apiService.checkToken(token)){
            return ResponseDto.builder()
                    .status(401)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Неверный API токен").build()).build();
        }

        Facility facility = facilityRepository.findByUuid(uuid);

        if (facility == null){
            return ResponseDto.builder()
                    .status(400)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Объекта с uuid = "+uuid+" не существует").build()).build();
        }

        FacilityDto facilityResponseDto = FacilityDto.builder().id(facility.id).name(facility.name).uuid(facility.uuid).address(facility.address).note(facility.note).isActive(facility.isActive).companyId(facility.company.getId()).build();
        return ResponseDto.builder().status(200).msgType(MsgTypes.normal.toString()).content(facilityResponseDto).build();
    }

    public ResponseDto getById(String token, int id) throws ParseException {
        if (apiService.checkToken(token)){
            return ResponseDto.builder()
                    .status(401)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Неверный API токен").build()).build();
        }

        Optional<Facility> optFacility = facilityRepository.findById(id);

        if (optFacility.isEmpty()){
            return ResponseDto.builder()
                    .status(400)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Объекта с id = "+id+" не существует").build()).build();
        }

        Facility facility = optFacility.get();

        FacilityDto facilityResponseDto = FacilityDto.builder().id(facility.id).name(facility.name).uuid(facility.uuid).address(facility.address).note(facility.note).isActive(facility.isActive).companyId(facility.company.getId()).build();
        return ResponseDto.builder().status(200).msgType(MsgTypes.normal.toString()).content(facilityResponseDto).build();
    }
}

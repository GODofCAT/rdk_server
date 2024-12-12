package com.example.rdk.service.mobile;

import com.example.rdk.dto.mobile.MCompanyResponseDto;
import com.example.rdk.models.Company;
import com.example.rdk.repositories.CompanyRepository;
import com.example.rdk.service.web.ApiService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class CompanyMobileService {
    private final CompanyRepository companyRepository;
    private final ApiService apiService;

    public MCompanyResponseDto getById(String token, int id) throws ParseException {
        if (apiService.checkToken(token)){
            log.error("mobile/companyService/getById | log error | token = " + token);
            return MCompanyResponseDto.builder()
                    .status(401)
                    .build();
        }

        try {
            Optional<Company> optionalCompany = companyRepository.findById(id);
            if (optionalCompany.isEmpty()) {
                log.error("mobile/companyService/getById | company not found ");
                return MCompanyResponseDto.builder()
                        .status(400)
                        .build();
            }
            Company company = optionalCompany.get();

            return MCompanyResponseDto.builder().status(200).password(company.password).login(company.login).isActive(company.isActive).build();
        }catch (Exception e){
            log.error("mobile/companyService/getById | exception | " + e.getMessage());
        }
        return  MCompanyResponseDto.builder().status(500).build();
    }
}

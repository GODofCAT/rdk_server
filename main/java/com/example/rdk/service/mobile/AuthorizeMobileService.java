package com.example.rdk.service.mobile;

import com.example.rdk.dto.mobile.IsValidAPIDto;
import com.example.rdk.dto.mobile.MAuthorizeResponseDto;
import com.example.rdk.dto.web.authorization.AuthRequestDto;
import com.example.rdk.models.Company;
import com.example.rdk.repositories.CompanyRepository;
import com.example.rdk.repositories.UserRepository;
import com.example.rdk.service.web.ApiService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
@AllArgsConstructor
public class AuthorizeMobileService {
    private final CompanyRepository companyRepository;
    private final ApiService apiService;

    public MAuthorizeResponseDto authorizationMobileCompany(AuthRequestDto authRequestDto){
        Company company = companyRepository.findByLoginAndPassword(authRequestDto.getLogin(),authRequestDto.getPassword());
        if(company == null){
            return MAuthorizeResponseDto.builder().status(400).token(null).companyId(-1).build();
        }

        if (company.isActive == 0){
            return MAuthorizeResponseDto.builder().status(402).token(null).companyId(-1).build();
        }

        return MAuthorizeResponseDto.builder().status(200).token(apiService.createToken()).companyId(company.id).login(company.login).password(company.password).build();
    }

    public IsValidAPIDto apiKeyIsValid(String token) throws ParseException {
        return IsValidAPIDto.builder().status(200).isValid(!apiService.checkToken(token)).build();
    }

}

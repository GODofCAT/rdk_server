package com.example.rdk.service.web;

import com.example.rdk.controllers.utils.MsgTypes;
import com.example.rdk.dto.error.ErrorDto;
import com.example.rdk.dto.web.authorization.AuthAdminResponseDto;
import com.example.rdk.dto.web.authorization.AuthCompanyResponseDto;
import com.example.rdk.dto.web.authorization.AuthRequestDto;
import com.example.rdk.dto.ResponseDto;
import com.example.rdk.dto.web.authorization.AuthUUIDResponseDto;
import com.example.rdk.models.Company;
import com.example.rdk.models.Facility;
import com.example.rdk.models.User;
import com.example.rdk.repositories.ApiKeyRepository;
import com.example.rdk.repositories.CompanyRepository;
import com.example.rdk.repositories.FacilityRepository;
import com.example.rdk.repositories.UserRepository;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Properties;


@Service
@AllArgsConstructor
public class AuthorizeService {
    private final ApiKeyRepository apiKeyRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final FacilityRepository facilityRepository;
    private final ApiService apiService;

    public ResponseDto authorizationByUserLoginAndPassword(AuthRequestDto authRequestDto){
        User user = userRepository.findByLoginAndPassword(authRequestDto.getLogin(), authRequestDto.getPassword());
        if (user == null){
           return ResponseDto.builder()
                   .status(401)
                   .msgType(MsgTypes.error.toString())
                   .content(ErrorDto.builder().message("Введен неправильный логин или пароль").build()).build();
        }

        return ResponseDto.builder().status(200).msgType(MsgTypes.normal.toString()).content(AuthAdminResponseDto.builder().token(apiService.createToken()).build()).build();
    }

    public ResponseDto authorizationWebCompany(AuthRequestDto authRequestDto){
        Company company = companyRepository.findByLoginAndPassword(authRequestDto.getLogin(),authRequestDto.getPassword());
        if(company == null){
            return ResponseDto.builder()
                    .status(400)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Введен неправильный логин или пароль").build()).build();
        }

        if (company.getIsActive() == 0){
            return ResponseDto.builder()
                    .status(400)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Компания не активна").build()).build();
        }


        return ResponseDto.builder().status(200).msgType(MsgTypes.normal.toString()).content(AuthCompanyResponseDto.builder().token(apiService.createToken()).companyId(company.id).companyName(company.name).build()).build();
    }

    public ResponseDto getApiByUUID(String uuid){
        Facility facility = facilityRepository.findByUuid(uuid);

        if (facility == null){
            return ResponseDto.builder()
                    .status(400)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Введен неверный uuid").build()).build();
        }

        return ResponseDto.builder().status(200).msgType(MsgTypes.normal.toString()).content(AuthUUIDResponseDto.builder().token(apiService.createToken()).facilityId(facility.id).facilityName(facility.name).build()).build();
    }

    public ResponseDto restorePassword() throws MessagingException {
        String to = "xxx";
        String from = "xxx";
        String password = "xxx";//utlc cuiy mdzf rvwe
        String host = "smtp.gmail.com";
        String port = "587";

        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.debug", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");


        Session session = Session.getDefaultInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from,password);
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("Востановление пароля");

            User restoreUser = userRepository.findAll().get(0);
            // actual mail body
            message.setText(" \nlogin: "+restoreUser.login+"\npassword: "+restoreUser.password);

            // Send message
            Transport.send(message); System.out.println("Email Sent successfully....");
        } catch (MessagingException mex){ mex.printStackTrace(); return ResponseDto.builder().status(500).msgType(MsgTypes.error.toString()).content(null).build();}

        return ResponseDto.builder().status(200).msgType(MsgTypes.normal.toString()).content(null).build();
    }

}

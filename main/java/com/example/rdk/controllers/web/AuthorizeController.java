package com.example.rdk.controllers.web;

import com.example.rdk.service.web.AuthorizeService;
import com.example.rdk.dto.web.authorization.AuthRequestDto;
import com.example.rdk.dto.ResponseDto;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/authorize")
@AllArgsConstructor
public class AuthorizeController {
    private final AuthorizeService authorizeService;

    @PostMapping(value = "/web-admin")
    public ResponseDto authorizeWebAdmin(@RequestBody @Valid AuthRequestDto authRequestDto){
        return authorizeService.authorizationByUserLoginAndPassword(authRequestDto);
    }

    @PostMapping(value = "/web-company")
    public ResponseDto authorizeWebCompany(@RequestBody @Valid AuthRequestDto authRequestDto){
        return authorizeService.authorizationWebCompany(authRequestDto);
    }

    @GetMapping(value = "/restore")
    public ResponseDto restorePassword() throws MessagingException {
        return authorizeService.restorePassword();
    }

    @GetMapping(value = "/get-API-by-UUID/{uuid}")
    public ResponseDto getApiByUUID(@PathVariable String uuid){
        return authorizeService.getApiByUUID(uuid);
    }

}

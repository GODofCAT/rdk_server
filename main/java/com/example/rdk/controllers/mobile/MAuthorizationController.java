package com.example.rdk.controllers.mobile;

import com.example.rdk.dto.mobile.IsValidAPIDto;
import com.example.rdk.dto.mobile.MAuthorizeResponseDto;
import com.example.rdk.dto.web.authorization.AuthRequestDto;
import com.example.rdk.service.mobile.AuthorizeMobileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping(path = "/mobile/authorize")
@AllArgsConstructor
public class MAuthorizationController {
    private final AuthorizeMobileService authorizeService;

    @PostMapping(value = "/web-mobile")
    public MAuthorizeResponseDto authorizeMobile(@RequestBody AuthRequestDto authRequestDto){
        return authorizeService.authorizationMobileCompany(authRequestDto);
    }

    @GetMapping(value = "/is-token-available/{token}")
    public IsValidAPIDto isTokenAvailable(@PathVariable String token) throws ParseException {
        return authorizeService.apiKeyIsValid(token);
    }
}

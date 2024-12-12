package com.example.rdk.controllers.mobile;

import com.example.rdk.dto.error.ErrorDto;
import com.example.rdk.dto.mobile.MessageDto;
import com.example.rdk.service.mobile.ErrorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping(path = "/mobile/error")
@AllArgsConstructor
public class MErrorController {
    private final ErrorService errorService;
    @PostMapping(value = "/add-error")
    public MessageDto addNewError(@RequestBody ErrorDto errorDto) throws ParseException {
        return errorService.addNewError(errorDto.getMessage(), errorDto.getDate());
    }
}

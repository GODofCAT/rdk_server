package com.example.rdk.service.mobile;

import com.example.rdk.dto.mobile.MessageDto;
import com.example.rdk.models.Error;
import com.example.rdk.repositories.ErrorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
@Slf4j
@AllArgsConstructor
public class ErrorService {
    private final ErrorRepository errorRepository;

    public MessageDto addNewError(String error, String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy ss:mm:hh");

        log.info("mobile/addNewError | error = "+error);

        errorRepository.saveAndFlush(new Error(error, simpleDateFormat.parse(date)));
        return MessageDto.builder().status(200).content("ok").build();

    }
}

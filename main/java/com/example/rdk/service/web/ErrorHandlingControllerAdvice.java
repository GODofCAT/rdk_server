package com.example.rdk.service.web;

import com.example.rdk.controllers.utils.MsgTypes;
import com.example.rdk.dto.ResponseDto;
import com.example.rdk.dto.error.ValidationErrorResponse;
import com.example.rdk.dto.error.Violation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ErrorHandlingControllerAdvice {
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto onConstraintValidationException(
            ConstraintViolationException e
    ) {
        List<String> message = e.getConstraintViolations().stream().map(constraintViolation -> e.getMessage()).toList();
        ResponseDto responseDto = ResponseDto.builder().status(400).msgType(MsgTypes.error.toString()).content(message).build();
        log.error("controllerAdvice | onConstraintValidationException | "+ responseDto.toString());
        return responseDto;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDto onMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        List<String> message = e.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
        ResponseDto responseDto = ResponseDto.builder().status(400).msgType(MsgTypes.error.toString()).content(message.get(0)).build();
        log.error("controllerAdvice | onMethodArgumentNotValidException | "+ responseDto.toString());
        return responseDto;
    }

}

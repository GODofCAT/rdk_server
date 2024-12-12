package com.example.rdk.service.web;

import com.example.rdk.models.ApiKey;
import com.example.rdk.repositories.ApiKeyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ApiService {
    private final ApiKeyRepository apiKeyRepository;

    public String createToken(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();

        ApiKey apiKey = new ApiKey(UUID.randomUUID().toString(), simpleDateFormat.format(date));
        apiKeyRepository.saveAndFlush(apiKey);
        return  apiKey.apiKey;
    }

    public boolean checkToken(String token) throws ParseException {
        ApiKey apiKey = apiKeyRepository.findByApiKey(token);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        Date expirationDate = simpleDateFormat.parse(apiKey.expirationTime);
        long timeDifference = 604800000;
        if (date.getTime() - expirationDate.getTime() < timeDifference){
            System.out.println(date.compareTo(expirationDate));
            System.out.println(date.getTime() - expirationDate.getTime());
            return false;
        }
        System.out.println(date.compareTo(expirationDate));
        System.out.println(date.getTime() - expirationDate.getTime());
        return true;
    }
}

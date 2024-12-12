package com.example.rdk.service.mobile;


import com.example.rdk.dto.mobile.DrugContainer;
import com.example.rdk.dto.mobile.MDrugResponseDto;
import com.example.rdk.dto.mobile.MProcessResponseDto;
import com.example.rdk.dto.mobile.ProcessContainer;
import com.example.rdk.models.Drug;
import com.example.rdk.models.Process;
import com.example.rdk.repositories.DrugRepository;
import com.example.rdk.repositories.ProcessRepository;
import com.example.rdk.service.web.ApiService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class DrugMobileService {
    private final DrugRepository drugRepository;
    private final ProcessRepository processRepository;
    private final ApiService apiService;

    public MDrugResponseDto getAllDrugs(String token) throws ParseException {
        if (apiService.checkToken(token)) {
            log.error("mobile/getAllDrugs | log error | token = " + token);
            return MDrugResponseDto.builder().status(401).
                    message("Неверный API токен").content(null).build();
        }

        try {
            List<Drug> drugList = drugRepository.findAll();
            List<DrugContainer> drugContainer = new ArrayList<>();
            for (Drug item : drugList) {
                drugContainer.add(DrugContainer.builder().name(item.getName()).ingredients(item.getIngredients()).build());
            }

            return MDrugResponseDto.builder().status(200).message("normal").content(drugContainer).build();
        }catch (Exception e){
            log.error("mobile/getAllDrugs | tc error | exception = " + e.getMessage());
        }
        return MDrugResponseDto.builder().status(500).message("error").content(null).build();
    }

    public MProcessResponseDto getAllProcess(String token) throws ParseException {
        if (apiService.checkToken(token)){
            log.error("mobile/getAllProcess | log error | token = " + token);
            return MProcessResponseDto.builder().status(401).
                    message("Неверный API токен").content(null).build();
        }

        try {
            List<Process> processList = processRepository.findAll();
            List<ProcessContainer> processContainer = new ArrayList<>();
            for (Process item : processList) {
                processContainer.add(ProcessContainer.builder().name(item.getName()).mobileId(item.getMobileId()).build());
            }

            return MProcessResponseDto.builder().status(200).message("normal").content(processContainer).build();
        }catch (Exception e){
            log.error("mobile/getAllProcess | exception = "+e.getMessage());
        }
        return MProcessResponseDto.builder().status(500).message("error").content(null).build();
    }


}

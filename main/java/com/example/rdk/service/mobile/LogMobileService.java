package com.example.rdk.service.mobile;

import com.example.rdk.dto.mobile.MLogResponse;
import com.example.rdk.dto.mobile.WorkTypeResponse;
import com.example.rdk.dto.web.logs.AddNewLogDto;
import com.example.rdk.models.Company;
import com.example.rdk.models.Facility;
import com.example.rdk.models.Log;
import com.example.rdk.models.WorkType;
import com.example.rdk.repositories.CompanyRepository;
import com.example.rdk.repositories.FacilityRepository;
import com.example.rdk.repositories.LogRepository;
import com.example.rdk.repositories.WorkTypeRepository;
import com.example.rdk.service.web.ApiService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class LogMobileService {
    private final WorkTypeRepository workTypeRepository;
    private final FacilityRepository facilityRepository;
    private final CompanyRepository companyRepository;
    private final LogRepository logRepository;
    private final ApiService apiService;

    public MLogResponse addNewLog(String token, List<AddNewLogDto> requestDto) throws ParseException {
        if (apiService.checkToken(token)){
            log.error("mobile/addNewLog | log error | token = "+token);
            return MLogResponse.builder()
                    .status(401).build();
        }
        try {
            List<String> uuids = new ArrayList<>();

            for (AddNewLogDto item : requestDto) {
                Optional<Company> companyOptional = companyRepository.findById(item.getCompanyId());
                Optional<Facility> facilityOptional = facilityRepository.findById(item.getFacilityId());
                Optional<WorkType> workTypeOptional = workTypeRepository.findById(item.getWorkId());
                Log newLog;

                if (companyOptional.get().isActive == 0) {
                    log.error("mobile/addNewLog | company not active");
                    return MLogResponse.builder().status(400).uuids(uuids).build();
                }

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                boolean isMoreThan1;
                List<Log> findLog = logRepository.findByDateAndTagNumOrderByIdAsc(simpleDateFormat.parse(item.getDate()), item.getTagNum());
                if (!findLog.isEmpty()) {
                    if (findLog.size() > 1){
                        newLog = findLog.get(findLog.size() - 1);
                        isMoreThan1  = true;
                    }else {
                        newLog = findLog.get(0);
                        isMoreThan1  = false;
                    }

                    newLog.setUuid(item.getUuid());
                    newLog.setProcessName(item.getProcessName());
                    newLog.setWorkType(workTypeOptional.get());
                    newLog.setControlNum(item.getControlNum());
                    newLog.setControlNumStatus(item.getControlNumStatus());
                    newLog.setFacility(facilityOptional.get());

                    if (isMoreThan1){
                        for (int i = 0; i <findLog.size() - 1 ; i++) {
                            logRepository.delete(findLog.get(i));
                        }
                    }
                } else {
                    newLog = new Log(item.getControlNum(), item.getControlNumStatus(), simpleDateFormat.parse(item.getDate()), item.getTagNum(), workTypeOptional.get(), companyOptional.get(), facilityOptional.get(), item.getUuid(), item.getProcessName());
                }
                logRepository.save(newLog);
                log.info("mobile/addNewLog | log added | uuid = " + newLog.uuid);
                uuids.add(item.getUuid());
            }

            return MLogResponse.builder().status(200).uuids(uuids).build();
        }catch (Exception e){
            log.error("mobile/addNewLog | exception | "+e.getMessage());
        }
        return MLogResponse.builder().status(500).uuids(null).build();
    }

    public WorkTypeResponse getWorkIdByMobileName(String token, String mobileName) throws ParseException {
        if (apiService.checkToken(token)){
            log.error("mobile/getWorkIdByMobileName | log error | token = "+token);
            return WorkTypeResponse.builder().status(401).id(-1).build();
        }

        WorkType workType = workTypeRepository.findByMobileName(mobileName);
        if (workType == null){
            log.error("mobile/getWorkIdByMobileName | workType not found | mobileName = "+mobileName);
            return WorkTypeResponse.builder().status(400).id(-1).build();
        }

        return WorkTypeResponse.builder().status(200).id(workType.id).build();
    }
}

package com.example.rdk.service.mobile;

import com.example.rdk.dto.mobile.FacilityContainer;
import com.example.rdk.dto.mobile.MFacilityResponse;
import com.example.rdk.models.Facility;
import com.example.rdk.repositories.FacilityRepository;
import com.example.rdk.service.web.ApiService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FacilityMobileService {
    private final FacilityRepository facilityRepository;
    private final ApiService apiService;

    public MFacilityResponse getFacilityByCompanyId(String token, int companyId) throws ParseException {
        if (apiService.checkToken(token)){
            log.error("mobile/getFacilityByCompanyId | log error | token = "+token);
            return MFacilityResponse.builder().status(401).facilities(null).build();
        }

        try {
            List<Facility> facilityList = facilityRepository.findAllByCompanyId(companyId);

            List<FacilityContainer> facilityNames = new ArrayList<>();
            for (Facility facility : facilityList) {
                facilityNames.add(FacilityContainer.builder().id(facility.id).name(facility.name).isActive(facility.isActive).build());
            }

            return MFacilityResponse.builder().status(200).facilities(facilityNames).build();
        }catch (Exception e){
            log.error("mobile/getFacilityByCompanyId | exeption | "+ e.getMessage());
        }
        return null;
    }
}

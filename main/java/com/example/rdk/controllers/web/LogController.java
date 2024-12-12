package com.example.rdk.controllers.web;

import com.example.rdk.dto.ResponseDto;
import org.springframework.core.io.InputStreamResource;
import com.example.rdk.dto.web.logs.GetAllLogsRequestDto;
import com.example.rdk.dto.web.logs.GetDistinctByDateRequestDto;
import com.example.rdk.service.web.LogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping(path = "/logs")
@AllArgsConstructor
public class LogController {
    private final LogService logService;

    @GetMapping(value = "/get-all-works-types")
    public ResponseDto getAllWorksTypes(@RequestHeader String Authorization) throws ParseException {
        return logService.getAllWorksTypes(Authorization);
    }

    @PostMapping(value = "/get-all-logs-by-date-work-id-facility-id")
    public ResponseDto getAllLogsByDateWorkIdFacilityId(@RequestHeader String Authorization, @RequestBody @Valid GetAllLogsRequestDto requestDto) throws ParseException {
        return  logService.getAllLogsByDateWorkIdFacilityId(Authorization,requestDto);
    }

    @PostMapping(value = "/get-all-logs-date-distinct")
    public ResponseDto getAllByDate(@RequestHeader String Authorization, @RequestBody @Valid GetDistinctByDateRequestDto requestDto) throws ParseException {
        return logService.getAllLogsByDateDistinct(Authorization,requestDto);
    }

    @GetMapping(path="/download-report")
    public ResponseEntity<InputStreamResource> downloadDocument(@RequestParam("workid") int workId, @RequestParam("facilityId") int facilityId, @RequestParam("date") String date, @RequestHeader String Authorization) throws IOException, ParseException {
       return logService.downloadReport(workId,facilityId,date,Authorization);
    }

    @PostMapping(path = "/upload-report")
    public ResponseDto uploadReport(@RequestParam("report")MultipartFile file, @RequestParam("workId") int workId){
        return logService.uploadReport(file, workId);
    }

}

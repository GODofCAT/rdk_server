package com.example.rdk.controllers.mobile;

import com.example.rdk.dto.mobile.MLogResponse;
import com.example.rdk.dto.mobile.WorkTypeResponse;
import com.example.rdk.dto.web.logs.AddNewLogDto;
import com.example.rdk.service.mobile.LogMobileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(path = "/mobile/logs")
@AllArgsConstructor
public class MLogController {
    private final LogMobileService logService;

    @PostMapping(value = "/add-new")
    public MLogResponse addNewLog(@RequestHeader String Authorization, @RequestBody List<AddNewLogDto> addNewLogDto) throws ParseException {
        return logService.addNewLog(Authorization,addNewLogDto);
    }

    @PostMapping(value = "/get-workId")
    public WorkTypeResponse getWorkIdByMobileName(@RequestHeader String Authorization, @RequestBody String mobileName) throws ParseException {
        return logService.getWorkIdByMobileName(Authorization, mobileName);
    }
}

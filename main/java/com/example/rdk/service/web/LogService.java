package com.example.rdk.service.web;

import com.example.rdk.controllers.utils.MsgTypes;
import com.example.rdk.models.Company;
import org.springframework.core.io.InputStreamResource;
import com.example.rdk.dto.error.ErrorDto;
import com.example.rdk.dto.ResponseDto;
import com.example.rdk.dto.web.logs.*;
import com.example.rdk.models.Facility;
import com.example.rdk.models.Log;
import com.example.rdk.models.WorkType;
import com.example.rdk.repositories.CompanyRepository;
import com.example.rdk.repositories.FacilityRepository;
import com.example.rdk.repositories.LogRepository;
import com.example.rdk.repositories.WorkTypeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LogService {
    private final WorkTypeRepository workTypeRepository;
    private final FacilityRepository facilityRepository;
    private final CompanyRepository companyRepository;
    private final LogRepository logRepository;
    private final ApiService apiService;
    private final ModelMapper modelMapper;

    public ResponseDto getAllWorksTypes(String token) throws ParseException {
        if (apiService.checkToken(token)) {
            return ResponseDto.builder()
                    .status(401)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Неверный API токен").build()).build();
        }

        List<WorksTypesDto> worksTypesDtoList = workTypeRepository.findAllByOrderByIdAsc().stream().map(workType -> WorksTypesDto.builder().id(workType.id).name(workType.name).build()).collect(Collectors.toList());
        return ResponseDto.builder().status(200)
                .msgType(MsgTypes.normal.toString())
                .content(worksTypesDtoList).build();
    }

    public ResponseDto getAllLogsByDateWorkIdFacilityId(String token, GetAllLogsRequestDto requestDto) throws ParseException {
        if (apiService.checkToken(token)) {
            return ResponseDto.builder()
                    .status(401)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Неверный API токен").build()).build();
        }

        Optional<Facility> optionalFacility = facilityRepository.findById(requestDto.getFacilityId());
        Optional<WorkType> optionalWorkType = workTypeRepository.findById(requestDto.getWorkId());

        if (optionalFacility.isEmpty() || optionalWorkType.isEmpty()) {
            return ResponseDto.builder()
                    .status(400)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Компанияили тип не найдены").build()).build();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        List<LogsDto> logList = logRepository.findAllByDateAndFacilityAndWorkTypeOrderByIdDesc(simpleDateFormat.parse(requestDto.getDate()), optionalFacility.get(), optionalWorkType.get()).stream().map(log -> LogsDto.builder().id(log.id).date(simpleDateFormat.format(log.date)).controlNumStatus(log.controlNumStatus).tagNum(log.tagNum).facilityId(log.facility.id).workId(log.workType.id).companyId(log.company.getId()).controlNum(log.controlNum).companyName(log.company.getName()).facilityName(log.facility.name).workName(log.workType.name).processName((log.processName == null) ? "" : log.processName).build()).collect(Collectors.toList());

        return ResponseDto.builder().status(200)
                .msgType(MsgTypes.normal.toString())
                .content(logList).build();
    }

    public ResponseDto getAllLogsByDateDistinct(String token, GetDistinctByDateRequestDto requestDto) throws ParseException {
        if (apiService.checkToken(token)) {
            return ResponseDto.builder()
                    .status(401)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Неверный API токен").build()).build();
        }

        Optional<Facility> optionalFacility = facilityRepository.findById(requestDto.getFacilityId());
        Optional<WorkType> optionalWorkType = workTypeRepository.findById(requestDto.getWorkId());

        if (optionalFacility.isEmpty() || optionalWorkType.isEmpty()) {
            return ResponseDto.builder()
                    .status(400)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Компания или тип не найдены").build()).build();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        List<GetDistinctByDateResponseDto> dateResponseDto = logRepository.findUniqueDate(optionalFacility.get(), optionalWorkType.get()).stream().map(date -> GetDistinctByDateResponseDto.builder().date(simpleDateFormat.format(date)).build()).collect(Collectors.toList());

        return ResponseDto.builder().status(200)
                .msgType(MsgTypes.normal.toString())
                .content(dateResponseDto).build();
    }

    public ResponseEntity<InputStreamResource> downloadReport(int workId, int facilityId, String date, String token) throws IOException, ParseException {
        if (apiService.checkToken(token)) {
            ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ErrorDto.builder().message("Неверный API токен"));
        }

        Optional<WorkType> workTypeOptional = workTypeRepository.findById(workId);
        Optional<Facility> facilityOptional = facilityRepository.findById(facilityId);
        if (workTypeOptional.isEmpty() || facilityOptional.isEmpty()) {
            ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ErrorDto.builder().message("Компания или тип не найдены"));
        }

        WorkType workType = workTypeOptional.get();
        Facility facility = facilityOptional.get();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        List<Log> logList = logRepository.findAllByFacilityAndWorkTypeAndDate(facility, workType, simpleDateFormat.parse(date));

        String reportName = "report-" + date;

        switch (workId) {
            case 1:
                reportName += "-Deratizaciya.csv";
                break;
            case 2:
                reportName += "-Dezinsekciya.csv";
                break;
            case 3:
                reportName += "-Feromonnyj-Monitoring.csv";
                break;
        }

        File reportForUpload = new File(reportName);

        FileOutputStream  writer = new FileOutputStream(reportForUpload);

        if (workId == 1) {
            writer.write(String.format("%s;%s;%s;%s;%s\n", "номер точки контроля", "дата", "статус точки контроля", "примененное действие", "объект").getBytes());
            for (Log log : logList) {
                writer.write(String.format("%s;%s;%s;%s;%s\n", log.controlNum, date, log.controlNumStatus, log.processName, facility.name).getBytes());
            }
        } else {
            writer.write(String.format("%s;%s;%s;%s\n", "номер точки контроля", "дата", "статус точки контроля", "объект").getBytes());
            for (Log log : logList) {
                writer.write(String.format("%s;%s;%s;%s\n", log.controlNum, date, log.controlNumStatus, facility.name).getBytes());
            }
        }
        writer.close();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + reportName);

        InputStreamReader i = new InputStreamReader(new FileInputStream(reportForUpload));

        //Path path = Paths.get(reportForUpload.getAbsolutePath());
        //ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        FileInputStream fileInputStream = new FileInputStream(reportForUpload);
        InputStreamResource inputStreamResource = new InputStreamResource(fileInputStream);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(reportForUpload.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(inputStreamResource);

    }

    public ResponseDto uploadReport(MultipartFile uplodedFile, int workType) {
        if (uplodedFile.isEmpty()) {
            return ResponseDto.builder().status(400).msgType("error").content("загружен пустой файл").build();
        }
        try {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            File file = new File("uploaded-" + dateFormat.format(date) + ".txt");
            uplodedFile.transferTo(file);

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            String[] splitLine = new String[5];
            //34:8E:D9:11:18:29:C6-04.06.24-БЦ6_ББ-#128-`+`
            int controlNum;
            String controlNumStatus;
            Date logDate;
            String tagNum;
            Company company;
            Facility facility;
            String uuid;
            String processName;
            Log log;
            while (line != null) {
                splitLine = line.split("-");
                controlNum = Integer.parseInt(splitLine[3].substring(1));
                controlNumStatus = splitLine[4];
                logDate = dateFormat.parse(splitLine[1]);
                tagNum = splitLine[0];
                facility = facilityRepository.findByName(splitLine[2]);
                company = companyRepository.findById(facility.getId()).get();
                uuid = UUID.randomUUID().toString();
                if (workType == 1) {
                    processName =null;
                    log = new Log(controlNum, controlNumStatus, logDate, tagNum, workTypeRepository
                            .findById(workType).get(), company, facility, uuid, processName);
                } else {
                    log = new Log(controlNum, controlNumStatus, logDate, tagNum, null, company, facility, uuid, null);
                }
            }
           // logRepository.saveAndFlush(log);
            bufferedReader.close();

        } catch (Exception e) {

        }
        return null;
    }

}

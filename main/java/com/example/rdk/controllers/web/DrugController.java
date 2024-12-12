package com.example.rdk.controllers.web;

import com.example.rdk.dto.ResponseDto;
import com.example.rdk.dto.web.Drug.AddDrugDto;
import com.example.rdk.dto.web.Drug.UpdateDrugDto;
import com.example.rdk.service.web.DrugService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping(path = "/drug")
@AllArgsConstructor
public class DrugController {
    private final DrugService drugService;

    @GetMapping(value = "/get-all")
    public ResponseDto getAllDrugs(@RequestHeader String Authorization) throws ParseException {
        return drugService.getAllDrugs(Authorization);
    }

    @GetMapping(value = "/get-drug-by-id/{id}")
    public ResponseDto getDrugById(@PathVariable int id, @RequestHeader String Authorization) throws ParseException {
        return drugService.getDrugById(id,Authorization);
    }

    @PostMapping(value = "/add-new")
    public ResponseDto addNewDrug(@RequestBody AddDrugDto addDrugDto,@RequestHeader String Authorization) throws ParseException {
        return drugService.addNewDrug(addDrugDto,Authorization);
    }

    @PutMapping(value = "/update-by-id")
    public ResponseDto updateDrugById(@RequestBody UpdateDrugDto updateDrugDto,@RequestHeader String Authorization) throws ParseException {
        return drugService.updateDrugById(updateDrugDto,Authorization);
    }

    @DeleteMapping(value = "/delete-drug-by-id/{id}")
    public ResponseDto deleteDrugById(@PathVariable int id, @RequestHeader String Authorization) throws ParseException {
        return drugService.deleteDrugById(id,Authorization);
    }
}

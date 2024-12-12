package com.example.rdk.service.web;

import com.example.rdk.controllers.utils.MsgTypes;
import com.example.rdk.dto.ResponseDto;
import com.example.rdk.dto.error.ErrorDto;
import com.example.rdk.dto.web.Drug.AddDrugDto;
import com.example.rdk.dto.web.Drug.DrugDto;
import com.example.rdk.dto.web.Drug.UpdateDrugDto;
import com.example.rdk.models.Drug;
import com.example.rdk.repositories.DrugRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DrugService {
    DrugRepository drugRepository;
    ApiService apiService;
    ModelMapper modelMapper;

    public ResponseDto getAllDrugs(String token) throws ParseException {
        if (apiService.checkToken(token)){
            return ResponseDto.builder()
                    .status(401)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Неверный API токен").build()).build();
        }

        return ResponseDto.builder().status(200).msgType(MsgTypes.normal.toString()).content(drugRepository.findAllByOrderByIdAsc()).build();
    }

    public ResponseDto getDrugById(int id, String token) throws ParseException {
        if (apiService.checkToken(token)){
            return ResponseDto.builder()
                    .status(401)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Неверный API токен").build()).build();
        }

        Optional<Drug> optDrug = drugRepository.findById(id);
        if (optDrug.isEmpty()){
            return ResponseDto.builder()
                    .status(400)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Препарата с таким id не существует").build()).build();
        }

        Drug drug = optDrug.get();

        return ResponseDto.builder().status(200).msgType(MsgTypes.normal.toString()).content(DrugDto.builder().id(drug.getId()).name(drug.getName()).ingredients(drug.getIngredients()).build()).build();
    }

    public ResponseDto addNewDrug(AddDrugDto addDrugDto, String token) throws ParseException {
        if (apiService.checkToken(token)){
            return ResponseDto.builder()
                    .status(401)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Неверный API токен").build()).build();
        }

        if (drugRepository.findByName(addDrugDto.getName()).isPresent()){
            return ResponseDto.builder()
                    .status(400)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Препарат с таким именем уже существует").build()).build();
        }

        Drug drug = modelMapper.map(addDrugDto,Drug.class);
        drugRepository.saveAndFlush(drug);

        return ResponseDto.builder().status(200).msgType(MsgTypes.normal.toString()).content(DrugDto.builder().id(drug.getId()).name(drug.getName()).ingredients(drug.getIngredients()).build()).build();
    }

    public ResponseDto updateDrugById(UpdateDrugDto updateDrugDto, String token) throws ParseException {
        if (apiService.checkToken(token)){
            return ResponseDto.builder()
                    .status(401)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Неверный API токен").build()).build();
        }

        Optional<Drug> optDrugByName = drugRepository.findByName(updateDrugDto.getName());

        if (optDrugByName.isPresent() && optDrugByName.get().getId() != updateDrugDto.getId()){
            return ResponseDto.builder()
                    .status(400)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Препарат с таким именем уже существует").build()).build();
        }

        Optional<Drug> optDrugById = drugRepository.findById(updateDrugDto.getId());

        if (optDrugById.isEmpty()){
            return ResponseDto.builder()
                    .status(400)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Препарат с таким ID не найден").build()).build();
        }

        Drug drug = optDrugById.get();
        drug.setName(updateDrugDto.getName());
        drug.setIngredients(updateDrugDto.getIngredients());
        drugRepository.saveAndFlush(drug);

        return ResponseDto.builder().status(200).msgType(MsgTypes.normal.toString()).content(DrugDto.builder().id(drug.getId()).name(drug.getName()).ingredients(drug.getIngredients()).build()).build();
    }

    public ResponseDto deleteDrugById(int id, String token) throws ParseException {
        if (apiService.checkToken(token)){
            return ResponseDto.builder()
                    .status(401)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Неверный API токен").build()).build();
        }

        Optional<Drug> optDrugById = drugRepository.findById(id);

        if (optDrugById.isEmpty()){
            return ResponseDto.builder()
                    .status(400)
                    .msgType(MsgTypes.error.toString())
                    .content(ErrorDto.builder().message("Препарат с таким ID не найден").build()).build();
        }

        drugRepository.deleteById(id);

        return ResponseDto.builder().status(200).msgType(MsgTypes.normal.toString()).content(null).build();
    }
}

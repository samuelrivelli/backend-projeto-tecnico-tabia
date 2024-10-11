package com.tabia.projeto_tecnico.controller;

import com.tabia.projeto_tecnico.model.dto.OptionDTO;
import com.tabia.projeto_tecnico.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/options")
public class OptionController {

    private final OptionService optionService;

    @Autowired
    public OptionController(OptionService optionService){
        this.optionService = optionService;
    }

    @GetMapping
    public ResponseEntity<List<OptionDTO>> findAll(){
        List<OptionDTO> options = optionService.findAll();
        return ResponseEntity.ok(options);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<OptionDTO>> findById(@PathVariable("id") Long id){
        Optional<OptionDTO> option = optionService.findById(id);
        return ResponseEntity.ok(option);
    }

    @PostMapping
    public ResponseEntity<OptionDTO> post(@RequestBody OptionDTO optionDTO){
        Optional<OptionDTO> option = Optional.ofNullable(optionService.save(optionDTO));
        return new ResponseEntity<>(option.get(), HttpStatus.CREATED);
    }

}

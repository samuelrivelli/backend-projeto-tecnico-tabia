package com.tabia.projeto_tecnico.service;

import com.tabia.projeto_tecnico.exceptions.OptionNotFoundException;
import com.tabia.projeto_tecnico.model.dto.OptionDTO;
import com.tabia.projeto_tecnico.model.entity.Option;
import com.tabia.projeto_tecnico.repository.OptionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OptionService {

    @Autowired
    private OptionRepository optionRepository;

    public List<OptionDTO> findAll(){
        List<Option> options = optionRepository.findAll();

        return  options.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<OptionDTO> findById(Long id){
        Optional<Option> option = optionRepository.findById(id);

        if(!option.isPresent()){
            throw new OptionNotFoundException("Option not found");
        }

        return  Optional.of(convertToDTO(option.get()));
    }


    public OptionDTO convertToDTO(Option option) {
        ModelMapper modelMapper = new ModelMapper();
        OptionDTO optionDTO = modelMapper.map(option, OptionDTO.class);
        return optionDTO;
    }

    public Option create(OptionDTO optionDTO){
        ModelMapper modelMapper = new ModelMapper();
        Option option = modelMapper.map(optionDTO, Option.class);
        return option;
    }
}

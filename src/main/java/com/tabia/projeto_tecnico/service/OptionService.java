package com.tabia.projeto_tecnico.service;

import com.tabia.projeto_tecnico.exceptions.OptionNotFoundException;
import com.tabia.projeto_tecnico.exceptions.PollNotFoundException;
import com.tabia.projeto_tecnico.model.dto.OptionDTO;
import com.tabia.projeto_tecnico.model.entity.Option;
import com.tabia.projeto_tecnico.model.entity.Poll;
import com.tabia.projeto_tecnico.repository.OptionRepository;
import com.tabia.projeto_tecnico.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OptionService {

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private PollRepository pollRepository;

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

    public OptionDTO save(OptionDTO optionDTO){
        Option option = create(optionDTO);
        Option savedOption = optionRepository.save(option);

        return convertToDTO(savedOption);
    }


    public OptionDTO convertToDTO(Option option) {
       OptionDTO optionDTO = new OptionDTO();
       optionDTO.setId(option.getId());
       optionDTO.setText(option.getText());
       optionDTO.setPoolId(option.getPoll().getId());

       return optionDTO;
    }

    public Option create(OptionDTO optionDTO){
        Option option = new Option();
        option.setText(optionDTO.getText());

        Optional<Poll> poll = pollRepository.findById(optionDTO.getPoolId());

        if(!poll.isPresent()){
            throw new PollNotFoundException("Poll not found");
        }

        option.setPoll(poll.get());

        return option;
    }
}

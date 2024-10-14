package com.tabia.projeto_tecnico.service;

import com.tabia.projeto_tecnico.exceptions.OptionNotFoundException;
import com.tabia.projeto_tecnico.exceptions.PollNotFoundException;
import com.tabia.projeto_tecnico.model.dto.OptionDTO;
import com.tabia.projeto_tecnico.model.dto.VoteDTO;
import com.tabia.projeto_tecnico.model.entity.Option;
import com.tabia.projeto_tecnico.model.entity.Poll;
import com.tabia.projeto_tecnico.repository.OptionRepository;
import com.tabia.projeto_tecnico.repository.PollRepository;
import jakarta.transaction.Transactional;
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

    public OptionDTO update(Long id, OptionDTO optionDTO){
        Optional<Option> optionalOption = optionRepository.findById(id);

        if(!optionalOption.isPresent()){
            throw new OptionNotFoundException("Option not found");
        }

        Option existingOption = optionalOption.get();

        existingOption.setText(optionDTO.getText());

        if(optionDTO.getPoolId() != null) {
            Optional<Poll> poll = pollRepository.findById(optionDTO.getPoolId());
            if(!poll.isPresent()){
                throw new PollNotFoundException("Poll not found");
            }
            existingOption.setPoll(poll.get());
        }

        Option updatedOption = optionRepository.save(existingOption);

        return convertToDTO(updatedOption);
    }

    @Transactional
    public void delete(Long id){
        Optional<Option> option = optionRepository.findById(id);

        if(!option.isPresent()){
            throw new OptionNotFoundException("Option not found");
        }

        optionRepository.delete(option.get());

    }



    public OptionDTO convertToDTO(Option option) {
       OptionDTO optionDTO = new OptionDTO();
       optionDTO.setId(option.getId());
       optionDTO.setText(option.getText());
       optionDTO.setPoolId(option.getPoll().getId());

        List<VoteDTO> voteDTOs = option.getVotes().stream()
                .map(vote -> new VoteDTO(vote.getId(), vote.getUser().getId(), vote.getOption().getId(), vote.getPoll().getId()))
                .collect(Collectors.toList());
        optionDTO.setVotes(voteDTOs);

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

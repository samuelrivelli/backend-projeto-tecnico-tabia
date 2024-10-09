package com.tabia.projeto_tecnico.service;

import com.tabia.projeto_tecnico.exceptions.PollNotFoundException;
import com.tabia.projeto_tecnico.model.dto.PollDTO;
import com.tabia.projeto_tecnico.model.entity.Option;
import com.tabia.projeto_tecnico.model.entity.Poll;
import com.tabia.projeto_tecnico.repository.PollRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PollService {

    private PollRepository pollRepository;

    public List<PollDTO> findAll() {
        List<Poll> polls = pollRepository.findAll() ;

        return polls.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PollDTO> findById(Long id){
        Optional<Poll> poll = pollRepository.findById(id);

        if(!poll.isPresent()){
            throw new PollNotFoundException("Poll not found");
        }

        return Optional.of(convertToDTO(poll.get()));
    }


    public PollDTO convertToDTO(Poll poll) {
        ModelMapper modelMapper = new ModelMapper();
        PollDTO pollDTO = modelMapper.map(poll, PollDTO.class);
        return pollDTO;
    }

    public Poll create(PollDTO pollDTO){
        ModelMapper modelMapper = new ModelMapper();
        Poll poll = modelMapper.map(pollDTO, Poll.class);
        return poll;
    }

}

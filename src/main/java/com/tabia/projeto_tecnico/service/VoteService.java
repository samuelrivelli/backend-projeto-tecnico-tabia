package com.tabia.projeto_tecnico.service;

import com.tabia.projeto_tecnico.exceptions.VoteNotFoundException;
import com.tabia.projeto_tecnico.model.dto.VoteDTO;
import com.tabia.projeto_tecnico.model.entity.Vote;
import com.tabia.projeto_tecnico.repository.VoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    public List<VoteDTO> findAll(){
        List<Vote> votes = voteRepository.findAll();

        return votes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<VoteDTO> findById(Long id){
        Optional<Vote> vote = voteRepository.findById(id);

        if(!vote.isPresent()){
            throw new VoteNotFoundException("Vote not found");
        }

        return Optional.of(convertToDTO(vote.get()));
    }

    public VoteDTO convertToDTO(Vote vote){
        ModelMapper modelMapper = new ModelMapper();
        VoteDTO voteDTO = modelMapper.map(modelMapper, VoteDTO.class);
        return voteDTO;
    }

    public Vote create(VoteDTO voteDTO){
        ModelMapper modelMapper = new ModelMapper();
        Vote vote = modelMapper.map(voteDTO, Vote.class);
        return vote;
    }
}

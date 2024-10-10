package com.tabia.projeto_tecnico.service;

import com.tabia.projeto_tecnico.exceptions.PollNotFoundException;
import com.tabia.projeto_tecnico.exceptions.UserNotFoundException;
import com.tabia.projeto_tecnico.model.dto.OptionDTO;
import com.tabia.projeto_tecnico.model.dto.PollDTO;
import com.tabia.projeto_tecnico.model.entity.Option;
import com.tabia.projeto_tecnico.model.entity.Poll;
import com.tabia.projeto_tecnico.model.entity.UserEntity;
import com.tabia.projeto_tecnico.repository.PollRepository;
import com.tabia.projeto_tecnico.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private UserRepository userRepository;

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

    public Poll save(PollDTO pollDTO){
        Poll poll = create(pollDTO);
        return pollRepository.save(poll);
    }


    public PollDTO convertToDTO(Poll poll) {
        PollDTO pollDTO = new PollDTO();
        pollDTO.setId(poll.getId());
        pollDTO.setTitle(poll.getTitle());
        pollDTO.setDescription(poll.getDescription());
        pollDTO.setUserId(poll.getUser().getId());

        List<OptionDTO> optionDTOs = poll.getOptions().stream()
                .map(option -> new OptionDTO(option.getId(), option.getText(), option.getPoll().getId()))
                .collect(Collectors.toList());

        pollDTO.setOptions(optionDTOs);

        return pollDTO;
    }

    public Poll create(PollDTO pollDTO) {
        Poll poll = new Poll();

        poll.setTitle(pollDTO.getTitle());
        poll.setDescription(pollDTO.getDescription());

        Optional<UserEntity> user = userRepository.findById(pollDTO.getUserId());

        if(!user.isPresent()){
            throw new UserNotFoundException("User not found");
        }

        poll.setUser(user.get());

        List<Option> options = pollDTO.getOptions().stream()
                .map(optionDTO -> new Option(null, optionDTO.getText(), poll)) // Cria nova Option
                .collect(Collectors.toList());

        poll.setOptions(options);

        return poll;
    }

}

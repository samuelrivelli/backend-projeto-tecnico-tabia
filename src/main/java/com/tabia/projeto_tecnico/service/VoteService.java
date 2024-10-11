package com.tabia.projeto_tecnico.service;

import com.tabia.projeto_tecnico.exceptions.OptionNotFoundException;
import com.tabia.projeto_tecnico.exceptions.PollNotFoundException;
import com.tabia.projeto_tecnico.exceptions.UserNotFoundException;
import com.tabia.projeto_tecnico.exceptions.VoteNotFoundException;
import com.tabia.projeto_tecnico.model.dto.VoteDTO;
import com.tabia.projeto_tecnico.model.entity.Option;
import com.tabia.projeto_tecnico.model.entity.Poll;
import com.tabia.projeto_tecnico.model.entity.UserEntity;
import com.tabia.projeto_tecnico.model.entity.Vote;
import com.tabia.projeto_tecnico.repository.OptionRepository;
import com.tabia.projeto_tecnico.repository.PollRepository;
import com.tabia.projeto_tecnico.repository.UserRepository;
import com.tabia.projeto_tecnico.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private OptionRepository optionRepository;


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

    public VoteDTO save(VoteDTO voteDTO){
        Vote vote = create(voteDTO);
        Vote savedVote = voteRepository.save(vote);

        return convertToDTO(savedVote);
    }

    public VoteDTO convertToDTO(Vote vote){
        VoteDTO voteDTO = new VoteDTO();
        voteDTO.setId(vote.getId());
        voteDTO.setUserId(vote.getUser().getId());
        voteDTO.setOptionId(vote.getOption().getId());
        voteDTO.setPollId(vote.getPoll().getId());

        return voteDTO;
    }

    public Vote create(VoteDTO voteDTO){
       Vote vote = new Vote();

        Optional<UserEntity> user = userRepository.findById(voteDTO.getUserId());

        if(!user.isPresent()){
            throw new UserNotFoundException("User not found");
        }

        vote.setUser(user.get());

        Optional<Poll> poll = pollRepository.findById(voteDTO.getPollId());

        if(!poll.isPresent()){
            throw new PollNotFoundException("Poll not found");
        }

        vote.setPoll(poll.get());

        Optional<Option> option = optionRepository.findById(voteDTO.getOptionId());

        if(!option.isPresent()){
            throw new OptionNotFoundException("Option not found");
        }

        vote.setOption(option.get());

        return vote;

    }
}

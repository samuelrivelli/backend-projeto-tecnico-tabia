package com.tabia.projeto_tecnico.service;

import com.tabia.projeto_tecnico.exceptions.PollNotFoundException;
import com.tabia.projeto_tecnico.exceptions.UserNotFoundException;
import com.tabia.projeto_tecnico.model.dto.CommentDTO;
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

    public PollDTO save(PollDTO pollDTO) {
        Poll poll = create(pollDTO);
        Poll savedPoll = pollRepository.save(poll);

        return convertToDTO(savedPoll);
    }

    public PollDTO update(Long id, PollDTO pollDTO) {
        Optional<Poll> optionalPoll = pollRepository.findById(id);

        if (!optionalPoll.isPresent()) {
            throw new PollNotFoundException("Poll not found");
        }

        Poll existingPoll = optionalPoll.get();

        existingPoll.setTitle(pollDTO.getTitle());
        existingPoll.setDescription(pollDTO.getDescription());

        if (pollDTO.getUserId() != null) {
            Optional<UserEntity> user = userRepository.findById(pollDTO.getUserId());
            if (!user.isPresent()) {
                throw new UserNotFoundException("User not found");
            }
            existingPoll.setUser(user.get());
        }

        Poll updatedPoll = pollRepository.save(existingPoll);

        return convertToDTO(updatedPoll);
    }




    public PollDTO convertToDTO(Poll poll) {
        PollDTO pollDTO = new PollDTO();
        pollDTO.setId(poll.getId());
        pollDTO.setTitle(poll.getTitle());
        pollDTO.setDescription(poll.getDescription());
        pollDTO.setUserId(poll.getUser().getId());

        // Mapeia as opções
        List<OptionDTO> optionDTOs = poll.getOptions().stream()
                .map(option -> {
                    Long voteCount = option.getVotes() != null ? (long) option.getVotes().size() : 0;
                    OptionDTO optionDTO = new OptionDTO();
                    optionDTO.setId(option.getId());
                    optionDTO.setText(option.getText());
                    optionDTO.setPoolId(poll.getId());
                    optionDTO.setVoteCount(voteCount);

                    return optionDTO;
                })
                .collect(Collectors.toList());

        pollDTO.setOptions(optionDTOs);


        List<CommentDTO> commentDTOs = poll.getComments().stream()
                .map(comment -> new CommentDTO(
                        comment.getId(),
                        comment.getContent(),
                        comment.getUser().getId(),
                        comment.getPoll().getId(),
                        comment.getCreatedAt()
                ))
                .collect(Collectors.toList());

        pollDTO.setComments(commentDTOs);

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
                .map(optionDTO -> {
                    Option option = new Option();
                    option.setText(optionDTO.getText());
                    option.setPoll(poll);
                    return option;
                })
                .collect(Collectors.toList());

        poll.setOptions(options);

        return pollRepository.save(poll);
    }


}

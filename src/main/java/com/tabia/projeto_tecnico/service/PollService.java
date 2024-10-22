package com.tabia.projeto_tecnico.service;

import com.tabia.projeto_tecnico.exceptions.InsufficientOptionsException;
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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        List<Poll> polls = pollRepository.findAllByOrderByCreatedAtDesc() ;

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

    @Transactional
    public PollDTO save(PollDTO pollDTO) {
        Poll poll = create(pollDTO);

        if(poll.getOptions().size()<2){
            throw new InsufficientOptionsException("A poll must have two or more options");
        }

        Poll savedPoll = pollRepository.save(poll);

        return convertToDTO(savedPoll);
    }

    @Transactional
    public PollDTO update(Long id, PollDTO pollDTO) {
        Optional<Poll> optionalPoll = pollRepository.findById(id);

        if (!optionalPoll.isPresent()) {
            throw new PollNotFoundException("Poll not found");
        }

        Poll existingPoll = optionalPoll.get();

        existingPoll.setTitle(pollDTO.getTitle());
        existingPoll.setDescription(pollDTO.getDescription());
        existingPoll.setIsOpen(pollDTO.getIsOpen());

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

    @Transactional
    public void delete(Long id){
        Optional<Poll> poll = pollRepository.findById(id);

        if(!poll.isPresent()){
            throw new PollNotFoundException("Poll not found");
        }

        pollRepository.delete(poll.get());

    }

    @Transactional
    public PollDTO openPoll(Long id){
        Optional<Poll> optionalPoll = pollRepository.findById(id);

        if(!optionalPoll.isPresent()){
            throw new PollNotFoundException("Poll not found");
        }

        Poll poll = optionalPoll.get();
        poll.setIsOpen(true);
        pollRepository.save(poll);

        return convertToDTO(poll);
    }

    @Transactional
    public PollDTO closePoll(Long id){
        Optional<Poll> optionalPoll = pollRepository.findById(id);

        if(!optionalPoll.isPresent()){
            throw new PollNotFoundException("Poll not found");
        }

        Poll poll = optionalPoll.get();
        poll.setIsOpen(false);
        pollRepository.save(poll);

        return convertToDTO(poll);
    }

    public PollDTO convertToDTO(Poll poll) {
        PollDTO pollDTO = new PollDTO();
        pollDTO.setId(poll.getId());
        pollDTO.setTitle(poll.getTitle());
        pollDTO.setDescription(poll.getDescription());
        pollDTO.setUserId(poll.getUser().getId());
        pollDTO.setCreatedAt(poll.getCreatedAt());
        pollDTO.setIsOpen(poll.getIsOpen());


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
                .sorted((c1, c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()))
                .map(comment -> new CommentDTO(
                        comment.getId(),
                        comment.getContent(),
                        comment.getUser().getId(),
                        comment.getPoll().getId(),
                        comment.getCreatedAt()
                ))
                .collect(Collectors.toList());

        pollDTO.setComments(commentDTOs);

        return pollDTO;//
    }


    public Poll create(PollDTO pollDTO) {
        Poll poll = new Poll();

        poll.setTitle(pollDTO.getTitle());
        poll.setDescription(pollDTO.getDescription());
        poll.setCreatedAt(LocalDateTime.now());
        poll.setIsOpen(pollDTO.getIsOpen());

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

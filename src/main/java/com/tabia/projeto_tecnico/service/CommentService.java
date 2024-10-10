package com.tabia.projeto_tecnico.service;

import com.tabia.projeto_tecnico.exceptions.CommentNotFoundException;
import com.tabia.projeto_tecnico.exceptions.PollNotFoundException;
import com.tabia.projeto_tecnico.exceptions.UserNotFoundException;
import com.tabia.projeto_tecnico.model.dto.CommentDTO;
import com.tabia.projeto_tecnico.model.entity.Comment;
import com.tabia.projeto_tecnico.model.entity.Option;
import com.tabia.projeto_tecnico.model.entity.Poll;
import com.tabia.projeto_tecnico.model.entity.UserEntity;
import com.tabia.projeto_tecnico.repository.CommentRepository;
import com.tabia.projeto_tecnico.repository.PollRepository;
import com.tabia.projeto_tecnico.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollRepository pollRepository;

    public List<CommentDTO> findAll(){
        List<Comment> comments = commentRepository.findAll();

        return comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CommentDTO> findById(Long id){
        Optional<Comment> comment = commentRepository.findById(id);

        if(!comment.isPresent()){
            throw new CommentNotFoundException("Comment not found");
        }

        return Optional.of(convertToDTO(comment.get()));
    }

    public CommentDTO convertToDTO(Comment comment) {
       CommentDTO commentDTO = new CommentDTO();
       commentDTO.setId(comment.getId());
       commentDTO.setContent(comment.getContent());
       commentDTO.setUserId(comment.getUser().getId());
       commentDTO.setPollId(comment.getPoll().getId());
       commentDTO.setCreatedAt(comment.getCreatedAt());

       return commentDTO;
    }

    public Comment create(CommentDTO commentoDTO){
        Comment comment = new Comment();
        comment.setContent(commentoDTO.getContent());

        Optional<UserEntity> user = userRepository.findById(commentoDTO.getUserId());

        if(!user.isPresent()){
            throw new UserNotFoundException("User not found");
        }

        comment.setUser(user.get());

        Optional<Poll> poll = pollRepository.findById(commentoDTO.getPollId());

        if(!poll.isPresent()){
            throw new PollNotFoundException("Poll not found");
        }

        comment.setPoll(poll.get());
        comment.setCreatedAt(commentoDTO.getCreatedAt());

        return comment;

    }
}

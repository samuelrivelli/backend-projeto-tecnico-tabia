package com.tabia.projeto_tecnico.service;

import com.tabia.projeto_tecnico.exceptions.CommentNotFoundException;
import com.tabia.projeto_tecnico.model.dto.CommentoDTO;
import com.tabia.projeto_tecnico.model.entity.Comment;
import com.tabia.projeto_tecnico.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<CommentoDTO> findAll(){
        List<Comment> comments = commentRepository.findAll();

        return comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CommentoDTO> findById(Long id){
        Optional<Comment> comment = commentRepository.findById(id);

        if(!comment.isPresent()){
            throw new CommentNotFoundException("Comment not found");
        }

        return Optional.of(convertToDTO(comment.get()));
    }

    public CommentoDTO convertToDTO(Comment comment) {
        ModelMapper modelMapper = new ModelMapper();
        CommentoDTO commentDTO = modelMapper.map(comment, CommentoDTO.class);
        return commentDTO;
    }

    public Comment create(CommentoDTO commentoDTO){
        ModelMapper modelMapper = new ModelMapper();
        Comment comment = modelMapper.map(commentoDTO, Comment.class);
        return comment;
    }
}

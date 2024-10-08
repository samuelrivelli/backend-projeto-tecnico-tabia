package com.tabia.projeto_tecnico.controller;

import com.tabia.projeto_tecnico.model.dto.CommentDTO;
import com.tabia.projeto_tecnico.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> findAll(){
        List<CommentDTO> comments = commentService.findAll();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CommentDTO>> findById(@PathVariable("id") Long id){
        Optional<CommentDTO> comment = commentService.findById(id);
        return ResponseEntity.ok(comment);
    }

}

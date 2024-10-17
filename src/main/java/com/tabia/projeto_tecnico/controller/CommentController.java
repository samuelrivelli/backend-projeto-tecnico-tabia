package com.tabia.projeto_tecnico.controller;

import com.tabia.projeto_tecnico.model.dto.CommentDTO;
import com.tabia.projeto_tecnico.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/comments")
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

    @PostMapping
    public ResponseEntity<CommentDTO> post(@RequestBody CommentDTO commentDTO){
        CommentDTO comment = commentService.save(commentDTO);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> put (@PathVariable Long id, @RequestBody CommentDTO commentDTO){
        CommentDTO comment = commentService.update(id,commentDTO);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

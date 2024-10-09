package com.tabia.projeto_tecnico.controller;

import com.tabia.projeto_tecnico.model.dto.VoteDTO;
import com.tabia.projeto_tecnico.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/votes")
public class VoteController {

    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService){
        this.voteService = voteService;
    }

    @GetMapping
    public ResponseEntity<List<VoteDTO>> findAll(){
        List<VoteDTO> votes = voteService.findAll();
        return ResponseEntity.ok(votes);
    }

    @GetMapping("/{id}")
    public ResponseEntity <Optional<VoteDTO>> findById(@PathVariable("id") Long id){
        Optional<VoteDTO> vote = voteService.findById(id);
        return ResponseEntity.ok(vote);
    }
}

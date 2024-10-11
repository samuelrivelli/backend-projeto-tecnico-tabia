package com.tabia.projeto_tecnico.controller;

import com.tabia.projeto_tecnico.model.dto.PollDTO;
import com.tabia.projeto_tecnico.model.entity.Poll;
import com.tabia.projeto_tecnico.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Repeatable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/polls")
public class PollController {

    private PollService pollService;

    @Autowired
    public PollController(PollService pollService){
        this.pollService = pollService;
    }

    @GetMapping
    public ResponseEntity<List<PollDTO>> findAll(){
        List<PollDTO> polls = pollService.findAll();
        return ResponseEntity.ok(polls);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PollDTO>> findById(@PathVariable("id") Long id) {
        Optional<PollDTO> poll = pollService.findById(id);
        return ResponseEntity.ok(poll);
    }

    @PostMapping
    public ResponseEntity<PollDTO> post (@RequestBody PollDTO pollDTO){
        Optional<PollDTO> poll = Optional.ofNullable(pollService.save(pollDTO));
        return new ResponseEntity<>(poll.get(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PollDTO> put (@PathVariable Long id, @RequestBody PollDTO pollDTO){
        Poll updatedPoll = pollService.update(id, pollDTO);
        PollDTO updatedPollDTO = pollService.convertToDTO(updatedPoll);
        return ResponseEntity.ok(updatedPollDTO);
    }



}

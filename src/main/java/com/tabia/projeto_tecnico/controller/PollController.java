package com.tabia.projeto_tecnico.controller;

import com.tabia.projeto_tecnico.model.dto.PollDTO;
import com.tabia.projeto_tecnico.service.EmailService;
import com.tabia.projeto_tecnico.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/polls")
public class PollController {

    private PollService pollService;
    private final SimpMessagingTemplate messagingTemplate;
    private EmailService emailService;

    @Autowired
    public PollController(PollService pollService, SimpMessagingTemplate messagingTemplate, EmailService emailService){
        this.pollService = pollService;
        this.messagingTemplate = messagingTemplate;
        this.emailService = emailService;
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
    public ResponseEntity<PollDTO> post(@RequestBody PollDTO pollDTO) {
        PollDTO poll = pollService.save(pollDTO);

        String subject = "Nova Enquete Criada";
        String text = "Uma nova enquete foi criada: " + poll.getTitle();

        try {
            emailService.sendEmailToAllUsers(subject, text, poll.getId());
        } catch (Exception e) {
            System.err.println("Erro ao enviar email: " + e.getMessage());
        }

        messagingTemplate.convertAndSend("/topic/polls", poll);
        return new ResponseEntity<>(poll, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PollDTO> put(@PathVariable Long id, @RequestBody PollDTO pollDTO) {
        PollDTO updatedPoll = pollService.update(id, pollDTO);
        return ResponseEntity.ok(updatedPoll);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        pollService.delete(id);
        return ResponseEntity.noContent().build();
    }


}

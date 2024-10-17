package com.tabia.projeto_tecnico.controller;

import com.tabia.projeto_tecnico.model.dto.PollDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class PollWebSocketController {

    @MessageMapping("/polls")
    @SendTo("/topic/polls")
    public PollDTO sendPollUpdate(PollDTO poll) throws Exception {
        return poll;
    }
}

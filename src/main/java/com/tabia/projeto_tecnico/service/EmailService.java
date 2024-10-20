package com.tabia.projeto_tecnico.service;

import com.tabia.projeto_tecnico.model.entity.UserEntity;
import com.tabia.projeto_tecnico.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UserRepository userRepository;

    public void sendEmailToAllUsers(String subject, String text, Long pollId) {
        List<UserEntity> users = userRepository.findAll();
        String pollLink = "http://localhost:3000/poll/" + pollId + "/comments";

        for (UserEntity user : users) {
            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                try {
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(user.getEmail());
                    message.setSubject(subject);
                    message.setText(text + "\n\nAcesse a enquete pelo link: " + pollLink);
                    emailSender.send(message);
                } catch (Exception e) {
                    System.out.println("Erro ao enviar email para " + user.getEmail() + ": " + e.getMessage());
                }
            }
        }
    }
}


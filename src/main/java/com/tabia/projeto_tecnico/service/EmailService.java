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

    public void sendEmailToAllUsers(String subject, String text) {
        List<UserEntity> users = userRepository.findAll();

        for (UserEntity user : users) {
            String email = user.getEmail();

            if (email != null && !email.isEmpty()) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(email);
                message.setSubject(subject);
                message.setText(text);
                try {
                    emailSender.send(message);
                } catch (Exception e) {
                    System.err.println("Erro ao enviar email para " + email + ": " + e.getMessage());
                }
            } else {
                System.out.println("Usuário " + user.getUsername() + " não possui um email válido.");
            }
        }
    }
}

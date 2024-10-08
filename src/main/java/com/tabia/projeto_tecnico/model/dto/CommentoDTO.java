package com.tabia.projeto_tecnico.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentoDTO {

    private Long id;
    private String content;
    private String firsName;
    private String lastName;
    private Long pollId;
    private LocalDateTime createdAt = LocalDateTime.now();
}

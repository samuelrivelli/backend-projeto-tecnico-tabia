package com.tabia.projeto_tecnico.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Long id;
    private String content;
    private Long userId;
    private String username;
    private Long pollId;
    private LocalDateTime createdAt = LocalDateTime.now();

    public CommentDTO(Long id, String content, Long userId, Long pollId, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.pollId = pollId;
        this.createdAt = createdAt;
    }

}

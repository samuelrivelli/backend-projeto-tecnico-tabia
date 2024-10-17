package com.tabia.projeto_tecnico.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PollDTO {
    private Long id;
    private String title;
    private String description;
    private Long userId;
    private List<OptionDTO> options;
    private List<CommentDTO> comments;
    private LocalDateTime createdAt;
}

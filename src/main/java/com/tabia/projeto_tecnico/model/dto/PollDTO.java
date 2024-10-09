package com.tabia.projeto_tecnico.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PollDTO {
    private Long id;
    private String title;
    private String description;
    private String userId;
    private List<OptionDTO> options;
}

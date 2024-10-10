package com.tabia.projeto_tecnico.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {
    private Long id;
    private Long userId;
    private Long optionId;
    private Long pollId;
}

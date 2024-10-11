package com.tabia.projeto_tecnico.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionDTO {
    private Long id;
    private String text;
    private Long poolId;
    private List<VoteDTO> votes;
    private Long voteCount;
}

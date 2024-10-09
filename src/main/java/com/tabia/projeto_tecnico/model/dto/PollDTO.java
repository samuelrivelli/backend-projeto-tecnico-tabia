package com.tabia.projeto_tecnico.model.dto;

import java.util.List;

public class PollDTO {
    private Long id;
    private String title;
    private String description;
    private String userId;
    private List<OptionDTO> options;
}

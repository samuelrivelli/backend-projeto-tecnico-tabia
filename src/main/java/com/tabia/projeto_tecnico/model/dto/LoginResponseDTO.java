package com.tabia.projeto_tecnico.model.dto;

import com.tabia.projeto_tecnico.enums.UserRole;

public record LoginResponseDTO(String token, Long userId, UserRole role) {
}
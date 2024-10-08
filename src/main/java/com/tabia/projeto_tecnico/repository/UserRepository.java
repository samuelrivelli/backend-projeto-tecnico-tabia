package com.tabia.projeto_tecnico.repository;

import com.tabia.projeto_tecnico.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}

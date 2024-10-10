package com.tabia.projeto_tecnico.repository;

import com.tabia.projeto_tecnico.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserDetails findByUsername(String login);
}

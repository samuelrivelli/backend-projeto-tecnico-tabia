package com.tabia.projeto_tecnico.repository;

import com.tabia.projeto_tecnico.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VoteRepository extends JpaRepository<Vote, UUID> {
}

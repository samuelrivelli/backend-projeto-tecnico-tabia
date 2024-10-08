package com.tabia.projeto_tecnico.repository;

import com.tabia.projeto_tecnico.model.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}

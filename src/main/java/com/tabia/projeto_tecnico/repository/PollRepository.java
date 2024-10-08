package com.tabia.projeto_tecnico.repository;

import com.tabia.projeto_tecnico.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PollRepository extends JpaRepository<Poll, UUID> {
}

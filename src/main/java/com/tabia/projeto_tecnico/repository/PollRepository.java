package com.tabia.projeto_tecnico.repository;

import com.tabia.projeto_tecnico.model.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll, Long> {
}

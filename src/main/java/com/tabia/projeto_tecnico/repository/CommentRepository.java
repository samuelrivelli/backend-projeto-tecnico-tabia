package com.tabia.projeto_tecnico.repository;

import com.tabia.projeto_tecnico.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}

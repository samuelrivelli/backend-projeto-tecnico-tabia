package com.tabia.projeto_tecnico.repository;

import com.tabia.projeto_tecnico.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

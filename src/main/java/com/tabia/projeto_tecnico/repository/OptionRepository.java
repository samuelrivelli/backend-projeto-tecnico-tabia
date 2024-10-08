package com.tabia.projeto_tecnico.repository;

import com.tabia.projeto_tecnico.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OptionRepository extends JpaRepository<Option, UUID> {
}

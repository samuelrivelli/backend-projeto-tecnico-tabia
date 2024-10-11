package com.tabia.projeto_tecnico.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="poll_id", nullable=false)
    @JsonBackReference
    private Poll poll;

    @OneToMany(mappedBy = "option", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Vote> votes = new ArrayList<>();

}

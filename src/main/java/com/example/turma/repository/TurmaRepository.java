package com.example.turma.repository;

import com.example.turma.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
    List<Turma> findByNomeTurmaContaining(String nomeTurma);
    List<Turma> findByTurnoTurmaContaining(String turnoTurma);
}

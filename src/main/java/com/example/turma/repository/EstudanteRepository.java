package com.example.turma.repository;

import com.example.turma.model.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface EstudanteRepository extends JpaRepository<Estudante, Long> {
    List<Estudante> findByTurmaId(Long turmaId);
    List<Estudante> findByNomeEstudanteContaining(String nomeEstudante);

    @Transactional
    void deleteById(Long turmaId);
}

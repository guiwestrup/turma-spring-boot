package com.example.turma.controller;

import com.example.turma.exception.ResourceNotFoundException;
import com.example.turma.model.Turma;
import com.example.turma.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TurmaController {
    @Autowired //Injeção de dependência via anotação Spring
    TurmaRepository turmaRepository;

    @GetMapping("/turmas") //endpoint /turmas implementado para retornar as turmas existentes no bd.
    public ResponseEntity<List<Turma>> getAllTurma(@RequestParam(required = false) String nomeTurma) {
        List<Turma> turmas = new ArrayList<Turma>();
        if (nomeTurma == null) {
            turmaRepository.findAll().forEach(turmas::add);
        } else {
            turmaRepository.findByNomeTurmaContaining(nomeTurma).forEach(turmas::add);
        }

        if (turmas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(turmas, HttpStatus.OK);
    }

    @GetMapping("/turmas/{id}")//endpoint /turmas implementado com o parâmetro id, retorna uma turma
    public ResponseEntity<Turma> getTurmaById(@PathVariable("id") long id) {
        Turma turma = turmaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não foi encontrada turma com o id = " + id));
        return new ResponseEntity<>(turma, HttpStatus.OK);
    }

    @PostMapping("/turmas")//endpoint /turmas implementado com POST para incluir uma turma no bd.
    public ResponseEntity<Turma> createTurma(@RequestBody Turma turma) {
        int x = 0;
        Turma _turma = turmaRepository.save(new Turma(x, turma.getNomeTurma(), turma.getTurnoTurma()));
        return new ResponseEntity<>(_turma, HttpStatus.CREATED);
    }

    @PutMapping("/turmas/{id}")//endpoint /turmas com id para alteração de uma turma no bd.
    public ResponseEntity<Turma> updateTurma(@PathVariable("id") long id, @RequestBody Turma turma) {
        Turma _turma = turmaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não foi encontrada turma com o id = " + id));

        _turma.setNomeTurma(turma.getNomeTurma());

        return new ResponseEntity<>(turmaRepository.save(_turma), HttpStatus.OK);
    }

    @DeleteMapping("/turmas/{id}") //endpoint /turmas com id para exclusão de uma turma
    public ResponseEntity<HttpStatus> deleteTurma(@PathVariable("id") long id) {
        turmaRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/turmas") //endpoint /turmas para exclusão de todas as turmas.
    public ResponseEntity<HttpStatus> deleteAllTurma() {
        turmaRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

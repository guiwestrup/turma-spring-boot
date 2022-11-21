package com.example.turma.controller;

import com.example.turma.exception.ResourceNotFoundException;
import com.example.turma.model.Estudante;
import com.example.turma.repository.EstudanteRepository;
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
public class EstudanteController {
    @Autowired
        private TurmaRepository turmaRepository;

    @Autowired
        private EstudanteRepository estudanteRepository;

    @GetMapping("/estudantes")//endpoint para a busca de todos os estudantes no bd.
    public ResponseEntity<List<Estudante>> getAllEstudante(@RequestParam(required = false) String nomeEstudante) {
        List<Estudante> estudantes = new ArrayList<>();
        if (nomeEstudante == null) {
            estudantes.addAll(estudanteRepository.findAll());
        }
        else{
            estudantes.addAll(estudanteRepository.findByNomeEstudanteContaining(nomeEstudante));
        }

        if (estudantes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(estudantes, HttpStatus.OK);
    }

    @GetMapping("/turmas/{turmaId}/estudantes")//endpoint para a busca de estudantes por turma
    public ResponseEntity<List<Estudante>> getAllEstudanteByTurmaId(@PathVariable(value = "turmaId") Long turmaId) {
        if (!turmaRepository.existsById(turmaId)){
            throw new ResourceNotFoundException("Não foi encontrada turma com o Id = " + turmaId);
        }

        List<Estudante> estudantes = estudanteRepository.findByTurmaId(turmaId);
        return new ResponseEntity<>(estudantes, HttpStatus.OK);
    }

    @GetMapping("/estudantes/{id}")//endpoint para a busca de estudante por id
    public ResponseEntity<Estudante> getEstudanteByTurmaId(@PathVariable(value = "id") Long id) {
        Estudante estudante = estudanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado estudante com o id = " + id));
        return new ResponseEntity<>(estudante, HttpStatus.OK);
    }

    @PostMapping("/turmas/{turmaId}/estudantes") //endpoint para inserção de estudantes por turma.
    public ResponseEntity<Estudante> createEstudante(@PathVariable(value = "turmaId") Long turmaId, @RequestBody Estudante estudanteRequest) {
        Estudante estudante = turmaRepository.findById(turmaId).map(turma -> {
            estudanteRequest.setTurma(turma);
            return estudanteRepository.save(estudanteRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado turma com id = " +
                turmaId));
        return new ResponseEntity<>(estudante, HttpStatus.CREATED);
    }

    @PutMapping("/estudantes/{id}") //endpoint para alteração de estudantes por id
    public ResponseEntity<Estudante> updateEstudante(@PathVariable("id") long id, @RequestBody
    Estudante estudanteRequest) {
        Estudante estudante = estudanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EstudanteId " + id + "Não encontrado"));
                        estudante.setNomeEstudante(estudanteRequest.getNomeEstudante());
        return new ResponseEntity<>(estudanteRepository.save(estudante), HttpStatus.OK);
    }
    @DeleteMapping("/estudantes/{id}") //endpoint para exclusão de estudantes por id
    public ResponseEntity<HttpStatus> deleteEstudante(@PathVariable("id") long id) {
        estudanteRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/turmas/{turmaId}/estudantes")//endpoint para exclusão de estudantes por turma
    public ResponseEntity<List<Estudante>> deleteAllEstudanteOfTurma(@PathVariable(value =
            "turmaId") Long turmaId) {
        if (!turmaRepository.existsById(turmaId)) {
            throw new ResourceNotFoundException("Não encontrado turma com o id = " + turmaId);
        }
        estudanteRepository.deleteById(turmaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

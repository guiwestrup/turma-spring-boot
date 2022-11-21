package com.example.turma.model;

import javax.persistence.*;

@Entity
@Table(name = "turma")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "turma_generator")
    private long id;
    @Column(name = "nome_turma")
    private String nomeTurma;
    @Column(name = "turno_turma")
    private String turnoTurma;

    public Turma() {
    }

    public Turma(long id, String nomeTurma, String turnoTurma) {
        this.id = id;
        this.nomeTurma = nomeTurma;
        this.turnoTurma = turnoTurma;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }

    public String getTurnoTurma() {
        return turnoTurma;
    }

    public void setTurnoTurma(String turnoTurma) {
        this.turnoTurma = turnoTurma;
    }
}

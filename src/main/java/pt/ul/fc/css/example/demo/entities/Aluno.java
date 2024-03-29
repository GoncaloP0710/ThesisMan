package pt.ul.fc.css.example.demo.entities;

import java.time.LocalDate;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Aluno {
    
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int n_aluno;
    double average;
    @ManyToOne
    Candidatura candidatura;

    public Aluno(double average, Candidatura candidatura) {
        this.average = average;
        this.candidatura = candidatura;
    }

    public int getNumAluno() {
        return n_aluno;
    }

    public double getAverage() {
        return average;
    }

    public Candidatura getCandidatura() {
        return candidatura;
    }

}
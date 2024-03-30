package pt.ul.fc.css.example.demo.entities;

import java.util.Objects;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Aluno")
public class Aluno {
    
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer n_aluno;

    @NonNull
    private Double average;

    @ManyToOne
    private Candidatura candidatura;

    
    public Aluno(double average, Candidatura candidatura) {
        this.average = average;
        this.candidatura = candidatura;
    }

    public Integer getNumAluno() {
        return n_aluno;
    }

    public Double getAverage() {
        return average;
    }

    public Candidatura getCandidatura() {
        return candidatura;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        var that = (Aluno) obj;
        return Objects.equals(this.n_aluno, that.n_aluno) &&
                Objects.equals(this.average, that.average) &&
                Objects.equals(this.candidatura, that.candidatura);
    }

    @Override
    public int hashCode() {
        return Objects.hash(n_aluno, average, candidatura);
    }

    @Override
    public String toString() {
        return "Aluno[" +
                "n_aluno=" + n_aluno + ", " +
                "average=" + average + ", " +
                "candidatura=" + candidatura + ']';
    }

}
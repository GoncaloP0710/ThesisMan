package pt.ul.fc.css.example.demo.entities;

import java.util.Objects;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;


@Entity
public class Aluno extends Utilizador{
    
    @NonNull
    private Double average;

    @ManyToOne
    private Candidatura candidatura;

    
    public Aluno(double average, String name, String contact) {
        super(name, contact);
        this.average = average;
        this.candidatura = null;
    }

    protected Aluno() {
        super();
        this.average = 0.0;
        this.candidatura = null;
    }

    public Integer getNumAluno() {
        return super.getId();
    }

    public Double getAverage() {
        return average;
    }

    public Candidatura getCandidatura() {
        return (candidatura != null) ? this.candidatura : null;
    }

    public void setCandidatura(Candidatura candidatura) {
        this.candidatura = candidatura;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        var that = (Aluno) obj;
        return Objects.equals(super.getId(), that.getId()) &&
                Objects.equals(this.average, that.average) &&
                Objects.equals(this.candidatura, that.candidatura);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), average);
    }

    @Override
    public String toString() {
        return "Aluno[" +
                "n_aluno=" + super.getId() + ", " +
                "average=" + average + ", " +']';
    }

}
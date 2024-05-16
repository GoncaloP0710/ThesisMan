package pt.ul.fc.css.example.demo.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;


@Entity
public class Aluno extends Utilizador{
    
    @NonNull
    private Double average;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Candidatura> candidaturas;

    @ManyToOne
    @JoinColumn(name="mestrado_id", nullable = false)
    private Mestrado mestrado;
    
    
    public Aluno(double average, String name, String contact, Mestrado mestrado) {
        super(name, contact);
        this.average = average;
        this.candidaturas = new ArrayList<Candidatura>();
        this.mestrado = mestrado;
    }

    
    public Aluno(double average, String name, String contact, Mestrado mestrado, List<Candidatura> candidaturas) {
        super(name, contact);
        this.average = average;
        this.candidaturas = candidaturas;
        this.mestrado = mestrado;
    }

    protected Aluno() {
        super();
        this.average = 0.0;
        this.candidaturas = new ArrayList<Candidatura>();
        this.mestrado = new Mestrado();
    }

    /**
     * Returns the student's identification number.
     *
     * @return the student's identification number
     */
    public Integer getId() {
        return super.getId();
    }

    /**
     * Returns the average of the student.
     *
     * @return the average of the student
     */
    public Double getAverage() {
        return average;
    }

    /**
     * Returns the list of candidaturas associated with the student.
     *
     * @return the list of candidaturas associated with the student, or null if there are no candidaturas
     */
    public List<Candidatura> getCandidatura() {
        return (candidaturas != null) ? this.candidaturas : null;
    }

    /**
     * Adds a candidatura to the student's list of candidaturas.
     *
     * @param candidatura the candidatura to be added
     */
    public void addCandidatura(Candidatura candidatura) {
        candidaturas.add(candidatura);
    }

    /**
     * Returns the mestrado associated with the student.
     *
     * @return the mestrado associated with the student
     */
    public Mestrado getMestrado() {
        return this.mestrado;
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
                Objects.equals(this.candidaturas, that.candidaturas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), average);
    }

    @Override
    public String toString() {
        StringBuilder candidaturasIds = new StringBuilder();
        for(Candidatura candidatura: this.candidaturas){
            candidaturasIds.append(candidatura.getId()).append(", ");
        }
        return "Aluno[" +
                "n_aluno=" + super.getId() + ", " +
                "ids_candidaturas=" + candidaturasIds.toString() + ", " +
                "average=" + average + ", " +']';
    }

}
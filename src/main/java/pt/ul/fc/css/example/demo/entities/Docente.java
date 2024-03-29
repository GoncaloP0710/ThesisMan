package pt.ul.fc.css.example.demo.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "DOCENTE")
public class Docente {
    
    @Id @Column(name = "num_faculdade") @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer numFaculdade;
    @NonNull
    private String departamento;
    @ElementCollection
    @Column(name = "temas_propostos")
    private List<Tema> temasPropostos;

    public Docente(String departamento, List<Tema> temas) {
        this.departamento = departamento;
        temasPropostos = temas;
    }
    
    public Integer getNumFaculdade() {
        return numFaculdade;
    } 

    public String getDepartamento() {
        return departamento;
    }

    public List<Tema> getTemasPropostos() {
        return temasPropostos;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        var that = (Docente) obj;
        return Objects.equals(this.numFaculdade, that.numFaculdade) &&
                Objects.equals(this.departamento, that.departamento) &&
                Objects.equals(this.temasPropostos, that.temasPropostos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numFaculdade, departamento, temasPropostos);
    }

    @Override
    public String toString() {
        return "Docente[" +
                "num docente=" + numFaculdade + ", " +
                "departamento=" + departamento + ", " +
                "temas propostos=" + temasPropostos + ']';
    }
}

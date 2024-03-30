package pt.ul.fc.css.example.demo.entities;

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

@Entity
public class Docente {
    
    @Id @Column(name = "num_faculdade") @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @OneToMany(mappedBy = "orientadorInterno")
    private Integer numDocente;

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
        return this.numDocente;
    } 

    public String getDepartamento() {
        return this.departamento;
    }

    public List<Tema> getTemasPropostos() {
        return this.temasPropostos;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        var that = (Docente) obj;
        return Objects.equals(this.numDocente, that.numDocente) &&
                Objects.equals(this.departamento, that.departamento) &&
                Objects.equals(this.temasPropostos, that.temasPropostos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numDocente, departamento, temasPropostos);
    }

    @Override
    public String toString() {
        return "Docente[" +
                "num docente=" + numDocente + ", " +
                "departamento=" + departamento + ", " +
                "temas propostos=" + temasPropostos + ']';
    }
}

package pt.ul.fc.css.example.demo.entities;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Juri {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer juri_id;
    
    @ManyToOne
    @JoinColumn(name="presidente_id")
    private Docente presidente;

    @NonNull
    @ManyToOne
    @JoinColumn(name="arguente_id")
    private Docente arguente;

    @NonNull
    @ManyToOne
    @JoinColumn(name="orientador_id")
    private Docente DocenteOrientador;

    public Juri(Docente presidente, Docente arguente, Docente DocenteOrientador) {
        this.presidente = presidente;
        this.arguente = arguente;
        this.DocenteOrientador = DocenteOrientador;
    }

    public Juri(Docente arguente, Docente DocenteOrientador) {
        this.presidente = null;
        this.arguente = arguente;
        this.DocenteOrientador = DocenteOrientador;
    }

    public Juri() {
        this.presidente = null;
        this.arguente = null;
        this.DocenteOrientador = null;
    }

    public Docente getPresidente() {
        return presidente;
    }

    public Docente getArguente() {
        return arguente;
    }

    public Docente getDocenteOrientador() {
        return DocenteOrientador;
    }

}

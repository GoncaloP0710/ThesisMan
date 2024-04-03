package pt.ul.fc.css.example.demo.entities;

import java.util.Objects;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Represents a Juri entity.
 */
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
    private Docente docenteOrientador;


    public Juri(@NonNull Docente arguente, @NonNull Docente docenteOrientador, Docente presidente) {
        this.presidente = presidente;
        this.arguente = arguente;
        this.docenteOrientador = docenteOrientador;
    }

    
    public Juri(@NonNull Docente arguente, @NonNull Docente docenteOrientador) {
        this.presidente = null;
        this.arguente = arguente;
        this.docenteOrientador = docenteOrientador;
    }

   
    public Juri() {
        this.presidente = null;
        this.arguente = null;
        this.docenteOrientador = null;
    }

    /**
     * Returns the Id of the Juri
     * 
     * @return id of the Juri
     */
    public Integer getJuriId() {
        return this.juri_id;
    }
    
    /**
     * Returns the presidente Docente.
     * 
     * @return The presidente Docente.
     */
    public Docente getPresidente() {
        return presidente;
    }

    /**
     * Returns the arguente Docente.
     * 
     * @return The arguente Docente.
     */
    public Docente getArguente() {
        return arguente;
    }

    /**
     * Returns the Docente Orientador.
     * 
     * @return The Docente Orientador.
     */
    public Docente getDocenteOrientador() {
        return docenteOrientador;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        var that = (Juri) obj;
        return Objects.equals(this.juri_id, that.getJuriId()) &&
                this.presidente.equals(that.getPresidente()) &&
                this.arguente.equals(that.getArguente()) &&
                this.docenteOrientador.equals(that.getDocenteOrientador());
    }

    @Override
    public int hashCode() {
        return Objects.hash(juri_id, presidente, arguente, docenteOrientador);
    }

    @Override
    public String toString() {
        return "Juri[" +
                "juri_id=" + juri_id + ", " +
                "presidente=" + ((presidente != null) ? this.presidente.getName() : " ") + ", " +
                "arguente=" + this.arguente.getName() + ", " +
                "Docente Orientador=" + this.docenteOrientador.getName() + ']';
    }

}

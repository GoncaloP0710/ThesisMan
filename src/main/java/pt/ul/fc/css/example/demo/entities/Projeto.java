package pt.ul.fc.css.example.demo.entities;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Represents a Projeto entity.
 */
@Entity
public class Projeto extends Tese {

    @ManyToOne
    @JoinColumn(name="docente_id")
    private Docente docente;

    
    public Projeto(Candidatura candidatura) {
		super(candidatura);
        this.docente = null;
	}

    
    public Projeto() {
        super();
        this.docente = null;
    }

    /**
     * Retrieves the Docente object associated with the Projeto.
     *
     * @return The Docente object associated with the Projeto, or null if not set.
     */
    public Docente getDocente() {
        return (this.docente == null ? null : this.docente);
    }

    /**
     * Sets the Orientador Interno.
     *
     * @param docente The Docente object to associate with the Projeto.
     */
    public void setOrientadorInterno(Docente docente) {
        this.docente = docente;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        var that = (Projeto) obj;
        return Objects.equals(this.docente, that.docente) &&
                Objects.equals(super.getId(), that.getId()) &&
                Objects.equals(super.getDefesas(), that.getDefesas()) &&
                Objects.equals(super.getCandidatura(), that.getCandidatura());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.docente, super.getId(), super.getDefesas(), super.getCandidatura());
    }

    @Override
    public String toString() {
        StringBuilder defesasIds = new StringBuilder();
        for(Defesa defesa: super.getDefesas()){
            defesasIds.append(defesa.getId()).append(", ");
        }
        return "Tese[" +
                "docente=" + this.docente.toString() + ", " +
                "Id=" + super.getId().toString() + ", " +
                "defesas=" +  defesasIds.toString() + ", " +
                "candidatura=" + super.getCandidatura().getId() + ']';
    }
}
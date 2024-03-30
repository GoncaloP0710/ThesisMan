package pt.ul.fc.css.example.demo.entities;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Representa uma Projeto
 */
@Entity
public class Projeto extends Tese {

    @ManyToOne
    @JoinColumn(name="docente_id", nullable = false)
    private Docente docente;

    public Projeto(List<Defesa> defesas) {
		super(defesas);
	}

    public Docente getDocente() {
        return docente;
    }

    // @Override
    // public boolean equals(Object obj) {
    //     if (obj == this)
    //         return true;
    //     if (obj == null || obj.getClass() != this.getClass())
    //         return false;
    //     var that = (Projeto) obj;
    //     return Objects.equals(this.id, that.id) &&
    //             Objects.equals(this.defesaProposta, that.defesaProposta) &&
    //             Objects.equals(this.defesaFinal, that.defesaFinal) && 
    //             Objects.equals(this.orientadorInterno, that.orientadorInterno);
    // }

    // @Override
    // public int hashCode() {
    //     return Objects.hash(this.id, this.defesaProposta, this.defesaFinal, this.orientadorInterno);
    // }

    // @Override
    // public String toString() {
    //     return "Tese[" +
    //             "id=" + this.id + ", " +
    //             "Id da defesa proposta=" + this.defesaProposta.getId() + ", " +
    //             "Id da defesa final=" + this.defesaFinal.getId() + ", " +
    //             "orientadorInterno=" + this.orientadorInterno.toString() + ']';
    // }
}
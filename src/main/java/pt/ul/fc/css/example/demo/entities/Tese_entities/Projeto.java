package pt.ul.fc.css.example.demo.entities.Tese_entities;

import pt.ul.fc.css.example.demo.entities.Tese_entities.Tese;
import pt.ul.fc.css.example.demo.entities.Defesa;
import java.util.Objects;

/**
 * Representa uma Projeto
 */
public abstract class Projeto extends Tese {

    @ManyToOne
    @JoinColumn(name="orientador_interno", nullable = false)
    private Docente orientadorInterno;

    public Projeto(Defesa defesaProposta, Defesa defesaFinal) {
		super(defesaProposta,defesaFinal);
	}

    public Docente getOrientadorInterno() {
        return orientadorInterno;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        var that = (Tese) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.defesaProposta, that.defesaProposta) &&
                Objects.equals(this.defesaFinal, that.defesaFinal) && 
                Objects.equals(this.orientadorInterno, that.orientadorInterno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.defesaProposta, this.defesaFinal, this.orientadorInterno);
    }

    @Override
    public String toString() {
        return "Tese[" +
                "id=" + this.id + ", " +
                "defesaProposta=" + this.defesaProposta.toString + ", " +
                "defesaFinal=" + this.defesaFinal.toString + ", " +
                "orientadorInterno=" + this.orientadorInterno.toString + ']';
    }
}
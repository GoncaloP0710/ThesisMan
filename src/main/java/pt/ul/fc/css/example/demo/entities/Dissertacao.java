package pt.ul.fc.css.example.demo.entities;
import java.util.List;
import jakarta.persistence.Entity;

/**
 * Representa uma Dissertacao
 */
@Entity
public class Dissertacao extends Tese {

    public Dissertacao(List<Defesa> defesas) {
		super(defesas);
	}
	public Dissertacao() {
		super();
	}
}
package pt.ul.fc.css.example.demo.entities;
import jakarta.persistence.Entity;

/**
 * Representa uma Dissertacao. 
 */
@Entity
public class Dissertacao extends Tese {

	public Dissertacao(Candidatura candidatura) {
		super(candidatura);
	}

	public Dissertacao() {
		super();
	}
}
package pt.ul.fc.css.example.demo.entities;
import jakarta.persistence.Entity;

/**
 * Represents a Dissertacao entity. 
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
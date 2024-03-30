package pt.ul.fc.css.example.demo.entities;

/**
 * Representa uma Dissertacao
 */
public abstract class Dissertacao extends Tese {

    public Dissertacao(Defesa defesaProposta, Defesa defesaFinal) {
		super(defesaProposta,defesaFinal);
	}
}
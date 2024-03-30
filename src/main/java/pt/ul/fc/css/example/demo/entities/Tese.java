package pt.ul.fc.css.example.demo.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;


import java.util.Objects;

/**
 * Representa uma tese
 */
@Entity
public abstract class Tese {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    protected Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "defesa_proposta", referencedColumnName = "id")
    protected Defesa defesaProposta;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "defesa_final", referencedColumnName = "id")
    protected Defesa defesaFinal;

    public Tese(Defesa defesaProposta, Defesa defesaFinal) {
        this.defesaProposta = defesaProposta;
        this.defesaFinal = defesaFinal;
    }

    public Integer getId() {
        return id;
    }

    public Defesa getDefesaProposta() {
        return defesaProposta;
    }

    public Defesa getDefesaFinal() {
        return defesaFinal;
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
                Objects.equals(this.defesaFinal, that.defesaFinal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, defesaProposta, defesaFinal);
    }

    @Override
    public String toString() {
        return "Tese[" +
                "id=" + this.id + ", " +
                "Id da defesaProposta=" + (this.defesaProposta == null ? "not defined": this.defesaProposta.getId())  + ", " +
                "Id da defesaFinal=" + (this.defesaFinal == null ? "not defined": this.defesaFinal.getId()) + ']';
    }
}

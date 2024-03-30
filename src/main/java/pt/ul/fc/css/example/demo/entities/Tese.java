package pt.ul.fc.css.example.demo.entities;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.InheritanceType;
import java.util.List;


/**
 * Representa uma tese
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Tese {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    protected Integer id;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "candidatura_id", referencedColumnName = "id")
    private Candidatura candidatura;

    @OneToMany(mappedBy="tese")
    protected List<Defesa> defesas;

    // @OneToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "defesa_proposta", referencedColumnName = "id")
    // protected Defesa defesa;

    // @OneToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "defesa_final", referencedColumnName = "id")
    // protected Defesa defesaFinal;

    public Tese(List<Defesa> defesas) {
        // this.defesaProposta = defesaProposta;
        // this.defesaFinal = defesaFinal;
        this.defesas = defesas;
    }

    public Tese() {
        this.defesas = null;
    }

    public Integer getId() {
        return id;
    }

    public List<Defesa> getDefesaProposta() {
        return this.defesas;
    }

    // public Defesa getDefesaFinal() {
    //     return defesaFinal;
    // }

    // @Override
    // public boolean equals(Object obj) {
    //     if (obj == this)
    //         return true;
    //     if (obj == null || obj.getClass() != this.getClass())
    //         return false;
    //     var that = (Tese) obj;
    //     return Objects.equals(this.id, that.id) &&
    //             Objects.equals(this.defesaProposta, that.defesaProposta) &&
    //             Objects.equals(this.defesaFinal, that.defesaFinal);
    // }

    // @Override
    // public int hashCode() {
    //     return Objects.hash(id, defesaProposta, defesaFinal);
    // }

    // @Override
    // public String toString() {
    //     return "Tese[" +
    //             "id=" + this.id + ", " +
    //             "Id da defesaProposta=" + (this.defesaProposta == null ? "not defined": this.defesaProposta.getId())  + ", " +
    //             "Id da defesaFinal=" + (this.defesaFinal == null ? "not defined": this.defesaFinal.getId()) + ']';
    // }
}

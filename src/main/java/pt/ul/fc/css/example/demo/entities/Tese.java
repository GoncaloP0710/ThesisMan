package pt.ul.fc.css.example.demo.entities;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.DiscriminatorType;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Representa uma tese
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Tese {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    protected Integer id;

    @OneToOne(cascade= CascadeType.MERGE)
    @JoinColumn(name = "candidatura_id", referencedColumnName = "id")
    private Candidatura candidatura;

    @OneToMany(mappedBy="tese", cascade = CascadeType.ALL)
    protected List<Defesa> defesas;

    public Tese(Candidatura candidatura) {
        this.candidatura = candidatura;
        this.defesas = new ArrayList<Defesa>();
    }

    public Tese() {
        this.candidatura = null;
        this.defesas = new ArrayList<Defesa>();
    }

    public Integer getId() {
        return id;
    }

    public List<Defesa> getDefesas() {
        return this.defesas;
    }

    public Candidatura getCandidatura() {
        return (this.candidatura == null) ? null: this.candidatura;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        var that = (Tese) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.candidatura, that.candidatura) &&
                Objects.equals(this.defesas, that.defesas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, candidatura, defesas);
    }

    @Override
    public String toString() {
        StringBuilder defesasIds = new StringBuilder();
        for(Defesa defesa: defesas){
            defesasIds.append(defesa.getId()).append(", ");
        }
        return "Tese[" +
                "id=" + this.id + ", " +
                "Id da candidatura=" + candidatura.getId() + ", " +
                "Id das defesas=" + defesasIds.toString() + ']';
    }
}

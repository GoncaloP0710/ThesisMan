package pt.ul.fc.css.example.demo.entities;

import java.util.List;
import java.util.Objects;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class UtilizadorEmpresarial extends Utilizador{
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NonNull
    private String empresa;

    @ElementCollection
    @CollectionTable(
        name="temas_propostos",
        joinColumns=@JoinColumn(name="docente_id")
    )
    private List<Tema> temasPropostos;

    public UtilizadorEmpresarial(String empresa, List<Tema> temas, String name, String contact) {
        super(name, contact);
        this.empresa = empresa;
        temasPropostos = temas;
    }

    protected UtilizadorEmpresarial() {
        super();
        this.empresa = "";
        this.temasPropostos = null;
    }

    public Integer getId() {
        return id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public List<Tema> getTemasPropostos() {
        return temasPropostos;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        var that = (UtilizadorEmpresarial) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.empresa, that.empresa) &&
                Objects.equals(this.temasPropostos, that.temasPropostos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, empresa, temasPropostos);
    }

    @Override
    public String toString() {
        return "Utilizador Empresarial[" +
                "id=" + id + ", " +
                "empresa=" + empresa + ", " +
                "temas propostos=" + temasPropostos + ']';
    }
}

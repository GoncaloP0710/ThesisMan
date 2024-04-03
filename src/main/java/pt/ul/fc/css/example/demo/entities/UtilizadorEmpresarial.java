package pt.ul.fc.css.example.demo.entities;

import java.util.ArrayList;
import java.util.Objects;
import java.util.List;


import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;


/**
 * Represents a UtilizadorEmpresarial entity.
 */
@Entity
public class UtilizadorEmpresarial extends Utilizador {

    @NonNull
    private String empresa;

    @OneToMany(mappedBy = "submissor")
    private List<Tema> temasPropostos;


    public UtilizadorEmpresarial(String empresa, String name, String contact) {
        super(name, contact);
        this.empresa = empresa;
        temasPropostos = new ArrayList<Tema>();
    }


    protected UtilizadorEmpresarial() {
        super();
        this.empresa = "";
        this.temasPropostos = new ArrayList<Tema>();
    }

    /**
     * Returns the empresa of the UtilizadorEmpresarial.
     *
     * @return the empresa of the UtilizadorEmpresarial
     */
    public String getEmpresa() {
        return empresa;
    }

    /**
     * Returns the list of temasPropostos of the UtilizadorEmpresarial.
     *
     * @return the list of temasPropostos of the UtilizadorEmpresarial
     */
    public List<Tema> getTemasPropostos() {
        return temasPropostos;
    }

    /**
     * Sets the empresa of the UtilizadorEmpresarial.
     *
     * @param empresa the empresa to be set
     */
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    /**
     * Sets the list of temasPropostos of the UtilizadorEmpresarial.
     *
     * @param temasPropostos the list of temasPropostos to be set
     */
    public void setTemas(List<Tema> temasPropostos) {
        this.temasPropostos = temasPropostos;
    }

    /**
     * Adds the tema to this.temasPropostos of the UtilizadorEmpresarial
     * 
     * @param tema the tema to be added
     */
    public void addTemaPropostos(Tema tema) {
        this.temasPropostos.add(tema);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        var that = (UtilizadorEmpresarial) obj;
        return Objects.equals(super.getId(), that.getId()) &&
                Objects.equals(this.empresa, that.empresa) &&
                Objects.equals(this.temasPropostos, that.temasPropostos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), empresa, temasPropostos);
    }

    @Override
    public String toString() {
        return "Utilizador Empresarial[" +
                "id=" + super.getId() + ", " +
                "empresa=" + empresa + ", " +
                "temas propostos=" + temasPropostos + ']';
    }
}

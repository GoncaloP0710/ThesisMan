package pt.ul.fc.css.example.demo.entities;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

/**
 * Represents a Docente (Teacher) entity.
 */
@Entity
public class Docente extends Utilizador{

    @NonNull
    private String departamento;

    @NonNull
    private Boolean isAdmnistrador;

    @OneToMany(mappedBy = "submissor")
    private List<Tema> temasPropostos;

    @OneToMany(mappedBy="docente")
    private List<Projeto> projeto;

    public Docente(String departamento, boolean isAdmin, String name, String contact) {
        super(name, contact);
        this.departamento = departamento;
        temasPropostos = new ArrayList<Tema>();
        isAdmnistrador = isAdmin;
    }

    protected Docente() {
        super();
        this.departamento = "";
        this.temasPropostos = new ArrayList<Tema>();
        isAdmnistrador = false;
    }
    
    /**
     * Checks if the Docente is an administrator.
     *
     * @return true if the Docente is an administrator, false otherwise
     */
    public Boolean isAdmnistrador() {
        return this.isAdmnistrador;
    }

    /**
     * Gets the departamento of the Docente.
     *
     * @return the departamento of the Docente
     */
    public String getDepartamento() {
        return this.departamento;
    }

    /**
     * Gets the list of temasPropostos (proposed themes) by the Docente.
     *
     * @return the list of temasPropostos by the Docente
     */
    public List<Tema> getTemasPropostos() {
        return this.temasPropostos;
    }

    /**
     * Adds a Tema to the list of temasPropostos by the Docente.
     *
     * @param tema the Tema to be added to the list of temasPropostos
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
        var that = (Docente) obj;
        return Objects.equals(super.getId(), that.getId()) &&
                Objects.equals(this.departamento, that.departamento) &&
                Objects.equals(this.temasPropostos, that.temasPropostos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), departamento, temasPropostos);
    }

    @Override
    public String toString() {
        return "Docente[" +
                "num docente=" + super.getId() + ", " +
                "departamento=" + departamento + ", " +
                "temas propostos=" + temasPropostos.toString() + ", " + 
                "é administrador=" + (isAdmnistrador ? "Sim" : "Não") + ']';
    }
}

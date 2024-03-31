package pt.ul.fc.css.example.demo.entities;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

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
    
    public Boolean isAdmnistrador() {
        return this.isAdmnistrador;
    }

    public String getDepartamento() {
        return this.departamento;
    }

    public List<Tema> getTemasPropostos() {
        return this.temasPropostos;
    }

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

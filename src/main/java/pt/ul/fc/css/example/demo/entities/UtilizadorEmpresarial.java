package pt.ul.fc.css.example.demo.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;

@Entity
public class UtilizadorEmpresarial {
    
    String empresa;
    @ElementCollection
    @Column(name = "temas_propostos")
    List<Tema> temasPropostos;

    public UtilizadorEmpresarial(String empresa, List<Tema> temas) {
        this.empresa = empresa;
        temasPropostos = temas;
    }

    public getEmpresa() {
        return empresa;
    }

    public getTemasPropostos() {
        return temasPropostos;
    }
}

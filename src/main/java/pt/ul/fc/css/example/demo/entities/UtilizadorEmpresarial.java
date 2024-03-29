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

    public getEmpresa() {
        return empresa;
    }

    public getTemasPropostos() {
        return temasPropostos;
    }
}

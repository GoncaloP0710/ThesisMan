package pt.ul.fc.css.example.demo.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Docente {
    
    @Id @Column(name = "num_faculdade") @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int numFaculdade;
    String departamento;
    @ElementCollection
    @Column(name = "temas_propostos")
    List<Tema> temasPropostos;
    
    public int getNumFaculdade() {
        return numFaculdade;
    } 

    public String getDepartamento() {
        return departamento;
    }

    public List<Tema> getTemasPropostos() {
        return temasPropostos;
    }
}

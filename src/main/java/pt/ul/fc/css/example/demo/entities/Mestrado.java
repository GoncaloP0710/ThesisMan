package pt.ul.fc.css.example.demo.entities;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Mestrado {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer mestradoId;
    
    @NonNull
    private String nome;

    @OneToOne()
    @JoinColumn(name="coordenador_id")
    private Docente coordenador;

    public Mestrado(String nome, Docente coordenador) {
        this.nome = nome;
        this.coordenador = (coordenador.isAdmnistrador()) ? coordenador: null;
        if (this.coordenador == null) {
            System.out.println("Mestrado: " + nome + "não tem administrador");
        }
    }

    public Mestrado(String nome){
        this.nome = nome;
        this.coordenador = null;
    }

    protected Mestrado() {
        this.nome = "";
        this.coordenador = null;
    }

    public String getNome() {
        return nome;
    }

    public Docente getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Docente coordenador){
        this.coordenador = (coordenador.isAdmnistrador()) ? coordenador: null;
        if (this.coordenador == null) {
            System.out.println("Mestrado: " + nome + "não tem administrador");
        }
    }
}

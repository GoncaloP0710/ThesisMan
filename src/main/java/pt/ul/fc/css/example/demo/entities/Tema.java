package pt.ul.fc.css.example.demo.entities;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Tema {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NonNull
    private String titulo;

    @NonNull
    private String descricao;

    @NonNull
    private float remuneracaoMensal;

    @NonNull
    private Utilizador submissor;


    public Tema(@NonNull String titulo, @NonNull String descricao, float remuneracaoMensal, Utilizador submissor) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.remuneracaoMensal = remuneracaoMensal;
        this.submissor = submissor;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public float getRemuneracaoMensal() {
        return remuneracaoMensal;
    }

    public Utilizador getSubmissor() {
        return submissor;
    }


}

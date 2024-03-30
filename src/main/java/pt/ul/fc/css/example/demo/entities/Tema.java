package pt.ul.fc.css.example.demo.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tema")
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

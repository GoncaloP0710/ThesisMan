package pt.ul.fc.css.example.demo.entities;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;


@Entity
public class Tema {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer temaId;

    @NonNull
    private String titulo;

    @NonNull
    private String descricao;

    @NonNull
    private float remuneracaoMensal;

    @ManyToMany(fetch = jakarta.persistence.FetchType.EAGER)
    @JoinTable(
        name = "tema_mestrado", 
        joinColumns = @JoinColumn(name = "tema_id"), 
        inverseJoinColumns = @JoinColumn(name = "mestrado_id"))
    private List<Mestrado> mestradosCompativeis;

    @NonNull
    @ManyToOne
    @JoinColumn(name="userId", nullable = false)
    private Utilizador submissor;


    public Tema(@NonNull String titulo, @NonNull String descricao, float remuneracaoMensal, @NonNull Docente submissor, List<Mestrado> mestradosCompativeis){
        this.titulo = titulo;
        this.descricao = descricao;
        this.remuneracaoMensal = remuneracaoMensal;
        this.submissor = submissor;
        this.mestradosCompativeis = mestradosCompativeis;
    }

    public Tema(@NonNull String titulo, @NonNull String descricao, float remuneracaoMensal, @NonNull Docente submissor){
        this.titulo = titulo;
        this.descricao = descricao;
        this.remuneracaoMensal = remuneracaoMensal;
        this.submissor = submissor;
        this.mestradosCompativeis = new ArrayList<Mestrado>();
    }

    public Tema() {
        this.titulo = "";
        this.descricao = "";
        this.remuneracaoMensal = 0;
        this.submissor = null;
        this.mestradosCompativeis = new ArrayList<Mestrado>();
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

    public List<Mestrado> getMestrados() {
        return this.mestradosCompativeis;
    }

    public void addMestradosCompativeis(Mestrado mestrado){
        this.mestradosCompativeis.add(mestrado);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) 
            return true;
        if (obj == null || obj.getClass() != this.getClass()) 
            return false;
            var that = (Tema) obj;
            return Objects.equals(this.temaId, that.temaId) &&
            Objects.equals(this.titulo, that.titulo) &&
            Objects.equals(this.descricao, that.descricao) &&
            Objects.equals(this.remuneracaoMensal, that.remuneracaoMensal) &&
            submissor.equals(that.submissor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(temaId);
    }

    @Override
    public String toString() {
        return "Tema{" +
                "temaId=" + temaId +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", remuneracaoMensal=" + remuneracaoMensal +
                ", submissor=" + submissor +
                '}';
    }


}

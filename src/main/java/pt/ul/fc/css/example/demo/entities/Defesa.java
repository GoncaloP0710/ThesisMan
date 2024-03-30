package pt.ul.fc.css.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "defesa")
public final class Defesa{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NonNull
    Boolean isFinal;

    @NonNull
    Boolean isPresencial;

    @NonNull
    Date date;

    @NonNull
    Integer duracao;

    @NonNull
    float nota;

    @NonNull
    @OneToOne(mappedBy = "defesa")
    private Tese tese;

    
    String sala;

    public Defesa(@NonNull boolean isFinal, @NonNull boolean isPresencial, @NonNull Date date, @NonNull int duracao, @NonNull float nota, String sala) {
        this.isFinal = isFinal;
        this.isPresencial = isPresencial;
        this.date = date;
        this.duracao = duracao;
        this.nota = nota;
        this.sala = sala;
    }

    public int getId() {
        return id;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public boolean isPresencial() {
        return isPresencial;
    }

    public Date getDate() {
        return date;
    }

    public int getDuracao() {
        return duracao;
    }

    public float getNota() {
        return nota;
    }

    public String getSala() {
        return sala;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) 
            return true;
        if (obj == null || obj.getClass() != this.getClass()) 
            return false;
        var that = (Defesa) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.isFinal, that.isFinal) &&
                Objects.equals(this.isPresencial, that.isPresencial) &&
                Objects.equals(this.date, that.date) &&
                Objects.equals(this.duracao, that.duracao) &&
                Objects.equals(this.nota, that.nota) &&
                Objects.equals(this.sala, that.sala);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isFinal, isPresencial, 
                            date, duracao, nota, sala);
    }

    @Override
    public String toString() {
        return "Defesa[" +
                "id=" + id + ", " +
                "isFinal=" + isFinal + ", " +
                "isPresencial=" + isPresencial + ", " +
                "date=" + date + ", " +
                "duracao=" + duracao + ", " +
                "nota=" + nota + ", " +
                "sala=" + sala + ']';
    }


}
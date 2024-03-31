package pt.ul.fc.css.example.demo.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.Objects;

@Entity
public final class Defesa{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NonNull
    Boolean isFinal;

    @NonNull
    Boolean isPresencial;

    Date date;

    @NonNull
    Integer duracao;

    float nota;

    @ManyToOne
    @JoinColumn(name="tese_id")
    private Tese tese;

    String sala;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="juri_id")
    private Juri juri;

    public Defesa(@NonNull boolean isFinal, @NonNull boolean isPresencial) {
        this.isFinal = isFinal;
        this.isPresencial = isPresencial;
        this.date = null;
        this.duracao = (isFinal) ? 90: 60;
        this.nota = -1;
        this.sala = null;
        juri = new Juri();
    }

    public Defesa() {
        this.isFinal = false;
        this.isPresencial = false;
        this.date = null;
        this.duracao = 60;
        this.nota = -1;
        this.sala = null;
        juri = new Juri();
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

    public Juri getJuri() {
        return juri;
    }

    public void setTese(Tese tese) {
        this.tese = tese;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSala(String sala){
        this.sala = sala;
    }

    public void setJuri(Juri juri) {
        this.juri = juri;
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
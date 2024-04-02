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
        juri = null;
    }

    public Defesa() {
        this.isFinal = false;
        this.isPresencial = false;
        this.date = null;
        this.duracao = 60;
        this.nota = -1;
        this.sala = null;
        juri = null;
    }

    /**
     * Returns the ID of the defense.
     *
     * @return the ID of the defense
     */
    public int getId() {
        return id;
    }

    /**
     * Returns true if the defense is the final defense, false otherwise.
     *
     * @return true if the defense is the final defense, false otherwise
     */
    public boolean isFinal() {
        return isFinal;
    }

    /**
     * Returns true if the defense is presencial, false otherwise.
     *
     * @return true if the defense is presencial, false otherwise
     */
    public boolean isPresencial() {
        return isPresencial;
    }

    /**
     * Returns the date of the defense.
     *
     * @return the date of the defense
     */
    public Date getDate() {
        return date;
    }

    /**
     * Returns the duration of the defense.
     *
     * @return the duration of the defense
     */
    public int getDuracao() {
        return duracao;
    }

    /**
     * Returns the grade of the defense.
     *
     * @return the grade of the defense
     */
    public float getNota() {
        return nota;
    }

    /**
     * Returns the room of the defense.
     *
     * @return the room of the defense
     */
    public String getSala() {
        return sala;
    }

    /**
     * Returns the jury of the defense.
     *
     * @return the jury of the defense
     */
    public Juri getJuri() {
        return juri;
    }

    /**
     * Sets the Tese object associated with the defense.
     *
     * @param tese the Tese object to set
     */
    public void setTese(Tese tese) {
        this.tese = tese;
    }

    /**
     * Sets the date of the defense.
     *
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Sets the room of the defense.
     *
     * @param sala the room to set
     */
    public void setSala(String sala){
        this.sala = sala;
    }

    /**
     * Sets the Juri object associated with the defense.
     *
     * @param juri the Juri object to set
     */
    public void setJuri(Juri juri) {
        this.juri = juri;
    }

    /**
     * Sets the grade of the defense.
     *
     * @param nota the grade to set
     */
    public void setNota(float nota) {
        this.nota = nota;
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
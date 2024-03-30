package pt.ul.fc.css.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.springframework.lang.NonNull;

@Entity
public class Candidatura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NonNull
    private Date dataCandidatura;

    @NonNull
    private EstadoCandidatura estado;

    @NonNull
    @OneToOne
    private Tese tese;

    @NonNull
    @ManyToOne
    @JoinColumn(name="tema_id")
    private Tema tema;

    public Candidatura(@NonNull Date dataCandidatura, @NonNull EstadoCandidatura estado, Tese tese, Tema tema) {
        this.dataCandidatura = dataCandidatura;
        this.estado = estado;
        this.tese = tese;
        this.tema = tema;
    }

    protected Candidatura() {
        this.dataCandidatura = new Date();
        this.estado = EstadoCandidatura.EMPROCESSAMENTO;
        this.tese = null;
        this.tema = null;
    }

    public int getId() {
        return id;
    }

    public Date getDataCandidatura() {
        return dataCandidatura;
    }

    public EstadoCandidatura getEstado() {
        return estado;
    }

    public Tese getTese() {
        return tese;
    }

    public Tema getTema() {
        return tema;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) 
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        var that = (Candidatura) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.dataCandidatura, that.dataCandidatura) &&
                Objects.equals(this.estado, that.estado) &&
                Objects.equals(this.tese, that.tese) &&
                Objects.equals(this.tema, that.tema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataCandidatura);
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = dateFormat.format(dataCandidatura);
        return "Candidatura[" +
                "id=" + id + ", " +
                "data de candidatura =" + strDate + ", " +
                "Estado de candidatura =" + estado.name() + ", " +
                "Id da Tese associada=" + tese.getId() + ", " +
                "Tema associado =" + tema.getTitulo() + ", ";
    }

}
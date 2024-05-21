package pt.ul.fc.css.example.demo.entities;

import jakarta.persistence.*;

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
    @Enumerated(EnumType.STRING)
    private EstadoCandidatura estado;

    @OneToOne(mappedBy = "candidatura", cascade = CascadeType.REMOVE)
    private Tese tese;

    @NonNull
    @ManyToOne
    @JoinColumn(name="tema_id")
    private Tema tema;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;


    public Candidatura(@NonNull Date dataCandidatura, @NonNull EstadoCandidatura estado, @NonNull Aluno aluno, @NonNull Tema tema) {
        this.dataCandidatura = dataCandidatura;
        this.estado = estado;
        this.tese = null;
        this.tema = tema;
        this.aluno = aluno;
    }

    protected Candidatura() {
        this.dataCandidatura = new Date();
        this.estado = EstadoCandidatura.EMPROCESSAMENTO;
        this.tese = null;
        this.tema = null;
        this.aluno = new Aluno();
    }

    /**
     * Returns the id of the candidatura.
     * 
     * @return The id of the candidatura.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Returns the date of the candidatura.
     * 
     * @return The date of the candidatura.
     */
    public Date getDataCandidatura() {
        return dataCandidatura;
    }
    
    /**
     * Returns the estado of the candidatura.
     * 
     * @return The estado of the candidatura.
     */
    public EstadoCandidatura getEstado() {
        return estado;
    }

    /**
     * Returns the tese associated with the candidatura.
     * 
     * @return The tese associated with the candidatura.
     */
    public Tese getTese() {
        return tese;
    }

    /**
     * Returns the tema associated with the candidatura.
     * 
     * @return The tema associated with the candidatura.
     */
    public Tema getTema() {
        return tema;
    }

    /**
     * Returns the aluno associated with the candidatura.
     * 
     * @return The aluno associated with the candidatura.
     */
    public Aluno getAluno() {
        return aluno;
    }

    /**
     * Sets the tese associated with the candidatura.
     * 
     * @param tese The tese to be associated with the candidatura.
     */
    public void setTese(Tese tese) {
            this.tese = tese;
    }

    public void setEstado(EstadoCandidatura estado) {
        this.estado = estado;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
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
                Objects.equals(this.aluno, that.aluno) &&
                Objects.equals(this.tema, that.tema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataCandidatura, aluno, estado, tese, aluno, tema);
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = dateFormat.format(dataCandidatura);
        return "Candidatura[" +
                "id=" + id + ", " +
                "nome_aluno=" + aluno.getName() + ", " +
                "data de candidatura =" + strDate + ", " +
                "Estado de candidatura =" + estado.name() + ", " +
                "Id da Tese associada=" + tese.getId() + ", " +
                "Tema associado =" + tema.getTitulo() + ", ";
    }

}

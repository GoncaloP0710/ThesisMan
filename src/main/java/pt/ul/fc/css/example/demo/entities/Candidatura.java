package pt.ul.fc.css.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

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

    @OneToOne(mappedBy = "candidatura")
    private Tese tese;

    @ManyToOne
    @JoinColumn(name="tema_id")
    private Tema tema;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    public Candidatura(@NonNull Date dataCandidatura, @NonNull EstadoCandidatura estado, @NonNull Aluno aluno) {
        this.dataCandidatura = dataCandidatura;
        this.estado = estado;
        this.tese = null;
        this.tema = null;
        this.aluno = aluno;
    }

    protected Candidatura() {
        this.dataCandidatura = new Date();
        this.estado = EstadoCandidatura.EMPROCESSAMENTO;
        this.tese = null;
        this.tema = null;
        this.aluno = new Aluno();
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

    public void setTese(Tese tese) {
        if(estado == EstadoCandidatura.APROVADO){
            this.tese = tese;
        }else{
            System.out.println("Não é possível associar uma tese a uma candidatura que não foi aprovada.");
        }
    }

    public void setTema(Tema tema) {
        if(estado == EstadoCandidatura.APROVADO){
            System.out.println("Não é possível associar um tema a uma candidatura que já foi processada.");
        }else{
            this.tema = tema;
        }
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

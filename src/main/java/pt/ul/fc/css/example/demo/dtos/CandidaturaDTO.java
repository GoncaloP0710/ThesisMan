package pt.ul.fc.css.example.demo.dtos;

import java.util.Date;

public class CandidaturaDTO {
    private Integer id;
    private String tema;
    private Date dataCandidatura;
    private String estado;
    private Integer teseId;
    private Integer alunoId;

    public CandidaturaDTO(Integer id, String tema, Date dataCandidatura, String estado, Integer teseId, Integer alunoId) {
        this.id = id;
        this.tema = tema;
        this.dataCandidatura = dataCandidatura;
        this.estado = estado;
        this.teseId = teseId;
        this.alunoId = alunoId;
    }

    public CandidaturaDTO() {
    }

    public Integer getId() {
        return id;
    }

    public String getTema() {
        return tema;
    }

    public Date getDataCandidatura() {
        return dataCandidatura;
    }

    public String getEstado() {
        return estado;
    }

    public Integer getTeseId() {
        return teseId;
    }

    public Integer getAlunoId() {
        return alunoId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public void setDataCandidatura(Date dataCandidatura) {
        this.dataCandidatura = dataCandidatura;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setTeseId(Integer teseId) {
        this.teseId = teseId;
    }

    public void setAlunoId(Integer alunoId) {
        this.alunoId = alunoId;
    }

}

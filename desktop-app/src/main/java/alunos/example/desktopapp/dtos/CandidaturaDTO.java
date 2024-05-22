package alunos.example.desktopapp.dtos;

import java.util.Date;

public class CandidaturaDTO {
    private Integer id;
    private Integer temaId;
    private Date dataCandidatura;
    private String estado;
    private Integer teseId;
    private Integer alunoId;

    public CandidaturaDTO(
            Integer id,
            Integer temaId,
            Date dataCandidatura,
            String estado,
            Integer teseId,
            Integer alunoId) {
        this.id = id;
        this.temaId = temaId;
        this.dataCandidatura = dataCandidatura;
        this.estado = estado;
        this.teseId = teseId;
        this.alunoId = alunoId;
    }

    public CandidaturaDTO() {}

    public Integer getId() {
        return id;
    }

    public Integer getTema() {
        return temaId;
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

    public void setTema(Integer tema) {
        this.temaId = tema;
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

    public Object getTemaId() {
        return temaId;
    }
}

package pt.ul.fc.css.example.demo.dtos;

import java.sql.Date;

public class DefesaDTO {
    private Integer id;
    private Boolean isFinal;
    private Boolean isPresencial;
    private Date data;
    private Integer duracao;
    private Integer nota;
    private Integer teseId;
    private String sala;
    private Integer juriId;

    public DefesaDTO() {}

    public DefesaDTO(Integer id, Boolean isFinal, Boolean isPresencial, Date data, Integer duracao, Integer nota, Integer teseId, String sala, Integer juriId) {
        this.id = id;
        this.isFinal = isFinal;
        this.isPresencial = isPresencial;
        this.data = data;
        this.duracao = duracao;
        this.nota = nota;
        this.teseId = teseId;
        this.sala = sala;
        this.juriId = juriId;
    }

    public Integer getId() {
        return this.id;
    }

    public Boolean getIsFinal() {
        return this.isFinal;
    }

    public Boolean getIsPresencial() {
        return this.isPresencial;
    }

    public Date getData() {
        return this.data;
    }

    public Integer getDuracao() {
        return this.duracao;
    }

    public Integer getNota() {
        return this.nota;
    }

    public Integer getTeseId() {
        return this.teseId;
    }

    public String getSala() {
        return this.sala;
    }

    public Integer getJuriId() {
        return this.juriId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIsFinal(Boolean isFinal) {
        this.isFinal = isFinal;
    }

    public void setIsPresencial(Boolean isPresencial) {
        this.isPresencial = isPresencial;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public void setTeseId(Integer teseId) {
        this.teseId = teseId;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public void setJuriId(Integer juriId) {
        this.juriId = juriId;
    }
}

package pt.ul.fc.css.example.demo.dtos;

import java.util.List;

public class ProjetoDTO {
    private Integer id;
    private Integer docenteId;
    private Integer candidaturaId;
    private List<Integer> defesasId;

    public ProjetoDTO() {}

    public ProjetoDTO(Integer id, Integer docenteId, Integer candidaturaId, List<Integer> defesasId) {
        this.id = id;
        this.docenteId = docenteId;
        this.candidaturaId = candidaturaId;
        this.defesasId = defesasId;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getDocenteId() {
        return this.docenteId;
    }

    public Integer getCandidaturaId() {
        return this.candidaturaId;
    }

    public List<Integer> getDefesasId() {
        return this.defesasId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDocenteId(Integer docenteId) {
        this.docenteId = docenteId;
    }

    public void setCandidaturaId(Integer candidaturaId) {
        this.candidaturaId = candidaturaId;
    }

    public void setDefesasId(List<Integer> defesasId) {
        this.defesasId = defesasId;
    }


    
}

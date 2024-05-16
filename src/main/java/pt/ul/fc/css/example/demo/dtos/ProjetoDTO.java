package pt.ul.fc.css.example.demo.dtos;

import java.util.List;

public class ProjetoDTO extends TeseDTO{
    private Integer docenteId;

    public ProjetoDTO() {}

    public ProjetoDTO(Integer id, Integer candidaturaId, byte[]documentProposto, byte[]documentoFinal, List<Integer> defesasId, Integer docenteId) {
        super(id, candidaturaId, documentProposto, documentoFinal, defesasId);
        this.docenteId = docenteId;
    }

    public Integer getId() {
        return super.getId();
    }

    public Integer getDocenteId() {
        return this.docenteId;
    }

    public List<Integer> getDefesasId() {
        return super.getDefesasId();
    }

    public void setDocenteId(Integer docenteId) {
        this.docenteId = docenteId;
    }

    public void setCandidaturaId(Integer candidaturaId) {
        super.setCandidaturaId(candidaturaId);
    }

    public void setDefesasId(List<Integer> defesasId) {
        super.setDefesasId(defesasId);
    }


    
}

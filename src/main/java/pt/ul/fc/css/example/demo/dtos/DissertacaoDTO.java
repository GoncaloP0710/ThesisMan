package pt.ul.fc.css.example.demo.dtos;
import java.util.List;

public class DissertacaoDTO extends TeseDTO{
    private Integer id;
    private Integer candidaturaId;
    private List<Integer> defesasId;

    public DissertacaoDTO(Integer id, Integer candidaturaId, List<Integer> defesasId) {
        super();
        this.id = id;
        this.candidaturaId = candidaturaId;
        this.defesasId = defesasId;
    }

    public DissertacaoDTO() {}

    public Integer getId() {
        return id;
    }

    public Integer getCandidaturaId() {
        return candidaturaId;
    }

    public List<Integer> getDefesasId() {
        return defesasId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCandidaturaId(Integer candidaturaId) {
        this.candidaturaId = candidaturaId;
    }

    public void setDefesasId(List<Integer> defesasId) {
        this.defesasId = defesasId;
    }

}

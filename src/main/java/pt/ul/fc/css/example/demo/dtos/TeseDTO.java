package pt.ul.fc.css.example.demo.dtos;

import java.util.List;

public class TeseDTO {
    private Integer id;
    private Integer candidaturaId;
    private byte[] documentProposto;
    private byte[] documentFinal;
    private List<Integer> defesasId;

    public TeseDTO() {}

    public TeseDTO(Integer id, Integer candidaturaId, byte[] documentProposto, byte[] documentFinal, List<Integer> defesasId) {
        this.id = id;
        this.candidaturaId = candidaturaId;
        this.documentProposto = documentProposto;
        this.documentFinal = documentFinal;
        this.defesasId = defesasId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCandidaturaId() {
        return candidaturaId;
    }

    public byte[] getDocumentProposto() {
        return documentProposto;
    }

    public byte[] getDocumentFinal() {
        return documentFinal;
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

    public void setDocumentProposto(byte[] documentProposto) {
        this.documentProposto = documentProposto;
    }

    public void setDocumentFinal(byte[] documentFinal) {
        this.documentFinal = documentFinal;
    }

    public void setDefesasId(List<Integer> defesasId) {
        this.defesasId = defesasId;
    }


}

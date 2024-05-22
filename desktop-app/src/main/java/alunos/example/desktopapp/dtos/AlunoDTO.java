package alunos.example.desktopapp.dtos;

import java.util.List;

public class AlunoDTO{
    private Integer id;
    private String name;
    private String contact;
    private Double average;
    private Integer mestrado;
    private List<Integer> candidaturaId;

    public AlunoDTO(Integer id, String name, String contact, Double average, Integer mestradoId, List<Integer> candidaturaId) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.average = average;
        this.mestrado = mestradoId;
        this.candidaturaId = candidaturaId;
    }

    public AlunoDTO(){}

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public Double getAverage() {
        return average;
    }

    public Integer getMestrado() {
        return mestrado;
    }

    public List<Integer> getCandidaturaId() {
        return candidaturaId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}

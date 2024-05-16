package pt.ul.fc.css.example.demo.dtos;

import java.util.List;

public class DocenteDTO {
    private Integer id;
    private String nome;
    private String contacto;
    private String departamento;
    private Boolean isAdministrador;
    private List<Integer> temasPropostos;
    private List<Integer> projetosId;

    public DocenteDTO() {}

    public DocenteDTO(Integer id, String nome, String contacto, String departamento, Boolean isAdministrador, List<Integer> temasPropostos, List<Integer> projetosId) {
        this.id = id;
        this.nome = nome;
        this.contacto = contacto;
        this.departamento = departamento;
        this.isAdministrador = isAdministrador;
        this.temasPropostos = temasPropostos;
        this.projetosId = projetosId;
    }

    public Integer getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getContacto() {
        return this.contacto;
    }

    public String getDepartamento() {
        return this.departamento;
    }

    public Boolean getIsAdministrador() {
        return this.isAdministrador;
    }

    public List<Integer> getTemasPropostos() {
        return this.temasPropostos;
    }

    public List<Integer> getProjetosId() {
        return this.projetosId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setIsAdministrador(Boolean isAdministrador) {
        this.isAdministrador = isAdministrador;
    }

    public void setTemasPropostos(List<Integer> temasPropostos) {
        this.temasPropostos = temasPropostos;
    }

    public void setProjetosId(List<Integer> projetosId) {
        this.projetosId = projetosId;
    }


    
}

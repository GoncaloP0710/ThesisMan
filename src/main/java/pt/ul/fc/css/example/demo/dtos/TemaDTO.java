package pt.ul.fc.css.example.demo.dtos;

import java.util.List;

public class TemaDTO{
    private Integer id;
    private String titulo;
    private String descricao;
    private float remuneracaoMensal;
    private Integer submissorId;
    private List<Integer> mestradosCompativeisId;
    

    public TemaDTO(Integer id, String titulo, String descricao, float remuneracaoMensal, Integer submissorId, List<Integer> mestradosCompativeisId) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.remuneracaoMensal = remuneracaoMensal;
        this.submissorId = submissorId;
        this.mestradosCompativeisId = mestradosCompativeisId;
    }

    public TemaDTO() {}

    public Integer getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public float getRemuneracaoMensal() {
        return remuneracaoMensal;
    }

    public Integer getSubmissorId() {
        return submissorId;
    }

    public List<Integer> getMestradosCompativeisId() {
        return mestradosCompativeisId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setRemuneracaoMensal(float remuneracaoMensal) {
        this.remuneracaoMensal = remuneracaoMensal;
    }

    public void setSubmissorId(Integer submissorId) {
        this.submissorId = submissorId;
    }

    public void setMestradosCompativeisId(List<Integer> mestradosCompativeisId) {
        this.mestradosCompativeisId = mestradosCompativeisId;
    }

    

}
package pt.ul.fc.css.example.demo.dtos;

public class MestradoDTO {
    private Integer id;
    private String nome;
    private Integer coordenadorId;

    public MestradoDTO(Integer id, String nome, Integer coordenadorId) {
        this.id = id;
        this.nome = nome;
        this.coordenadorId = coordenadorId;
    }

    public MestradoDTO() {
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getCoordenadorId() {
        return coordenadorId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCoordenadorId(Integer coordenadorId) {
        this.coordenadorId = coordenadorId;
    }





}

package pt.ul.fc.css.example.demo.dtos;

public class AlunoDTO{
    private Integer id;
    private String name;
    private String contact;
    private Double average;
    private Integer mestradoid;
    

    public AlunoDTO(Integer id, String name, String contact, Double average, Integer mestradoid) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.average = average;
        this.mestradoid = mestradoid;
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

    public Integer getMestradoId() {
        return mestradoid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
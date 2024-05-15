package pt.ul.fc.css.example.demo.dtos;

public class AlunoDTO{

    private String name;
    private String contact;
    private Double average;
    private MestradoDTO mestrado;
    

    public AlunoDTO(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    public AlunoDTO(){}

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
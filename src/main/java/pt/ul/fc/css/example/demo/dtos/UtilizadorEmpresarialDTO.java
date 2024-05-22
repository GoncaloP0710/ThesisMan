package pt.ul.fc.css.example.demo.dtos;
import java.util.List;

public class UtilizadorEmpresarialDTO {
    private Integer id;
    private String empresa;
    private String name;
    private String contact;
    private List<Integer> temasPropostosId;


    public UtilizadorEmpresarialDTO(Integer id,String name, String empresa, String contact, List<Integer> temasPropostosId) {
        this.id=id;
        this.name =name;
        this.empresa = empresa;
        this.contact = contact;
        this.temasPropostosId = temasPropostosId;
    }

    public UtilizadorEmpresarialDTO() {}

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getContact() {
        return contact;
    }

    public List<Integer> getTemasPropostosId() {
        return temasPropostosId;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setTemasPropostosId(List<Integer> temasPropostosId) {
        this.temasPropostosId = temasPropostosId;
    }

    

}

package pt.ul.fc.css.example.demo.dtos;
import java.util.List;

public class UtilizadorEmpresarialDTO {
    private String empresa;
    private String contact;
    private List<Integer> temasPropostosId;


    public UtilizadorEmpresarialDTO(String empresa, String contact, List<Integer> temasPropostosId) {
        this.empresa = empresa;
        this.contact = contact;
        this.temasPropostosId = temasPropostosId;
    }

    public UtilizadorEmpresarialDTO() {}

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

    public void setTemasPropostosId(List<Integer> temasPropostosId) {
        this.temasPropostosId = temasPropostosId;
    }

    

}

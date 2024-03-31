package pt.ul.fc.css.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Utilizador {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer userId;

    private String name;

    private String contact;

    public Utilizador(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    protected Utilizador() {
        this.name = "";
        this.contact = "";
    }

    public Integer getId() {
        return userId;
    }
    

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }
}

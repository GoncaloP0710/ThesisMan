package pt.ul.fc.css.example.demo.entities;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Utilizador {

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

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }
}

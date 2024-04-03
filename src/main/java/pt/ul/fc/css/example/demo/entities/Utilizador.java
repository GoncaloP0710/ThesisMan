package pt.ul.fc.css.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

/**
 * Represents a generic Utilizador entity.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Utilizador {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer userId;

    private String nome;

    private String contacto;

    
    public Utilizador(String name, String contact) {
        this.name = name;
        this.contacto = contact;
    }

    protected Utilizador() {
        this.name = "";
        this.contacto = "";
    }

    /**
     * Returns the ID of the Utilizador.
     *
     * @return the ID of the Utilizador
     */
    public Integer getId() {
        return userId;
    }

    /**
     * Returns the name of the Utilizador.
     *
     * @return the name of the Utilizador
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the contact information of the Utilizador.
     *
     * @return the contact information of the Utilizador
     */
    public String getContacto() {
        return contacto;
    }
}

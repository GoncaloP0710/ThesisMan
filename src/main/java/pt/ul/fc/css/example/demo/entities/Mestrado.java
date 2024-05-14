package pt.ul.fc.css.example.demo.entities;

import java.util.Objects;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

/**
 * Represents a Mestrado (Master's degree) entity.
 */
@Entity
public class Mestrado {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer mestradoId;
    
    @NonNull
    private String nome;

    @OneToOne()
    @JoinColumn(name="coordenador_id")
    private Docente coordenador;

    
    public Mestrado(String nome, Docente coordenador) {
        this.nome = nome;
        this.coordenador = coordenador;
    }

    
    public Mestrado(String nome){
        this.nome = nome;
        this.coordenador = null;
    }

    
    protected Mestrado() {
        this.nome = "";
        this.coordenador = null;
    }
    
    public Integer getId() {
        return mestradoId;
    }
    /**
     * Returns the nome of the Mestrado.
     * 
     * @return The nome of the Mestrado.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Returns the coordenador (coordinator) of the Mestrado.
     * 
     * @return The coordenador of the Mestrado.
     */
    public Docente getCoordenador() {
        return coordenador;
    }

    /**
     * Sets the coordenador (coordinator) of the Mestrado.
     * If the given coordenador is not an administrator, it sets the coordenador to null.
     * 
     * @param coordenador The coordenador to be set.
     */
    public void setCoordenador(Docente coordenador){
        this.coordenador = coordenador;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this)
            return true;
        if(obj == null || obj.getClass() != this.getClass())
            return false;
        var that = (Mestrado) obj;
        return Objects.equals(this.nome, that.getNome()) &&
               Objects.equals(this.coordenador, that.getCoordenador());
    }

    @Override
    public int hashCode(){
        return Objects.hash(nome, coordenador);
    }

    @Override
    public String toString(){
        return "Mestrado[" +
                "nome=" + nome + ", " +
                "coordenador=" + ((this.coordenador != null) ? coordenador.getName() : " ") +
                 ']';
    }


}

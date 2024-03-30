package pt.ul.fc.css.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pt.ul.fc.css.example.demo.entities.Docente;
import pt.ul.fc.css.example.demo.entities.Tema;

public interface DocenteRepository extends JpaRepository<Docente,Integer> {
    


    @Query("SELECT d FROM Docente d WHERE d.departamento = q")
    List<Docente> findByDepartamento(@Param("q") String q);

    @Query("SELECT d FROM Docente d WHERE d.temas_propostos = q ")
    List<Docente> findByListaDeTemas(@Param("q") List<Tema> q);
}

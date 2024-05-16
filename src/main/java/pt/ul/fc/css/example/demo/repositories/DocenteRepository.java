package pt.ul.fc.css.example.demo.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.example.demo.entities.Docente;

public interface DocenteRepository extends JpaRepository<Docente,Integer> {
    Optional<Docente> findById(Integer id);
    Optional<Docente> findByEmail(String email);
    Optional<Docente> findByNome(String nome);
    List<Docente> findAllByDepartamento(String departamento);
    List<Docente> findAll();
}

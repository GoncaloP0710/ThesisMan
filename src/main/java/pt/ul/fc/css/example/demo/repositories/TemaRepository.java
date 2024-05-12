package pt.ul.fc.css.example.demo.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.ul.fc.css.example.demo.entities.Mestrado;
import pt.ul.fc.css.example.demo.entities.Tema;

public interface TemaRepository extends JpaRepository<Tema, Integer>{
    //queries
    Optional<Tema> findByTitulo(String titulo);
    List<Tema> findAllByMestrado(Mestrado mestrado);
}

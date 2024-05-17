package pt.ul.fc.css.example.demo.repositories;

import pt.ul.fc.css.example.demo.entities.Mestrado;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MestradoRepository extends JpaRepository<Mestrado, Integer>{
    Optional<Mestrado> findById(Integer id);
    Optional<Mestrado> findByNome(String nome);
    List<Mestrado> findByNomeIn(List<String> mestradosCompativeis); 
}

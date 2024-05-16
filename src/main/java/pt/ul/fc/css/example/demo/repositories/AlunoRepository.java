package pt.ul.fc.css.example.demo.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.example.demo.entities.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    Optional<Aluno> findById(Integer id);
    Optional<Aluno> findByEmail(String email);
    Optional<Aluno> findByNome(String nome);
    
}
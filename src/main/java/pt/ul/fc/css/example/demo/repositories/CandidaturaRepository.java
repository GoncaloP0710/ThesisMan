package pt.ul.fc.css.example.demo.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pt.ul.fc.css.example.demo.entities.Candidatura;

public interface CandidaturaRepository extends JpaRepository<Candidatura, Integer>{

    Optional<Candidatura> findById(Integer id);
    Optional<Candidatura> findByTema(String tema);

    @Query("SELECT c FROM Candidatura c WHERE c.aluno.id = ?1")
    List<Candidatura> findAllByAlunoId(Integer alunoId);
    List<Candidatura> findAllByAlunoEmail(String email);

}

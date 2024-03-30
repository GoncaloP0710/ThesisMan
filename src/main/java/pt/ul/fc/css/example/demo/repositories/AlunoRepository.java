package pt.ul.fc.css.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pt.ul.fc.css.example.demo.entities.Aluno;
import pt.ul.fc.css.example.demo.entities.Candidatura;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    @Query("SELECT a FROM Aluno a WHERE average > q")
    List<Aluno> findByAverageHigher(@Param("q") Double q);

    @Query("SELECT a FROM Aluno a WHERE average < q")
    List<Aluno> findByAverageLower(@Param("q") Double q);

    @Query("SELECT a FROM Aluno a WHERE average > 9.5")
    List<Aluno> findByPositiveAverage();

    @Query("SELECT a FROM Aluno a WHERE a.candidatura = q ")
    List<Aluno> findByCandidatura(@Param("q") Candidatura q);
}
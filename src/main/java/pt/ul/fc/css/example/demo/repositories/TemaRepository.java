package pt.ul.fc.css.example.demo.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.example.demo.entities.Docente;
import pt.ul.fc.css.example.demo.entities.Mestrado;
import pt.ul.fc.css.example.demo.entities.Tema;
import pt.ul.fc.css.example.demo.entities.UtilizadorEmpresarial;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Integer>{
    @Query("SELECT t FROM Tema t WHERE t.titulo = :titulo")
    Optional<Tema> findByTitulo(String titulo);

    @Query("SELECT t FROM Tema t WHERE :mestrado MEMBER OF t.mestrados")
    List<Tema> findByMestrado(Mestrado mestrado);
}

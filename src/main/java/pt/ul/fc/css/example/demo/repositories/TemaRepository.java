package pt.ul.fc.css.example.demo.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pt.ul.fc.css.example.demo.entities.Docente;
import pt.ul.fc.css.example.demo.entities.Mestrado;
import pt.ul.fc.css.example.demo.entities.Tema;
import pt.ul.fc.css.example.demo.entities.UtilizadorEmpresarial;

public interface TemaRepository extends JpaRepository<Tema, Integer>{
    Optional<Tema> findById(Integer id);
    Optional<Tema> findByTitulo(String titulo);

    @Query("SELECT t FROM Tema t JOIN t.mestrados m WHERE m.id = :mestradoId")
    List<Tema> findByMestrado(@Param("mestradoId") Integer mestradoId);
    List<Tema> findAll();
    Optional<Tema> findByTituloAndDescricaoAndRemuneracaoMensal(String titulo, String descricao, float remuneracaoMensal);
}

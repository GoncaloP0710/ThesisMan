package pt.ul.fc.css.example.demo.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.ul.fc.css.example.demo.entities.Docente;
import pt.ul.fc.css.example.demo.entities.Mestrado;
import pt.ul.fc.css.example.demo.entities.Tema;
import pt.ul.fc.css.example.demo.entities.UtilizadorEmpresarial;

public interface TemaRepository extends JpaRepository<Tema, Integer>{

    //queries
    Optional<Tema> findByTitulo(String titulo);

    List<Tema> findAllByMestrado(Mestrado mestrado);

    Optional<List<Tema>> findAll(String titulo, String descricao, float remuneracaoMensal, Docente submissor,
            List<Mestrado> mestradosCompativeis);

    Optional<List<Tema>> findAll(String titulo, String descricao, float remuneracaoMensal, Docente submissor);

    Optional<List<Tema>> findAll(String titulo, String descricao, float remuneracaoMensal, UtilizadorEmpresarial submissor,
            List<Mestrado> mestradosCompativeis);

    Optional<List<Tema>> findAll(String titulo, String descricao, float remuneracaoMensal, UtilizadorEmpresarial submissor);
}

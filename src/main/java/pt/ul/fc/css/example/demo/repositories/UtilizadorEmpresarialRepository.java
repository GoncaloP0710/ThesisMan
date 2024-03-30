package pt.ul.fc.css.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pt.ul.fc.css.example.demo.entities.Tema;
import pt.ul.fc.css.example.demo.entities.UtilizadorEmpresarial;

public interface UtilizadorEmpresarialRepository extends JpaRepository<UtilizadorEmpresarial, Integer> {

    @Query("SELECT ue FROM Utilizador_Empresarial ue WHERE ue.empresa = q")
    List<UtilizadorEmpresarial> findByEmpresa(@Param("q") String q);

    @Query("SELECT ue FROM Utilizador_Empresarial ue WHERE ue.temas_propostos = q ")
    List<UtilizadorEmpresarial> findByListaDeTemas(@Param("q") List<Tema> q);
    
}

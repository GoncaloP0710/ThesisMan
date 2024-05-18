package pt.ul.fc.css.example.demo.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ul.fc.css.example.demo.entities.UtilizadorEmpresarial;

@Repository
public interface UtilizadorEmpresarialRepository extends JpaRepository<UtilizadorEmpresarial, Integer> {

    List<UtilizadorEmpresarial> findAll();
    Optional<UtilizadorEmpresarial> findById(Integer id);
    List<UtilizadorEmpresarial> findAllByEmpresa(String empresa);
    Optional<UtilizadorEmpresarial> findByEmail(String email);
    Optional<UtilizadorEmpresarial> findByEmpresa(String empresa);
    Optional<UtilizadorEmpresarial> findByTemasPropostos(List<Integer> temasPropostosId);

}
    
    
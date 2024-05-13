package pt.ul.fc.css.example.demo.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.example.demo.entities.UtilizadorEmpresarial;

public interface UtilizadorEmpresarialRepository extends JpaRepository<UtilizadorEmpresarial, Integer> {

    Optional<UtilizadorEmpresarial> findByContact(String contact);
    Optional<UtilizadorEmpresarial> findByEmpresa(String empresa);
    UtilizadorEmpresarial findByTemasPropostos(List<Integer> temasPropostosId);

}
    
    
package pt.ul.fc.css.example.demo.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.example.demo.entities.UtilizadorEmpresarial;

public interface UtilizadorEmpresarialRepository extends JpaRepository<UtilizadorEmpresarial, Integer> {

    UtilizadorEmpresarial findByContact(String contact);
    UtilizadorEmpresarial findByEmpresa(String empresa);
    UtilizadorEmpresarial findByTemasPropostos(List<Integer> temasPropostosId);

}
    
    
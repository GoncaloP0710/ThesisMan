package pt.ul.fc.css.example.demo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pt.ul.fc.css.example.demo.entities.Tema;
import pt.ul.fc.css.example.demo.entities.UtilizadorEmpresarial;
import pt.ul.fc.css.example.demo.repositories.UtilizadorEmpresarialRepository;

@RestController
@RequestMapping("/api")
public class UtilizadorEmpresarialController {
    
    private final UtilizadorEmpresarialRepository uERepository;

    public UtilizadorEmpresarialController(UtilizadorEmpresarialRepository uERepository) {
        this.uERepository = uERepository;
    }

    @GetMapping("/utilizador_empresarial/{id}")
    public UtilizadorEmpresarial getUE(@PathVariable Integer id) {
        return this.uERepository.getReferenceById(id);
    }

    @GetMapping("/utilizador_empresarial")
    public List<UtilizadorEmpresarial> getDocentesByDepartamento(@RequestParam String empresa) {
        return this.uERepository.findByEmpresa(empresa);
    }

    @GetMapping("/utilizador_empresarial")
    public List<UtilizadorEmpresarial> getDocentesByListaDeTemas(@RequestParam List<Tema> temas) {
        return this.uERepository.findByListaDeTemas(temas);
    }
}

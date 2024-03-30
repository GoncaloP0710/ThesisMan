package pt.ul.fc.css.example.demo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pt.ul.fc.css.example.demo.entities.Docente;
import pt.ul.fc.css.example.demo.entities.Tema;
import pt.ul.fc.css.example.demo.repositories.DocenteRepository;

@RestController
@RequestMapping("/api")
public class DocenteController {

    private final DocenteRepository docenteRepository;

    public DocenteController(DocenteRepository docenteRepository) {
        this.docenteRepository = docenteRepository;
    } 

    @GetMapping("/docente/{id}")
    public Docente getDocente(@PathVariable Integer id) {
        return this.docenteRepository.getReferenceById(id);
    }

    @GetMapping("/docente")
    public List<Docente> getDocentesByDepartamento(@RequestParam String departamento) {
        return this.docenteRepository.findByDepartamento(departamento);
    }

    @GetMapping("/docente")
    public List<Docente> getDocentesByListaDeTemas(@RequestParam List<Tema> temas) {
        return this.docenteRepository.findByListaDeTemas(temas);
    }
    
}

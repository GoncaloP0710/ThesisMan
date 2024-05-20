package pt.ul.fc.css.example.demo.handlers;

import java.util.List;

import org.springframework.stereotype.Component;

import pt.ul.fc.css.example.demo.entities.Docente;
import pt.ul.fc.css.example.demo.repositories.DocenteRepository;

@Component
public class DocenteHandler {
    private DocenteRepository docenteRepository;

    public DocenteHandler(DocenteRepository docenteRepository) {
        this.docenteRepository = docenteRepository;
    }

    public DocenteRepository getDocenteRepository() {
        return docenteRepository;
    }

    public List<Docente> getDocentes(){
        return docenteRepository.findAll();
    }
}

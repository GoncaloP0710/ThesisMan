package pt.ul.fc.css.example.demo;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import pt.ul.fc.css.example.demo.entities.Candidatura;
import pt.ul.fc.css.example.demo.repositories.CandidaturaRepository;


@RestController
@RequestMapping("/api")
public class CandidaturaController {
    
    private final CandidaturaRepository candidaturaRepository;


    public CandidaturaController(CandidaturaRepository candidaturaRepository){
        this.candidaturaRepository = candidaturaRepository;
    }

    @GetMapping("/candidatura/{id}")
    public Candidatura getCandidatura(@PathVariable Integer id) {
        return this.candidaturaRepository.getReferenceById(id);
    }

    @PutMapping("/candidatura")
    public Integer putCandidatura(@NonNull @RequestBody Candidatura candidatura) {
        Candidatura saved = candidaturaRepository.save(candidatura);
        return saved.getId();
    }

    @RequestMapping(value = "/index")
    public ModelAndView home() {
        return new ModelAndView("index");
    }
}

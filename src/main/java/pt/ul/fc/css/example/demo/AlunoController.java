package pt.ul.fc.css.example.demo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pt.ul.fc.css.example.demo.entities.Aluno;
import pt.ul.fc.css.example.demo.entities.Candidatura;
import pt.ul.fc.css.example.demo.repositories.AlunoRepository;

@RestController
@RequestMapping("/api")
public class AlunoController {
    
    private final AlunoRepository alunoRepository;

    public AlunoController(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @GetMapping("/aluno/{id}")
    public Aluno getAluno(@PathVariable Integer id) {
        return this.alunoRepository.getReferenceById(id);
    }

    @GetMapping("/aluno")
    public List<Aluno> getHigherAverageThan(@RequestParam Double average) {
        return this.alunoRepository.findByAverageHigher(average);
    }

    @GetMapping("/aluno")
    public List<Aluno> getHigherLowerThan(@RequestParam Double average) {
        return this.alunoRepository.findByAverageLower(average);
    }

    @GetMapping("/aluno")
    public List<Aluno> getPositiveAverages(@RequestParam Double average) {
        return this.alunoRepository.findByPositiveAverage();
    }

    @GetMapping("/aluno")
    public List<Aluno> getAlunoByCandidatura(@RequestParam Candidatura candidatura) {
        return this.alunoRepository.findByCandidatura(candidatura);
    }


}

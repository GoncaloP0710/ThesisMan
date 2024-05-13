package pt.ul.fc.css.example.demo.handlers;

import java.util.List;

import pt.ul.fc.css.example.demo.entities.Tema;
import pt.ul.fc.css.example.demo.repositories.AlunoRepository;
import pt.ul.fc.css.example.demo.repositories.TemaRepository;

public class ListarTemasAlunosHandler {
    private TemaRepository temaRepository;
    private AlunoRepository alunoRepository;

    public ListarTemasAlunosHandler(TemaRepository temaRepository, AlunoRepository alunoRepository) {
        this.temaRepository = temaRepository;
        this.alunoRepository = alunoRepository;
    }

    public List<Tema> listarTemasAluno(String email) {
        if (alunoRepository.findByEmail(email).isEmpty()) {
            throw new IllegalArgumentException("Aluno não encontrado");
        }
        return temaRepository.findAll();
    }
}

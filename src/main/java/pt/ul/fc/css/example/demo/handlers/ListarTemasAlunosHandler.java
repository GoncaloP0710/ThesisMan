package pt.ul.fc.css.example.demo.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import pt.ul.fc.css.example.demo.entities.Tema;
import pt.ul.fc.css.example.demo.dtos.TemaDTO;
import pt.ul.fc.css.example.demo.entities.Aluno;
import pt.ul.fc.css.example.demo.entities.Mestrado;
import pt.ul.fc.css.example.demo.repositories.AlunoRepository;
import pt.ul.fc.css.example.demo.repositories.TemaRepository;
import pt.ul.fc.css.example.demo.exceptions.NotPresentException;

@Component
public class ListarTemasAlunosHandler {
    private TemaRepository temaRepository;
    private AlunoRepository alunoRepository;

    public ListarTemasAlunosHandler(TemaRepository temaRepository, AlunoRepository alunoRepository) {
        this.temaRepository = temaRepository;
        this.alunoRepository = alunoRepository;
    }

    public List<TemaDTO> listarTemasAluno(Integer alunoId) throws NotPresentException {
        if (alunoId == null) {
            throw new IllegalArgumentException("Id do aluno é obrigatório");
        }
        Optional<Aluno> optAluno = alunoRepository.findById(alunoId);
        if (optAluno.isEmpty()) {
            throw new NotPresentException("Aluno não encontrado");
        }

        Aluno aluno = optAluno.get();
        List<TemaDTO> result = new ArrayList<>();
        List<Tema> temas = temaRepository.findByMestrado(aluno.getMestrado().getId());

        List<Integer> mestradosIds = new ArrayList<>();
        for (Tema t : temas) {
            mestradosIds = getMestradosIds(t.getMestrados());
            result.add(new TemaDTO(t.getId(), t.getTitulo(), t.getDescricao(), 
                        t.getRemuneracaoMensal(), t.getSubmissor().getId(), mestradosIds));
        }
        return result;
    }

    private List<Integer> getMestradosIds(List<Mestrado> mestrados) {
        List<Integer> result = new ArrayList<>();
        for (Mestrado m : mestrados) {
            result.add(m.getId());
        }
        return result;
    }
}

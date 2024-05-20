package pt.ul.fc.css.example.demo.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import pt.ul.fc.css.example.demo.entities.Tema;
import pt.ul.fc.css.example.demo.entities.Tese;
import pt.ul.fc.css.example.demo.dtos.CandidaturaDTO;
import pt.ul.fc.css.example.demo.dtos.TeseDTO;
import pt.ul.fc.css.example.demo.entities.Aluno;
import pt.ul.fc.css.example.demo.entities.Candidatura;
import pt.ul.fc.css.example.demo.entities.Defesa;
import pt.ul.fc.css.example.demo.entities.Mestrado;
import pt.ul.fc.css.example.demo.repositories.AlunoRepository;
import pt.ul.fc.css.example.demo.repositories.TemaRepository;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;
import pt.ul.fc.css.example.demo.repositories.CandidaturaRepository;
import pt.ul.fc.css.example.demo.exceptions.NotPresentException;

@Component
public class SubmissaoDocFinalTeseAlunoHandler {

    private TeseRepository teseRepository;
    private AlunoRepository alunoRepository;
    private CandidaturaRepository candidaturaRepository;

    public SubmissaoDocFinalTeseAlunoHandler(TeseRepository teseRepository, AlunoRepository alunoRepository, CandidaturaRepository candidaturaRepository) {
        this.teseRepository = teseRepository;
        this.alunoRepository = alunoRepository;
        this.candidaturaRepository = candidaturaRepository;
    }

    public TeseDTO submeterDocFinalTeseAluno(Integer alunoId, Integer candidaturaID, byte[] document) throws NotPresentException {
        Optional<Candidatura> optCandidatura = candidaturaRepository.findById(candidaturaID);
        if(optCandidatura.isEmpty()){
            throw new NotPresentException("Candidatura não encontrada");
        }
        Optional<Aluno> optAluno = alunoRepository.findById(alunoId);
        if(optAluno.isEmpty()){
            throw new NotPresentException("Aluno não encontrado");
        }
        Candidatura candidatura = optCandidatura.get();
        Aluno aluno = optAluno.get();
        
        Tese tese = candidatura.getTese();
        tese.setDocumentFinal(document);
        teseRepository.save(tese);
        // TODO: Verificar se é necessário guardar a candidatura e o aluno
        candidaturaRepository.save(candidatura);
        alunoRepository.save(aluno);
        // ------------------------------

        TeseDTO teseDTO = new TeseDTO(tese.getId(), tese.getCandidatura().getId(), tese.getDocumentProposto(), tese.getDocumentFinal(), getDefesasIds(tese.getDefesas()));
        return teseDTO;
    }

    private List<Integer> getDefesasIds(List<Defesa> defesas) {
        List<Integer> result = new ArrayList<>();
        for (Defesa d : defesas) {
            result.add(d.getId());
        }
        return result;
    }
}
package pt.ul.fc.css.example.demo.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.example.demo.dtos.TeseDTO;
import pt.ul.fc.css.example.demo.entities.Aluno;
import pt.ul.fc.css.example.demo.entities.Candidatura;
import pt.ul.fc.css.example.demo.entities.Defesa;
import pt.ul.fc.css.example.demo.entities.Mestrado;
import pt.ul.fc.css.example.demo.entities.Tese;
import pt.ul.fc.css.example.demo.repositories.AlunoRepository;
import pt.ul.fc.css.example.demo.repositories.CandidaturaRepository;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;
import pt.ul.fc.css.example.demo.exceptions.NotPresentException;

@Component
public class SubmissaoDocPropostaTeseAlunoHandler {
        
    private CandidaturaRepository candidaturaRepository;
    private AlunoRepository alunoRepository;
    private TeseRepository teseRepository;
    

    public SubmissaoDocPropostaTeseAlunoHandler(TeseRepository teseRepository, CandidaturaRepository candidaturaRepository
                                                , AlunoRepository alunoRepository) {
        this.candidaturaRepository = candidaturaRepository;
        this.teseRepository = teseRepository;
        this.alunoRepository = alunoRepository;
    }

    @Transactional
    public TeseDTO SubmitPropostaTeseDocsAluno(Integer candidaturaID, byte[] document, Integer alunoId) throws NotPresentException {
        Optional<Candidatura> optCandidatura = candidaturaRepository.findById(candidaturaID);
        if(optCandidatura.isEmpty()){
            throw new NotPresentException("Candidatura não encontrada");
        }
        Optional<Aluno> optAluno = alunoRepository.findById(alunoId);
        if(optAluno.isEmpty()){
            throw new NotPresentException("Aluno não encontrado");
        }
        Aluno aluno = optAluno.get();
        Candidatura candidatura = optCandidatura.get();

        Tese tese = null;
        try {
            tese = candidatura.getTese();
            if (tese == null) {
                throw new NotPresentException("Tese não encontrado");
            }
        } catch (Exception e) {
            throw new NotPresentException("Tese não encontrado");
        }
        tese.setDocumentProposto(document);
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

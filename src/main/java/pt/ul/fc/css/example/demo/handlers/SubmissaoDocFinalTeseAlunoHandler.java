package pt.ul.fc.css.example.demo.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pt.ul.fc.css.example.demo.entities.Tema;
import pt.ul.fc.css.example.demo.dtos.TemaDTO;
import pt.ul.fc.css.example.demo.entities.Aluno;
import pt.ul.fc.css.example.demo.entities.Candidatura;
import pt.ul.fc.css.example.demo.entities.Mestrado;
import pt.ul.fc.css.example.demo.repositories.AlunoRepository;
import pt.ul.fc.css.example.demo.repositories.TemaRepository;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;
import pt.ul.fc.css.example.demo.repositories.CandidaturaRepository;
import pt.ul.fc.css.example.exceptions.NotPresentException;

public class SubmissaoDocFinalTeseAlunoHandler {

    private TeseRepository teseRepository;
    private AlunoRepository alunoRepository;
    private CandidaturaRepository candidaturaRepository;

    public SubmissaoDocFinalTeseAlunoHandler(TeseRepository teseRepository, AlunoRepository alunoRepository, CandidaturaRepository candidaturaRepository) {
        this.teseRepository = teseRepository;
        this.alunoRepository = alunoRepository;
        this.candidaturaRepository = candidaturaRepository;
    }

    public void submeterDocFinalTeseAluno(String emailAluno, Integer candidaturaID, byte[] document) throws NotPresentException {
        Optional<Candidatura> optCandidatura = candidaturaRepository.findById(candidaturaID);
        if(optCandidatura.isEmpty()){
            throw new NotPresentException("Candidatura não encontrada");
        }
        Optional<Aluno> optAluno = alunoRepository.findByEmail(emailAluno);
        if(optAluno.isEmpty()){
            throw new NotPresentException("Aluno não encontrado");
        }
        Aluno aluno = optAluno.get();
        List<Candidatura> candidaturas_ativas = aluno.getCandidatura();
        for(Candidatura c : candidaturas_ativas){
            if(c.getId() == candidaturaID){
                c.getTese().setDocumentFinal(document);
                teseRepository.save(c.getTese());
                // TODO: Verificar se é necessário guardar a candidatura e o aluno
                candidaturaRepository.save(c);
            }
        }
    }
}
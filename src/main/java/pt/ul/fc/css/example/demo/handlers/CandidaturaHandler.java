package pt.ul.fc.css.example.demo.handlers;

import pt.ul.fc.css.example.demo.entities.*;
import pt.ul.fc.css.example.demo.repositories.AlunoRepository;
import pt.ul.fc.css.example.demo.repositories.CandidaturaRepository;
import pt.ul.fc.css.example.demo.repositories.TemaRepository;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;
import pt.ul.fc.css.example.exceptions.IllegalCandidaturaException;
import pt.ul.fc.css.example.exceptions.NotPresentException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class CandidaturaHandler {

    private CandidaturaRepository candidaturaRepository;
    private AlunoRepository alunoRepository;
    private TemaRepository temaRepository;
    private TeseRepository teseRepository;

    public CandidaturaHandler(CandidaturaRepository candidaturaRepository, AlunoRepository alunoRepository,
                                 TemaRepository temaRepository) {
        this.candidaturaRepository = candidaturaRepository;
        this.alunoRepository = alunoRepository;
        this.temaRepository = temaRepository;
    }

    public void newCandidatura(Date dataCandidatura, EstadoCandidatura estado, String email) throws IllegalCandidaturaException, NotPresentException {
        List<Candidatura> candidaturas = candidaturaRepository.findAllByAlunoEmail(email);
        if (candidaturas != null) {
            if(candidaturas.size() >= 5){
                throw new IllegalCandidaturaException("O aluno já tem 5 candidaturas ativas");
            }
        }
        Optional<Aluno> optAluno = alunoRepository.findByEmail(email);
        if (!optAluno.isPresent()) {
            throw new NotPresentException("Aluno não encontrado");
        }
        Aluno aluno = optAluno.get();
        Candidatura new_candidatura;
        if(estado == null){
            new_candidatura = new Candidatura(dataCandidatura, EstadoCandidatura.EMPROCESSAMENTO, aluno);
        }else{
            new_candidatura = new Candidatura(dataCandidatura, estado, aluno);
        }
        aluno.addCandidatura(new_candidatura);
        candidaturaRepository.save(new_candidatura);
        alunoRepository.save(aluno);
    }

    public void addTemaToCandidatura(String titulo, Integer candidaturaID) throws NotPresentException {
        Optional<Tema> optTema = temaRepository.findByTitulo(titulo);
        if (!optTema.isPresent()) {
            throw new NotPresentException("Tema não encontrado");
        }
        Tema tema = optTema.get();
        Optional<Candidatura> optCandidatura = candidaturaRepository.findById(candidaturaID);
        if(optCandidatura.isEmpty()){
            throw new NotPresentException("Candidatura não existe");
        }
        Candidatura c = optCandidatura.get();
        c.setTema(tema);
        candidaturaRepository.save(c);
    }

    public void addTeseToCandidatura(Integer teseID, Integer candidaturaID) throws NotPresentException {
        Optional<Tese> optTese = teseRepository.findById(teseID);
        if(optTese.isEmpty()){
            throw new NotPresentException("Tese não encontrada");
        }
        Optional<Candidatura> optCandidatura = candidaturaRepository.findById(candidaturaID);
        if(optCandidatura.isEmpty()){
            throw new NotPresentException("Candidatura não encontrada");
        }
        Tese tese = optTese.get();
        Candidatura c = optCandidatura.get();
        c.setTese(tese);
        candidaturaRepository.save(c);
    }

    public void cancelCandidatura(Integer candidaturaID) {
        candidaturaRepository.deleteById(candidaturaID);
    }
}
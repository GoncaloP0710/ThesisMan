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

public class CandidaturaHandler {

    private CandidaturaRepository candidaturaRepository;

    private Candidatura currentCandidatura;

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
        aluno.addCandidatura(currentCandidatura);
        currentCandidatura = new Candidatura(dataCandidatura, estado, aluno);
        candidaturaRepository.save(currentCandidatura);
        alunoRepository.save(aluno);
    }

    public void addTemaToCandidatura(String titulo) throws NotPresentException {
        Optional<Tema> optTema = temaRepository.findByTitulo(titulo);
        if (!optTema.isPresent()) {
            throw new NotPresentException("Tema não encontrado");
        }
        Tema tema = optTema.get();
        currentCandidatura.setTema(tema);
        candidaturaRepository.save(currentCandidatura);
    }

    public void addTeseToCandidatura(Integer id) throws NotPresentException {
        Optional<Tese> optTese = teseRepository.findById(id);
        if(optTese.isEmpty())
            throw new NotPresentException("Tese não encontrada");
        Tese tese = optTese.get();
        currentCandidatura.setTese(tese);
        candidaturaRepository.save(currentCandidatura);
    }

    public void cancelCandidatura() {
        currentCandidatura = null;
    }
}
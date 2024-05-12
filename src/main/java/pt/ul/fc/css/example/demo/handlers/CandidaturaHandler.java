package pt.ul.fc.css.example.demo.handlers;

import pt.ul.fc.css.example.demo.entities.*;
import pt.ul.fc.css.example.demo.repositories.AlunoRepository;
import pt.ul.fc.css.example.demo.repositories.CandidaturaRepository;
import pt.ul.fc.css.example.demo.repositories.TemaRepository;
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

    public CandidaturaHandler(CandidaturaRepository candidaturaRepository, AlunoRepository alunoRepository) {
        this.candidaturaRepository = candidaturaRepository;
    }

    public void newCandidatura(Date dataCandidatura, EstadoCandidatura estado, Aluno aluno) throws IllegalCandidaturaException, NotPresentException {
        Optional<List<Candidatura>> candidatura = candidaturaRepository.findByAlunoId(aluno.getId());
        if (candidatura.isPresent()) {
            List<Candidatura> candidaturas = candidatura.get();
            if(candidaturas.size() >= 5){
                throw new IllegalCandidaturaException("O aluno já tem 5 candidaturas ativas");
            }
        }
        Optional<Aluno> aluno_optional = alunoRepository.findById(aluno.getId());
        if (!aluno_optional.isPresent()) {
            throw new NotPresentException("Aluno não encontrado");
        }
        aluno.addCandidatura(currentCandidatura);
        currentCandidatura = new Candidatura(dataCandidatura, estado, aluno);
        candidaturaRepository.save(currentCandidatura);
        alunoRepository.save(aluno);
    }

    public void addTemaToCandidatura(Tema tema) throws NotPresentException {
        Optional<Tema> tema_optional = temaRepository.findByTitulo(tema.getTitulo());
        if (!tema_optional.isPresent()) {
            throw new NotPresentException("Tema não encontrado");
        }
        currentCandidatura.setTema(tema);
        candidaturaRepository.save(currentCandidatura);
    }

    public void addTeseToCandidatura(Tese tese) {
        currentCandidatura.setTese(tese);
        candidaturaRepository.save(currentCandidatura);
    }

    public void close() {
        currentCandidatura = null;
    }
}
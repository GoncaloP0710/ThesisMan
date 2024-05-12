package pt.ul.fc.css.example.demo.handlers;

import pt.ul.fc.css.example.demo.entities.*;
import pt.ul.fc.css.example.demo.repositories.CandidaturaRepository;
import java.util.Date;

public class CandidaturaHandler {

    private CandidaturaRepository candidaturaRepository;

    private Candidatura currentCandidatura;

    public CandidaturaHandler(CandidaturaRepository candidaturaRepository) {
        this.candidaturaRepository = candidaturaRepository;
    }

    public void newCandidatura(Date dataCandidatura, EstadoCandidatura estado, Aluno aluno) {
        currentCandidatura = new Candidatura(dataCandidatura, estado, aluno);
        candidaturaRepository.save(currentCandidatura);
    }

    public void addTemaToCandidatura(Tema tema) {
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
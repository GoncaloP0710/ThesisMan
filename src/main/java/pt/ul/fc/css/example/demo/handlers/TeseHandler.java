package pt.ul.fc.css.example.demo.handlers;

import pt.ul.fc.css.example.demo.entities.Aluno;
import pt.ul.fc.css.example.demo.entities.Candidatura;
import pt.ul.fc.css.example.demo.entities.Tese;
import pt.ul.fc.css.example.demo.repositories.CandidaturaRepository;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;

public class TeseHandler {
    
    private TeseRepository teseRepository;
    private Tese currentTese;
    private CandidaturaRepository candidaturaRepository;
    

    public TeseHandler(TeseRepository teseRepository, CandidaturaRepository candidaturaRepository) {
        this.teseRepository = teseRepository;
        this.candidaturaRepository = candidaturaRepository;
    }

    public void SubmitPropostaTeseDocsAluno(Candidatura candidatura, byte[] document, Aluno aluno) {
        if(candidatura.getAluno().getId() != aluno.getId()){
            throw new IllegalArgumentException("Aluno não corresponde ao aluno da candidatura");
        }
        currentTese = new Tese(candidatura);
        currentTese.setDocument(document);
        candidatura.setTese(currentTese);
        teseRepository.save(currentTese);
        candidaturaRepository.save(candidatura);

    }
}

package pt.ul.fc.css.example.demo.handlers;

import java.util.List;
import java.util.Optional;

import pt.ul.fc.css.example.demo.entities.Aluno;
import pt.ul.fc.css.example.demo.entities.Candidatura;
import pt.ul.fc.css.example.demo.entities.Tese;
import pt.ul.fc.css.example.demo.repositories.AlunoRepository;
import pt.ul.fc.css.example.demo.repositories.CandidaturaRepository;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;

public class SubmissaoDocPropostaTeseAlunoHandler {
        
        private CandidaturaRepository candidaturaRepository;
        private AlunoRepository alunoRepository;
        
    
        public SubmissaoDocPropostaTeseAlunoHandler(TeseRepository teseRepository, CandidaturaRepository candidaturaRepository) {
            this.candidaturaRepository = candidaturaRepository;
        }
    
        public void SubmitPropostaTeseDocsAluno(Integer candidaturaID, byte[] document, String emailAluno) {
            Optional<Candidatura> optCandidatura = candidaturaRepository.findById(candidaturaID);
            if(optCandidatura.isEmpty()){
                throw new IllegalArgumentException("Candidatura não encontrada");
            }
            Optional<Aluno> optAluno = alunoRepository.findByEmail(emailAluno);
            if(optAluno.isEmpty()){
                throw new IllegalArgumentException("Aluno não encontrado");
            }
            Aluno aluno = optAluno.get();
            List<Candidatura> candidaturas_ativas = aluno.getCandidatura();
            for(Candidatura c : candidaturas_ativas){
                if(c.getId() == candidaturaID){
                    c.setDocument(document);
                    candidaturaRepository.save(c);
                }
            }
    
        }
    
}

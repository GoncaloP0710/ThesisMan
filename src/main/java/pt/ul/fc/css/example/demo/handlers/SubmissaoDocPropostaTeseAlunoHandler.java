package pt.ul.fc.css.example.demo.handlers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import pt.ul.fc.css.example.demo.entities.Aluno;
import pt.ul.fc.css.example.demo.entities.Candidatura;
import pt.ul.fc.css.example.demo.entities.Tese;
import pt.ul.fc.css.example.demo.repositories.AlunoRepository;
import pt.ul.fc.css.example.demo.repositories.CandidaturaRepository;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;
import pt.ul.fc.css.example.exceptions.NotPresentException;

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
    
        public void SubmitPropostaTeseDocsAluno(Integer candidaturaID, byte[] document, String emailAluno) throws NotPresentException {
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
                    c.getTese().setDocumentProposto(document);
                    teseRepository.save(c.getTese());
                    // TODO: Verificar se é necessário guardar a candidatura e o aluno
                    candidaturaRepository.save(c);
                }
            }
    
        }
    
}

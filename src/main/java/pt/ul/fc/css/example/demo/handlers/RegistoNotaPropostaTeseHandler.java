package pt.ul.fc.css.example.demo.handlers;

import java.util.Optional;

import pt.ul.fc.css.example.demo.entities.Defesa;
import pt.ul.fc.css.example.demo.entities.Tese;
import pt.ul.fc.css.example.demo.repositories.AlunoRepository;
import pt.ul.fc.css.example.demo.repositories.DocenteRepository;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;
import pt.ul.fc.css.example.exceptions.NotPresentException;

public class RegistoNotaPropostaTeseHandler {
    private TeseRepository teseRepository;
    private Defesa currentDefesa;
    private AlunoRepository alunoRepository;
    private DocenteRepository docenteRepository;

    public RegistoNotaPropostaTeseHandler(TeseRepository teseRepository, AlunoRepository alunoRepository, DocenteRepository docenteRepository) {
        this.teseRepository = teseRepository;
        this.alunoRepository = alunoRepository;
        this.docenteRepository = docenteRepository;
    }

    public void registarNotaPropostaTese(Integer teseID, String emailDocente, float nota) throws NotPresentException {
        if (teseID == null || emailDocente == null) {
            throw new IllegalArgumentException("Tese e docente e nota são obrigatórios");
        }
        if (nota < 0 || nota > 20) {
            throw new IllegalArgumentException("Nota inválida");
        }
        Optional<Tese> optTese = teseRepository.findById(teseID);
        if(optTese.isEmpty()) {
            throw new NotPresentException("Tese não encontrada");
        }
        Tese tese = optTese.get();
        if(!tese.getCandidatura().getTema().getSubmissor().getEmail().equals(emailDocente)) {
            throw new IllegalArgumentException("Docente não é o submissor do tema da tese");
        }
        if(tese.getDefesaProposta() == null) {
            throw new IllegalArgumentException("Defesa de proposta não marcada");
        }
        tese.getDefesaProposta().setNota(nota);
    }
    
}

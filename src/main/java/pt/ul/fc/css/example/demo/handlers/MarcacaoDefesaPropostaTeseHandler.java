package pt.ul.fc.css.example.demo.handlers;

import java.util.List;
import java.util.Optional;

import pt.ul.fc.css.example.demo.entities.Defesa;
import pt.ul.fc.css.example.demo.entities.Docente;
import pt.ul.fc.css.example.demo.entities.Tese;
import pt.ul.fc.css.example.demo.repositories.AlunoRepository;
import pt.ul.fc.css.example.demo.repositories.DocenteRepository;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;

public class MarcacaoDefesaPropostaTeseHandler {
    private TeseRepository teseRepository;
    private Defesa currentDefesa;
    private AlunoRepository alunoRepository;
    private DocenteRepository docenteRepository;

    public MarcacaoDefesaPropostaTeseHandler(TeseRepository teseRepository, AlunoRepository alunoRepository, DocenteRepository docenteRepository) {
        this.teseRepository = teseRepository;
        this.alunoRepository = alunoRepository;
        this.docenteRepository = docenteRepository;
    }

    public void marcarDefesaPropostaTese(Integer teseID, String emailDocente) {
        if (teseID == null || emailDocente == null) {
            throw new IllegalArgumentException("Tese e docente são obrigatórios");
        }
        Optional<Tese> optTese = teseRepository.findById(teseID);
        if(optTese.isEmpty()) {
            throw new IllegalArgumentException("Tese não encontrada");
        }
        Tese tese = optTese.get();
        if(!tese.getCandidatura().getTema().getSubmissor().getEmail().equals(emailDocente)) {
            throw new IllegalArgumentException("Docente não é o submissor do tema da tese");
        }
        currentDefesa = new Defesa();
        List<Defesa> defesas = tese.getDefesas();
        for(Defesa defesa : defesas) {
            if(!defesa.isFinal()) {
                throw new IllegalArgumentException("Já existe uma defesa de proposta marcada");
            }
        }
        tese.setDefesaProposta(currentDefesa);
    }
}

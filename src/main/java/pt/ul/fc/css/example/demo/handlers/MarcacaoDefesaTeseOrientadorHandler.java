package pt.ul.fc.css.example.demo.handlers;

import java.util.List;
import java.util.Optional;

import pt.ul.fc.css.example.demo.entities.Defesa;
import pt.ul.fc.css.example.demo.entities.Docente;
import pt.ul.fc.css.example.demo.entities.Tese;
import pt.ul.fc.css.example.demo.repositories.AlunoRepository;
import pt.ul.fc.css.example.demo.repositories.DocenteRepository;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;
import pt.ul.fc.css.example.exceptions.NotPresentException;

public class MarcacaoDefesaTeseOrientadorHandler {
    private TeseRepository teseRepository;
    private Defesa currentDefesa;
    private AlunoRepository alunoRepository;
    private DocenteRepository docenteRepository;

    public MarcacaoDefesaTeseOrientadorHandler(TeseRepository teseRepository, AlunoRepository alunoRepository, DocenteRepository docenteRepository) {
        this.teseRepository = teseRepository;
        this.alunoRepository = alunoRepository;
        this.docenteRepository = docenteRepository;
    }

    public void marcarDefesaTese(Integer teseID, String emailDocente, Integer presidenteId, 
                                Integer arguenteId, Integer docenteOrientadorId) throws NotPresentException {

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

        Optional<Docente> optPresidente = docenteRepository.findById(presidenteId); 
        if (optPresidente.isEmpty()) {
            throw new NotPresentException("Docente não encontrado");
        }
        Docente presidente = optPresidente.get();

        Optional<Docente> optArguente = docenteRepository.findById(arguenteId); 
        if (optArguente.isEmpty()) {
            throw new NotPresentException("Arguente não encontrado");
        }
        Docente arguente = optArguente.get();

        Optional<Docente> optDocenteOrientador = docenteRepository.findById(docenteOrientadorId); 
        if (optDocenteOrientador.isEmpty()) {
            throw new NotPresentException("Docente não encontrado");
        }
        Docente docenteOrientador = optDocenteOrientador.get();

        //TODO: criar o juri e salvar tudo

        // tese.setDefesaProposta(currentDefesa);
    }

}
package pt.ul.fc.css.example.demo.handlers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import pt.ul.fc.css.example.demo.entities.Candidatura;
import pt.ul.fc.css.example.demo.entities.Defesa;
import pt.ul.fc.css.example.demo.entities.Dissertacao;
import pt.ul.fc.css.example.demo.entities.Docente;
import pt.ul.fc.css.example.demo.entities.Juri;
import pt.ul.fc.css.example.demo.entities.Tese;
import pt.ul.fc.css.example.demo.repositories.AlunoRepository;
import pt.ul.fc.css.example.demo.repositories.CandidaturaRepository;
import pt.ul.fc.css.example.demo.repositories.DefesaRepository;
import pt.ul.fc.css.example.demo.repositories.DocenteRepository;
import pt.ul.fc.css.example.demo.repositories.JuriRepository;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;
import pt.ul.fc.css.example.demo.exceptions.*;

@Component
public class MarcacaoDefesaTeseHandler {
    private TeseRepository teseRepository;
    private DocenteRepository docenteRepository;
    private DefesaRepository defesaRepository;
    private JuriRepository juriRepository;
    private CandidaturaHandler candidaturaHandler;

    public MarcacaoDefesaTeseHandler(TeseRepository teseRepository, DocenteRepository docenteRepository,
                                                 DefesaRepository defesaRepository, JuriRepository juriRepository,
                                                 CandidaturaHandler candidaturaHandler) {
        this.teseRepository = teseRepository;
        this.docenteRepository = docenteRepository;
        this.defesaRepository = defesaRepository;
        this.juriRepository = juriRepository;
        this.candidaturaHandler = candidaturaHandler;
    }
    //teseId, data, online, room, arguente
    public void marcarDefesaDissertacaoPropostaTese(Integer docenteId, Integer teseId, Date data, Boolean online, String room, Integer arguenteId) throws NotPresentException {
        if (teseId == null || docenteId == null || data == null || online == null || arguenteId == null) {
            throw new NotPresentException("Argumentos inválidos");
        }

        Optional<Tese> optTese = teseRepository.findById(teseId);
        //System.out.println("TeseId" + optTese);
        if(optTese.isEmpty()) {throw new IllegalArgumentException("Tese não encontrada");}
        Tese tese = optTese.get();

        if(!tese.getCandidatura().getTema().getSubmissor().getId().equals(docenteId)) {throw new IllegalArgumentException("Docente não é o submissor do tema da tese");}
        List<Defesa> defesas = tese.getDefesas();
        for(Defesa defesa : defesas) {
            if(!defesa.isFinal()) {
                throw new IllegalArgumentException("Já existe uma defesa de proposta marcada");
            }
        }
        if(tese.getDocumentProposto() != null){
            throw new NotPresentException("Tese não tem documento de proposta");
        }

        Optional<Docente> optArguente = docenteRepository.findById(arguenteId); 
        if (optArguente.isEmpty()) {throw new NotPresentException("Arguente não encontrado");}
        Docente arguente = optArguente.get(); 

        Defesa d = new Defesa();
        Docente docente = docenteRepository.findById(docenteId).get();
        Juri j = new Juri(arguente, docente);

        d.setTese(tese);
        d.setData(data);
        if(online == false){
            d.setSala(room);
        }
        d.setJuri(j);
        defesaRepository.save(d);
       
    }

    public void marcarDefesaFinalTese(Integer docenteId, Integer teseId, Date data, Boolean online, String room, Integer arguenteId, Integer presidenteId) throws NotPresentException, NoProperStateException {
        //docenteId, teseId, data, online, room, arguente, presidente
        if (teseId == null || docenteId == null || data == null || online == null || arguenteId == null) {
            throw new NotPresentException("Argumentos inválidos");
        }
        Optional<Tese> optTese = teseRepository.findById(teseId);
        if(optTese.isEmpty()) {throw new IllegalArgumentException("Tese não encontrada");}
        Tese tese = optTese.get();
        if(!tese.getCandidatura().getTema().getSubmissor().getId().equals(docenteId)) {throw new IllegalArgumentException("Docente não é o orientador da tese");}
        List<Defesa> defesas = tese.getDefesas();
        for(Defesa defesa : defesas) {
            if(!defesa.isFinal()) {
                throw new IllegalArgumentException("Já existe uma defesa de proposta marcada");
            }
        }


    }
}

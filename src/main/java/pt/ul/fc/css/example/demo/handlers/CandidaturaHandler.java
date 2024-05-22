package pt.ul.fc.css.example.demo.handlers;

import pt.ul.fc.css.example.demo.dtos.CandidaturaDTO;
import pt.ul.fc.css.example.demo.dtos.TeseDTO;
import pt.ul.fc.css.example.demo.entities.*;
import pt.ul.fc.css.example.demo.repositories.AlunoRepository;
import pt.ul.fc.css.example.demo.repositories.CandidaturaRepository;
import pt.ul.fc.css.example.demo.repositories.TemaRepository;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;
import pt.ul.fc.css.example.demo.repositories.DefesaRepository;
import pt.ul.fc.css.example.demo.exceptions.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class CandidaturaHandler {

    private CandidaturaRepository candidaturaRepository;
    private AlunoRepository alunoRepository;
    private TemaRepository temaRepository;
    private TeseRepository teseRepository;
    private DefesaRepository defesaRepository;

    public CandidaturaHandler(CandidaturaRepository candidaturaRepository, AlunoRepository alunoRepository,
                                 TemaRepository temaRepository, TeseRepository teseRepository, DefesaRepository defesaRepository) {
        this.candidaturaRepository = candidaturaRepository;
        this.alunoRepository = alunoRepository;
        this.temaRepository = temaRepository;
        this.teseRepository = teseRepository;
        this.defesaRepository = defesaRepository;

    }

    public CandidaturaDTO newCandidatura(Date dataCandidatura, EstadoCandidatura estado, Integer alunoId, Integer temaId) throws IllegalCandidaturaException, NotPresentException {
        Optional<Aluno> optAluno = alunoRepository.findById(alunoId);
        if (!optAluno.isPresent()) {
            throw new NotPresentException("Aluno não encontrado");
        }
        List<Candidatura> candidaturas = candidaturaRepository.findAllByAlunoId(alunoId);
        if (candidaturas != null) {
            if(candidaturas.size() >= 5){
                throw new IllegalCandidaturaException("O aluno já tem 5 candidaturas ativas");
            }
            for (Candidatura c : candidaturas) {
                Date date1 = c.getDataCandidatura();
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(date1);
                cal2.setTime(dataCandidatura);

                if (c.getTema().getId() == temaId && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)){
                    throw new IllegalCandidaturaException("O aluno já tem uma candidatura a esse tema");
                }
            }
        }

        Aluno aluno = optAluno.get();
        Optional<Tema> optTema = temaRepository.findById(temaId);
        if (!optTema.isPresent()) {
            throw new NotPresentException("Tema não encontrado");
        }
        Tema tema = optTema.get();
        Candidatura new_candidatura;
        if(estado != null){
            new_candidatura = new Candidatura(dataCandidatura, estado, aluno, tema);
            candidaturaRepository.save(new_candidatura);
        }else{
            throw new NotPresentException("Estado não presente (null)");
        }
        aluno.addCandidatura(new_candidatura);
        alunoRepository.save(aluno);
        return new CandidaturaDTO(new_candidatura.getId(), new_candidatura.getTema().getId(),new_candidatura.getDataCandidatura(), new_candidatura.getEstado().name(), -1, new_candidatura.getAluno().getId());
    }


    public void addTeseToCandidatura(Integer teseID, Integer candidaturaID) throws NotPresentException {
        Optional<Tese> optTese = teseRepository.findById(teseID);
        if(optTese.isEmpty()){
            throw new NotPresentException("Tese não encontrada");
        }
        Optional<Candidatura> optCandidatura = candidaturaRepository.findById(candidaturaID);
        if(optCandidatura.isEmpty()){
            throw new NotPresentException("Candidatura não encontrada");
        }
        Tese tese = optTese.get();
        Candidatura c = optCandidatura.get();
        c.setTese(tese);
        candidaturaRepository.save(c);
    }

    public void cancelCandidatura(Integer candidaturaID) throws NotPresentException {
        Optional<Candidatura> cand = candidaturaRepository.findById(candidaturaID);
        if (cand.isEmpty()) {
            throw new NotPresentException("Candidatura não encontrada");
        }
        candidaturaRepository.deleteById(candidaturaID);
    }

    public List<Candidatura> getCandidaturas(){
        return candidaturaRepository.findAll();
    }

    public List<Candidatura> getCandidaturasAceites(){
        return candidaturaRepository.findAllByEstado(EstadoCandidatura.APROVADO);
    }

    public Tese getTese(Integer id){
        return teseRepository.findById(id).get();
    }

    public List<Candidatura> getCandidaturasWithDefesaPropostaDone() throws NoProperStateException{
        List<Candidatura> candidaturas = candidaturaRepository.findAllByEstado(EstadoCandidatura.APROVADO);
        List<Candidatura> candidaturasWithDefesaPropostaDone = new ArrayList<Candidatura>();
        for(Candidatura c : candidaturas){
            if(c.getTese().getDocumentProposto() != null && c.getTese().getDefesas().size() > 0){
                for(Defesa d : c.getTese().getDefesas()){
                    if(!d.isFinal() && d.getNota() > 10){
                        candidaturasWithDefesaPropostaDone.add(c);
                    }else{
                        throw new NoProperStateException("Aluno não tem defesa de proposta aprovada");
                    }
                }
            }
        }
        return candidaturasWithDefesaPropostaDone;
    }

    public List<Candidatura> getCandidaturasWithDefesaPropostaWithoutNota(){
        List<Candidatura> candidaturas = candidaturaRepository.findAllByEstado(EstadoCandidatura.APROVADO);
        List<Candidatura> candidaturasWithDefesaWithoutNota = new ArrayList<Candidatura>();
        for(Candidatura c : candidaturas){
            if(c.getTese().getDefesas().size() > 0){
                for(Defesa d : c.getTese().getDefesas()){
                    if(!d.isFinal() && d.getNota() == -1){
                        candidaturasWithDefesaWithoutNota.add(c);
                    }
                }
            }
        }
        return candidaturasWithDefesaWithoutNota;
    }

    public List<Candidatura> getCandidaturasWithDefesaFinalWithoutNota(){
        List<Candidatura> candidaturas = candidaturaRepository.findAllByEstado(EstadoCandidatura.APROVADO);
        List<Candidatura> candidaturasWithDefesaWithoutNota = new ArrayList<Candidatura>();
        for(Candidatura c : candidaturas){
            if(c.getTese().getDefesas().size() > 0){
                for(Defesa d : c.getTese().getDefesas()){
                    if(d.isFinal() && d.getNota() == -1){
                        candidaturasWithDefesaWithoutNota.add(c);
                    }
                }
            }
        }
        return candidaturasWithDefesaWithoutNota;
    }

    public List<CandidaturaDTO> listarCandidaturasAluno(Integer alunoID) throws NotPresentException {
        if (alunoID == null) {
            throw new IllegalArgumentException("Id do aluno é obrigatório");
        }
        Optional<Aluno> optAluno = alunoRepository.findById(alunoID);
        if(optAluno.isEmpty()){
            throw new NotPresentException("Aluno não encontrado");
        }
        List<Candidatura> candidaturas = candidaturaRepository.findAll();

        for (Candidatura c : candidaturas) {
        }

        List<Candidatura> candidaturas2 = new ArrayList<>();
        for (Candidatura c : candidaturas) {
            if(c.getAluno().getId() == alunoID){
                candidaturas2.add(c);
            }
        }

        List<CandidaturaDTO> candidaturasDTO = new ArrayList<>();
        for(Candidatura c : candidaturas2){
            if (c.getTema() != null && c.getTese() != null) {
                candidaturasDTO.add(new CandidaturaDTO(c.getId(), c.getTema().getId(), c.getDataCandidatura(), c.getEstado().name(), c.getTese().getId(), c.getAluno().getId()));
            } else if (c.getTema() != null) {
                candidaturasDTO.add(new CandidaturaDTO(c.getId(), c.getTema().getId(), c.getDataCandidatura(), c.getEstado().name(), -1, c.getAluno().getId()));
            } else {
                candidaturasDTO.add(new CandidaturaDTO(c.getId(), -1, c.getDataCandidatura(), c.getEstado().name(), -1, c.getAluno().getId()));
            }
        }
        return candidaturasDTO;
    }


    public CandidaturaDTO updateCandidaturaStatus(Integer candidaturaID, String estado) throws NotPresentException {
        System.out.println("1");
        Optional<Candidatura> optCandidatura = candidaturaRepository.findById(candidaturaID);
        System.out.println("2");
        if(optCandidatura.isEmpty()){throw new NotPresentException("Candidatura não encontrada");}
        System.out.println("3");
        Candidatura candidatura = optCandidatura.get();
        System.out.println("4");
        candidatura.setEstado(EstadoCandidatura.valueOf(estado));
        System.out.println("5");
        Tese t = null;
        if(candidatura.getEstado() == EstadoCandidatura.APROVADO){
            System.out.println("6");
            Tema tema = candidatura.getTema();
            System.out.println("7");
            if(!(tema.getSubmissor() instanceof Docente)){
                if (candidatura.getTese() != null) {
                    candidaturaRepository.save(candidatura);
                    return new CandidaturaDTO(candidatura.getId(), candidatura.getTema().getId(), candidatura.getDataCandidatura(), candidatura.getEstado().name(), candidatura.getTese().getId(), candidatura.getAluno().getId());

                } else {
                    System.out.println("8");
                    t = new Projeto(candidatura);
                    teseRepository.save(t);
                    System.out.println("10");
                    candidatura.setTese(t);
                    candidaturaRepository.save(candidatura);
                    System.out.println("11");
                    return new CandidaturaDTO(candidatura.getId(), candidatura.getTema().getId(), candidatura.getDataCandidatura(), candidatura.getEstado().name(), candidatura.getTese().getId(), candidatura.getAluno().getId());

                }

            }else{
                if (candidatura.getTese() != null) {
                    candidaturaRepository.save(candidatura);
                    return new CandidaturaDTO(candidatura.getId(), candidatura.getTema().getId(), candidatura.getDataCandidatura(), candidatura.getEstado().name(), candidatura.getTese().getId(), candidatura.getAluno().getId());

                } else {
                    System.out.println("9");
                    t = new Dissertacao(candidatura);
                    teseRepository.save(t);
                    System.out.println("10");
                    candidatura.setTese(t);
                    candidaturaRepository.save(candidatura);
                    System.out.println("11");
                    return new CandidaturaDTO(candidatura.getId(), candidatura.getTema().getId(), candidatura.getDataCandidatura(), candidatura.getEstado().name(), candidatura.getTese().getId(), candidatura.getAluno().getId());

                }

            }
        }
        System.out.println("10");
        candidaturaRepository.save(candidatura);
        System.out.println("11");
        return new CandidaturaDTO(candidatura.getId(), candidatura.getTema().getId(), candidatura.getDataCandidatura(), candidatura.getEstado().name(), -1, candidatura.getAluno().getId());
  }

    public List<CandidaturaDTO> listarCandidaturasAlunosProposta(Integer alunoID){
        List<Defesa> defesas = defesaRepository.findAll();
        for(Defesa d : defesas){
        }
        List<Candidatura> candidaturas = candidaturaRepository.findAllByEstado(EstadoCandidatura.APROVADO);
        List<Candidatura> candidaturasWithDefesaWithoutNota = new ArrayList<Candidatura>();
        for(Candidatura c : candidaturas){
            Optional<Tese> teseOptional = teseRepository.findById(c.getTese().getId());
            if(teseOptional.isEmpty()){
            }
            Tese tese = teseOptional.get();
            for(Defesa d : defesas){
                if (d.getTeseId() == tese.getId()) {
                    if (tese.getCandidatura().getAluno().getId().equals(alunoID)) {
                        if(!d.isFinal() && d.getNota() == -1){
                            candidaturasWithDefesaWithoutNota.add(c);
                        }
                    }
                } else {
                }
            }
        }
        List<CandidaturaDTO> candidaturasDTO = new ArrayList<>();
        for(Candidatura c : candidaturasWithDefesaWithoutNota){
            if (c.getTema() != null && c.getTese() != null) {
                candidaturasDTO.add(new CandidaturaDTO(c.getId(), c.getTema().getId(), c.getDataCandidatura(), c.getEstado().name(), c.getTese().getId(), c.getAluno().getId()));
            } else if (c.getTema() != null) {
                candidaturasDTO.add(new CandidaturaDTO(c.getId(), c.getTema().getId(), c.getDataCandidatura(), c.getEstado().name(), -1, c.getAluno().getId()));
            } else {
                candidaturasDTO.add(new CandidaturaDTO(c.getId(), -1, c.getDataCandidatura(), c.getEstado().name(), -1, c.getAluno().getId()));
            }
        }
        return candidaturasDTO;
    }

    public List<CandidaturaDTO> listarCandidaturasAlunosFinal(Integer alunoID){
        List<Defesa> defesas = defesaRepository.findAll();
        for(Defesa d : defesas){
        }
        List<Candidatura> candidaturas = candidaturaRepository.findAllByEstado(EstadoCandidatura.APROVADO);
        List<Candidatura> candidaturasWithDefesaWithoutNota = new ArrayList<Candidatura>();
        for(Candidatura c : candidaturas){
            Optional<Tese> teseOptional = teseRepository.findById(c.getTese().getId());
            if(teseOptional.isEmpty()){
            }
            Tese tese = teseOptional.get();
            for(Defesa d : defesas){
                if (d.getTeseId() == tese.getId()) {
                    if (tese.getCandidatura().getAluno().getId().equals(alunoID)) {
                        if(d.isFinal() && d.getNota() == -1){
                            candidaturasWithDefesaWithoutNota.add(c);
                        }
                    }
                } else {
                }
            }
        }
        List<CandidaturaDTO> candidaturasDTO = new ArrayList<>();
        for(Candidatura c : candidaturasWithDefesaWithoutNota){
            if (c.getTema() != null && c.getTese() != null) {
                candidaturasDTO.add(new CandidaturaDTO(c.getId(), c.getTema().getId(), c.getDataCandidatura(), c.getEstado().name(), c.getTese().getId(), c.getAluno().getId()));
            } else if (c.getTema() != null) {
                candidaturasDTO.add(new CandidaturaDTO(c.getId(), c.getTema().getId(), c.getDataCandidatura(), c.getEstado().name(), -1, c.getAluno().getId()));
            } else {
                candidaturasDTO.add(new CandidaturaDTO(c.getId(), -1, c.getDataCandidatura(), c.getEstado().name(), -1, c.getAluno().getId()));
            }
        }
        return candidaturasDTO;
    }

}
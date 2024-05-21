package pt.ul.fc.css.example.demo.handlers;

import pt.ul.fc.css.example.demo.dtos.CandidaturaDTO;
import pt.ul.fc.css.example.demo.dtos.TeseDTO;
import pt.ul.fc.css.example.demo.entities.*;
import pt.ul.fc.css.example.demo.repositories.AlunoRepository;
import pt.ul.fc.css.example.demo.repositories.CandidaturaRepository;
import pt.ul.fc.css.example.demo.repositories.TemaRepository;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;
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

    public CandidaturaHandler(CandidaturaRepository candidaturaRepository, AlunoRepository alunoRepository,
                                 TemaRepository temaRepository, TeseRepository teseRepository) {
        this.candidaturaRepository = candidaturaRepository;
        this.alunoRepository = alunoRepository;
        this.temaRepository = temaRepository;
        this.teseRepository = teseRepository;
    }

    public CandidaturaDTO newCandidatura(Date dataCandidatura, EstadoCandidatura estado, Integer alunoId, Integer temaId) throws IllegalCandidaturaException, NotPresentException {
        Optional<Aluno> optAluno = alunoRepository.findById(alunoId);
        if (!optAluno.isPresent()) {
            throw new NotPresentException("Aluno não encontrado");
        }
        List<Candidatura> candidaturas = candidaturaRepository.findAllByAlunoId(alunoId);
        System.out.println("candidaturas size: " + candidaturas.size());
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
        System.out.println("aluno id: " + aluno.getId());
        Optional<Tema> optTema = temaRepository.findById(temaId);
        if (!optTema.isPresent()) {
            throw new NotPresentException("Tema não encontrado");
        }
        Tema tema = optTema.get();
        System.out.println("tema id: " + tema.getId());
        Candidatura new_candidatura;
        if(estado != null){
            System.out.println("| 1 |");
            new_candidatura = new Candidatura(dataCandidatura, estado, aluno, tema);
            System.out.println("| 2 |");
            candidaturaRepository.save(new_candidatura);
            System.out.println("| 3 |");
            System.out.println("new_candidatura id: " + new_candidatura.getId());
        }else{
            System.out.println("| 4 |");
            throw new NotPresentException("Estado não presente (null)");
        }
        aluno.addCandidatura(new_candidatura);
        System.out.println("aluno candidaturas size: " + aluno.getCandidatura().size());
        alunoRepository.save(aluno);

        System.out.println("============================");
        System.out.println("new_candidatura id: " + new_candidatura.getId());
        System.out.println("============================");

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
        System.out.println("========== cancelCandidatura =========");
        Optional<Candidatura> cand = candidaturaRepository.findById(candidaturaID);

        if (cand.isEmpty()) {
            throw new NotPresentException("Candidatura não encontrada");
        }
        System.out.println("cancelCandidaturaId: " + candidaturaID);
        candidaturaRepository.deleteById(candidaturaID);

        System.out.println("candidatura deleted");
        System.out.println("==========  =========");
    }

    public List<Candidatura> getCandidaturas(){
        System.out.println("========== getCandidaturas =========");
        System.out.println("==========  =========");
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
        System.out.println("============================");
        System.out.println("alunoId: " + alunoID);
        System.out.println("============================");
        if (alunoID == null) {
            throw new IllegalArgumentException("Id do aluno é obrigatório");
        }
        Optional<Aluno> optAluno = alunoRepository.findById(alunoID);
        if(optAluno.isEmpty()){
            throw new NotPresentException("Aluno não encontrado");
        }
        System.out.println("before query");
        List<Candidatura> candidaturas = candidaturaRepository.findAll();

        for (Candidatura c : candidaturas) {
            System.out.println(c.getId());
        }

        List<Candidatura> candidaturas2 = new ArrayList<>();
        for (Candidatura c : candidaturas) {
            System.out.println(c.getId());
            if(c.getAluno().getId() == alunoID){
                candidaturas2.add(c);
                System.out.println("added candidatura: " + c.getId());
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
}
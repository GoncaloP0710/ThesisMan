package pt.ul.fc.css.example.demo.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import pt.ul.fc.css.example.demo.dtos.AlunoDTO;
import pt.ul.fc.css.example.demo.entities.Aluno;
import pt.ul.fc.css.example.demo.entities.Docente;
import pt.ul.fc.css.example.demo.entities.Tema;
import pt.ul.fc.css.example.demo.entities.Tese;
import pt.ul.fc.css.example.demo.entities.Candidatura;
import pt.ul.fc.css.example.demo.entities.Dissertacao;
import pt.ul.fc.css.example.demo.entities.Projeto;
import pt.ul.fc.css.example.demo.repositories.AlunoRepository;
import pt.ul.fc.css.example.demo.repositories.CandidaturaRepository;
import pt.ul.fc.css.example.demo.repositories.DocenteRepository;
import pt.ul.fc.css.example.demo.repositories.TemaRepository;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;
import pt.ul.fc.css.example.demo.exceptions.NotPresentException;

@Component
public class AtribuicaoTemaAdminHandler {
    private TemaRepository temaRepository;
    private DocenteRepository docenteRepository;
    private CandidaturaRepository candidaturaRepository;
    private AlunoRepository alunoRepository;
    private TeseRepository teseRepository;

    public AtribuicaoTemaAdminHandler(TemaRepository temaRepository, DocenteRepository docenteRepository, AlunoRepository alunoRepository,
                                        CandidaturaRepository candidaturaRepository, TeseRepository teseRepository) {
        this.temaRepository = temaRepository;
        this.docenteRepository = docenteRepository;
        this.candidaturaRepository = candidaturaRepository;
        this.alunoRepository = alunoRepository;
        this.teseRepository = teseRepository;
    }

    public void atribuirTemaAdmin(Integer temaId, Integer alunoId, Integer docenteId) throws NotPresentException {
        Optional<Docente> optDocente = docenteRepository.findById(docenteId);
        if (optDocente.isEmpty()) {
            throw new NotPresentException("Docente não encontrado");
        }
        Docente docente = optDocente.get();
        if(!docente.isAdmnistrador()){
            throw new IllegalArgumentException("Docente não tem permissões para atribuir temas");
        }
        Optional<Aluno> optAluno = alunoRepository.findById(alunoId);
        if (optAluno.isEmpty()) {
            throw new NotPresentException("Aluno não encontrado");
        }
        List<Candidatura> candidaturas = candidaturaRepository.findAllByAlunoId(alunoId);
        if(candidaturas.isEmpty()){
            throw new IllegalArgumentException("Aluno não tem candidaturas");
        }
        Optional<Candidatura> optCandidatura = candidaturas.stream().filter(candidatura -> candidatura.getTema() == null).findFirst();
        if(optCandidatura.isEmpty()){
            throw new NotPresentException("Aluno já tem um tema atribuído");
        }
        Optional<Tema> optTema = temaRepository.findById(temaId);
        if (optTema.isEmpty()) {
            throw new NotPresentException("Tema não encontrado");
        }
        Tema tema = optTema.get();
        Candidatura candidatura = optCandidatura.get();
        candidatura.setTema(tema);

        //*Set Tese da candidatura
        if (tema.getSubmissor() instanceof Docente) {
            Dissertacao tese = new Dissertacao(candidatura);
            teseRepository.save(tese);

            candidatura.setTese(tese);
            candidaturaRepository.save(candidatura);
        } else {
            Projeto tese = new Projeto(candidatura);
            teseRepository.save(tese);
            
            candidatura.setTese(tese);
            candidaturaRepository.save(candidatura);
        }
        
    }

    public List<AlunoDTO> getAlunos(){
        List<Aluno> alunos = alunoRepository.findAll();
        List<AlunoDTO> result = new ArrayList<>();
        List<Integer> candidaturasIds = new ArrayList<>();
        for(Aluno a : alunos){
            for(Candidatura c : a.getCandidatura()){
                candidaturasIds.add(c.getId());
            }
            result.add(new AlunoDTO(a.getId(), a.getName(), a.getEmail(), a.getAverage(), a.getMestrado().getNome(), candidaturasIds));
        }
        return result;
    }

    public AlunoDTO getAluno(String name) throws NotPresentException{
        Optional<Aluno> a = alunoRepository.findByNome(name);
        if(a.isEmpty()){
            throw new NotPresentException("Aluno não encontrado");
        }
        Aluno aluno = a.get();
        List<Integer> candidaturasIds = new ArrayList<>();
        for(Candidatura c : aluno.getCandidatura()){
            candidaturasIds.add(c.getId());
        }
        return new AlunoDTO(aluno.getId(), aluno.getName(), aluno.getEmail(), aluno.getAverage(), aluno.getMestrado().getNome(), candidaturasIds);
    }
}

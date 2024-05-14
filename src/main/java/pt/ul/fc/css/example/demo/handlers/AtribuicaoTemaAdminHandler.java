package pt.ul.fc.css.example.demo.handlers;

import java.util.List;
import java.util.Optional;

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
import pt.ul.fc.css.example.exceptions.NotPresentException;

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

    public void atribuirTemaAdmin(String titulo, String emailAluno, String emailDocente) throws NotPresentException {
        Optional<Docente> optDocente = docenteRepository.findByEmail(emailDocente);
        if (optDocente.isEmpty()) {
            throw new NotPresentException("Docente não encontrado");
        }
        Docente docente = optDocente.get();
        if(!docente.isAdmnistrador()){
            throw new IllegalArgumentException("Docente não tem permissões para atribuir temas");
        }
        Optional<Aluno> optAluno = alunoRepository.findByEmail(emailAluno);
        if (optAluno.isEmpty()) {
            throw new NotPresentException("Aluno não encontrado");
        }
        List<Candidatura> candidaturas = candidaturaRepository.findAllByAlunoEmail(emailAluno);
        if(candidaturas.isEmpty()){
            throw new IllegalArgumentException("Aluno não tem candidaturas");
        }
        Optional<Candidatura> optCandidatura = candidaturas.stream().filter(candidatura -> candidatura.getTema() == null).findFirst();
        if(optCandidatura.isEmpty()){
            throw new NotPresentException("Aluno já tem um tema atribuído");
        }
        Optional<Tema> optTema = temaRepository.findByTitulo(titulo);
        if (optTema.isEmpty()) {
            throw new NotPresentException("Tema não encontrado");
        }
        Tema tema = optTema.get();
        Candidatura candidatura = optCandidatura.get();
        candidatura.setTema(tema);

        // Set Tese da candidatura
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
}

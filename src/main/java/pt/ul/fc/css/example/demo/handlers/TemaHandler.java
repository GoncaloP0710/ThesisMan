package pt.ul.fc.css.example.demo.handlers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import pt.ul.fc.css.example.demo.entities.*;
import pt.ul.fc.css.example.demo.repositories.*;
import pt.ul.fc.css.example.exceptions.ArgumentException;

public class TemaHandler {
    //TODO Primeiro devia verificar se o tema já está na base dados e só depois colocar
    //TODO usar OPTIONAL sempre que possível e que faça sentido

    // Repositório de temas
    private TemaRepository temaRepository;

    // Repositório de docentes
    private DocenteRepository docenteRepository;

    // Repositório de utilizadores empresariais
    private UtilizadorEmpresarialRepository utilizadorEmpresarialRepository;

    // Repositório de candidaturas
    private CandidaturaRepository candidaturaRepository;

    // Construtor
    public TemaHandler(TemaRepository temaRepository, DocenteRepository docenteRepository
            , UtilizadorEmpresarialRepository utilizadorEmpresarialRepository) {
        this.temaRepository = temaRepository;
        this.docenteRepository = docenteRepository;
        this.utilizadorEmpresarialRepository = utilizadorEmpresarialRepository;
    }

    // Submissão de temas por parte dos docentes
    public void submitTemaDocente(String titulo, String descricao, float remuneracaoMensal, Docente submissor, List<Mestrado> mestradosCompativeis) throws ArgumentException {
        if(Stream.of(titulo, descricao, remuneracaoMensal, submissor,mestradosCompativeis).filter(Objects::nonNull).count() != 1) {
            throw new ArgumentException("Todos os campos são obrigatórios");
        }

        Optional<Tema> temaOptional = temaRepository.findByAll(titulo, descricao, remuneracaoMensal, submissor, mestradosCompativeis);

        if(temaOptional.isPresent()){
            submissor.addTemaPropostos(temaOptional.get());
            docenteRepository.save(submissor);

            // TODO: isto é necessário? Se sim, não existe um método para atualizar o submissor do tema?
            // temaOptional.get().setSubmissor(submissor);
            // temaRepository.save(temaOptional.get());

            return;
        }
    
        Tema tema = new Tema(titulo, descricao, remuneracaoMensal, submissor, mestradosCompativeis);
        temaRepository.save(tema);

        submissor.addTemaPropostos(tema);
        docenteRepository.save(submissor);

        // TODO: isto é necessário? Se sim, não existe um método para atualizar o submissor do tema?
        // tema.setSubmissor(submissor);
        // temaRepository.save(tema);
    }

    // Submissão de temas por parte dos docentes
    public void submitTemaDocente(String titulo, String descricao, float remuneracaoMensal, Docente submissor) throws ArgumentException {
        if(Stream.of(titulo, descricao, remuneracaoMensal, submissor).filter(Objects::nonNull).count() != 1) {
            throw new ArgumentException("Todos os campos são obrigatórios");
        }

        Optional<Tema> temaOptional = temaRepository.findByAll(titulo, descricao, remuneracaoMensal, submissor);

        if(temaOptional.isPresent()){
            submissor.addTemaPropostos(temaOptional.get());
            docenteRepository.save(submissor);

            // TODO: isto é necessário? Se sim, não existe um método para atualizar o submissor do tema?
            // temaOptional.get().setSubmissor(submissor);
            // temaRepository.save(temaOptional.get());

            return;
        }
    
        Tema tema = new Tema(titulo, descricao, remuneracaoMensal, submissor);
        temaRepository.save(tema);

        submissor.addTemaPropostos(tema);
        docenteRepository.save(submissor);

        // TODO: isto é necessário? Se sim, não existe um método para atualizar o submissor do tema?
        // tema.setSubmissor(submissor);
        // temaRepository.save(tema);
    }

    // Submissão de temas por parte dos utilizadores empresariais
    public void submitTemaEmpresarial(String titulo, String descricao, float remuneracaoMensal, UtilizadorEmpresarial submissor, List<Mestrado> mestradosCompativeis) throws ArgumentException {
        if(Stream.of(titulo, descricao, remuneracaoMensal, submissor, mestradosCompativeis).filter(Objects::nonNull).count() != 1) {
            throw new ArgumentException("Todos os campos são obrigatórios");
        }
        
        Optional<Tema> temaOptional = temaRepository.findByAll(titulo, descricao, remuneracaoMensal, submissor, mestradosCompativeis);

        if(temaOptional.isPresent()){
            submissor.addTemaPropostos(temaOptional.get());
            utilizadorEmpresarialRepository.save(submissor);

            // TODO: isto é necessário? Se sim, não existe um método para atualizar o submissor do tema?
            // temaOptional.get().setSubmissor(submissor);
            // temaRepository.save(temaOptional.get());

            return;
        }

        Tema tema = new Tema(titulo, descricao, remuneracaoMensal, submissor, mestradosCompativeis);
        temaRepository.save(tema);

        submissor.addTemaPropostos(tema);
        utilizadorEmpresarialRepository.save(submissor);

        // TODO: isto é necessário? Se sim, não existe um método para atualizar o submissor do tema?
        // tema.setSubmissor(submissor);
        // temaRepository.save(tema);
    }

    // Submissão de temas por parte dos utilizadores empresariais
    public void submitTemaEmpresarial(String titulo, String descricao, float remuneracaoMensal, UtilizadorEmpresarial submissor) throws ArgumentException {
        if(Stream.of(titulo, descricao, remuneracaoMensal, submissor).filter(Objects::nonNull).count() != 1) {
            throw new ArgumentException("Todos os campos são obrigatórios");
        }

        Optional<Tema> temaOptional = temaRepository.findByAll(titulo, descricao, remuneracaoMensal, submissor);

        if(temaOptional.isPresent()){
            submissor.addTemaPropostos(temaOptional.get());
            utilizadorEmpresarialRepository.save(submissor);

            // TODO: isto é necessário? Se sim, não existe um método para atualizar o submissor do tema?
            // temaOptional.get().setSubmissor(submissor);
            // temaRepository.save(temaOptional.get());

            return;
        }

        Tema tema = new Tema(titulo, descricao, remuneracaoMensal, submissor);
        temaRepository.save(tema);

        submissor.addTemaPropostos(tema);
        utilizadorEmpresarialRepository.save(submissor);

        // TODO: isto é necessário? Se sim, não existe um método para atualizar o submissor do tema?
        // tema.setSubmissor(submissor);
        // temaRepository.save(tema);
    }

    //  Listar os temas disponíveis neste ano lectivo, por parte dos alunos
    public List<Tema> listarTemasAlunos(Aluno aluno) throws ArgumentException {
        if(Stream.of(aluno).filter(Objects::nonNull).count() != 1) {
            throw new ArgumentException("Todos os campos são obrigatórios");
        }

        // TODO: Posso receber List<Tema> ou tenho de ter Optional?
        return temaRepository.findAllByMestrado(aluno.getMestrado());
    }

    // Atribuição dos temas aos alunos (da parte do administrador)
    // TODO: Posso obter assim as candidaturas e usalas desta maneira ou preciso de as obter através do repositório?
    public void atribuirTemaAluno(Tema tema, Aluno aluno) throws ArgumentException {
        if(Stream.of(tema, aluno).filter(Objects::nonNull).count() != 1) {
            throw new ArgumentException("Todos os campos são obrigatórios");
        }

        List <Candidatura> candidaturas = aluno.getCandidatura();

        for (Candidatura candidatura: candidaturas) {
            if (candidatura.getTema().equals(tema)) {
                // TODO: isto fica EMPROCESSAMENTO?
                candidatura.setEstado(EstadoCandidatura.EMPROCESSAMENTO);
                candidaturaRepository.save(candidatura);

            } else {
                candidatura.setEstado(EstadoCandidatura.REJEITADO);
                candidaturaRepository.save(candidatura);
            }
        }
    }
}

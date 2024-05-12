package pt.ul.fc.css.example.demo.handlers;

import java.util.List;
import pt.ul.fc.css.example.demo.entities.*;
import pt.ul.fc.css.example.demo.repositories.*;

public class TemaHandler {
    
    // Repositório de temas
    private TemaRepository temaRepository;

    // Repositório de docentes
    private DocenteRepository docenteRepository;

    // Repositório de utilizadores empresariais
    private UtilizadorEmpresarialRepository utilizadorEmpresarialRepository;

    // Construtor
    public TemaHandler(TemaRepository temaRepository, DocenteRepository docenteRepository
            , UtilizadorEmpresarialRepository utilizadorEmpresarialRepository) {
        this.temaRepository = temaRepository;
        this.docenteRepository = docenteRepository;
        this.utilizadorEmpresarialRepository = utilizadorEmpresarialRepository;
    }

    // Submissão de temas por parte dos docentes
    public void submitTemaDocente(String titulo, String descricao, float remuneracaoMensal, Docente submissor, List<Mestrado> mestradosCompativeis) {
        Tema tema = new Tema(titulo, descricao, remuneracaoMensal, submissor, mestradosCompativeis);
        temaRepository.save(tema);

        submissor.addTemaPropostos(tema);
        docenteRepository.save(submissor);
    }

    // Submissão de temas por parte dos docentes
    public void submitTemaDocente(String titulo, String descricao, float remuneracaoMensal, Docente submissor) {
        Tema tema = new Tema(titulo, descricao, remuneracaoMensal, submissor);
        temaRepository.save(tema);

        submissor.addTemaPropostos(tema);
        docenteRepository.save(submissor);
    }

    // Submissão de temas por parte dos utilizadores empresariais
    public void submitTemaEmpresarial(String titulo, String descricao, float remuneracaoMensal, UtilizadorEmpresarial submissor, List<Mestrado> mestradosCompativeis) {
        Tema tema = new Tema(titulo, descricao, remuneracaoMensal, submissor, mestradosCompativeis);
        temaRepository.save(tema);

        submissor.addTemaPropostos(tema);
        utilizadorEmpresarialRepository.save(submissor);
    }

    // Submissão de temas por parte dos utilizadores empresariais
    public void submitTemaEmpresarial(String titulo, String descricao, float remuneracaoMensal, UtilizadorEmpresarial submissor) {
        Tema tema = new Tema(titulo, descricao, remuneracaoMensal, submissor);
        temaRepository.save(tema);

        submissor.addTemaPropostos(tema);
        utilizadorEmpresarialRepository.save(submissor);
    }

    //  Listar os temas disponíveis neste ano lectivo, por parte dos alunos
    public List<Tema> listarTemasAlunos(Aluno aluno) {
        return temaRepository.findAllByMestrado(aluno.getMestrado());
    }

    // Atribuição dos temas aos alunos (da parte do administrador)
    public void atribuirTemaAluno(Tema tema, Aluno aluno) {
        List <Candidatura> candidaturas = aluno.getCandidatura();
        for (Candidatura candidatura: candidaturas) {
            if (candidatura.getTema().equals(tema)) {
                // TODO: alterar codigo da candidatura para atribuido ser visivel ou adicionar um seter
                // candidatura.estado = EstadoCandidatura.APROVADO;
            } else {
                // TODO: alterar EstadoCandidatura para REJEITADO
            }
        }
    }
}

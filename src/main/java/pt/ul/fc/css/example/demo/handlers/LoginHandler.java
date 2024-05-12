package pt.ul.fc.css.example.demo.handlers;

import java.util.Optional;

import pt.ul.fc.css.example.demo.entities.*;
import pt.ul.fc.css.example.demo.repositories.*;
import pt.ul.fc.css.example.exceptions.NotPresentException;

public class LoginHandler {
    //TODO Ver como está a função do login do aluno e fazer parecido para o resto
    //TODO acho que o utilizador empresarial é que precisamos de checkar mais cenas
    // Repositório de alunos
    private AlunoRepository alunoRepository;

    // Repositório de utilizadores empresariais
    private UtilizadorEmpresarialRepository utilizadorEmpresarialRepository;

    private DocenteRepository docenteRepository;
    // Construtor
    public LoginHandler(AlunoRepository alunoRepository, UtilizadorEmpresarialRepository utilizadorEmpresarialRepository,
                        DocenteRepository docenteRepository) {
        this.alunoRepository = alunoRepository;
        this.utilizadorEmpresarialRepository = utilizadorEmpresarialRepository;
        this.docenteRepository = docenteRepository;
    }

    // login de aluno
    public Aluno alunoLogin(String contact, String password) throws NotPresentException {
        
        // Procurar aluno com o username fornecido
       Optional<Aluno> currentAluno = alunoRepository.findByContact(contact);

        // Verificar se o aluno existe
        if(currentAluno.isPresent()){
            return currentAluno.get();
        } else {
            throw new NotPresentException("Aluno não encontrado");
        }
    }

    // registo de utilizador empresarial
    public UtilizadorEmpresarial userEmpresarialRegister(String empresa, String name, String contact) {
        UtilizadorEmpresarial utilizadorEmpresarial = new UtilizadorEmpresarial(empresa, name, contact);
        utilizadorEmpresarialRepository.save(utilizadorEmpresarial);
        return utilizadorEmpresarial;
    }

    // login de utilizador empresarial
    public UtilizadorEmpresarial userEmpresarialLogin(String contact) {

        // Procurar utilizador empresarial com o username fornecido
        UtilizadorEmpresarial currentUtilizadorEmpresarial = utilizadorEmpresarialRepository.findByContact(contact);

        // Verificar se o utilizador empresarial existe
        if (currentUtilizadorEmpresarial!=null) {
            return currentUtilizadorEmpresarial;
        } else {
            return null;
        }
    }
}
package pt.ul.fc.css.example.demo.handlers;

import pt.ul.fc.css.example.demo.entities.*;
import pt.ul.fc.css.example.demo.repositories.*;

public class LoginHandler {

    // Repositório de alunos
    private AlunoRepository alunoRepository;

    // Repositório de utilizadores empresariais
    private UtilizadorEmpresarialRepository utilizadorEmpresarialRepository;

    // Construtor
    public LoginHandler(AlunoRepository alunoRepository, UtilizadorEmpresarialRepository utilizadorEmpresarialRepository) {
        this.alunoRepository = alunoRepository;
        this.utilizadorEmpresarialRepository = utilizadorEmpresarialRepository;
    }

    // login de aluno
    public Aluno alunoLogin(String contact, String password) {
        // Procurar aluno com o username fornecido
        Aluno currentAluno = alunoRepository.findByContact(contact);

        // Verificar se o aluno existe
        if (currentAluno!=null) {
            return currentAluno;
        } else {
            return null;
        }
    }

    // registo de utilizador empresarial
    public UtilizadorEmpresarial userEmpresarialRegister(String empresa, String name, String contact) {
        UtilizadorEmpresarial utilizadorEmpr = new UtilizadorEmpresarial(empresa, name, contact);
        utilizadorEmpresarialRepository.save(utilizadorEmpr);
        return utilizadorEmpr;
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
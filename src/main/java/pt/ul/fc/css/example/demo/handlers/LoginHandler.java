package pt.ul.fc.css.example.demo.handlers;

import java.util.Optional;

import pt.ul.fc.css.example.demo.entities.*;
import pt.ul.fc.css.example.demo.repositories.*;
import pt.ul.fc.css.example.demo.dtos.*;
import pt.ul.fc.css.example.exceptions.NotPresentException;

public class LoginHandler {

    private AlunoRepository alunoRepository;
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
    public Aluno loginAluno(String email) throws NotPresentException {
        // Procurar aluno com o username fornecido
        Optional<Aluno> optAluno = alunoRepository.findByEmail(email);
        // Verificar se o aluno existe
        if(currentAluno.isPresent()){
            Aluno aluno = optAluno.get();
            AlunoDTO alunoDTO = new Aluno
            return currentAluno.get();
        } else {
            throw new NotPresentException("Aluno não encontrado");
        }
    }

    // registo de utilizador empresarial
    public UtilizadorEmpresarial registerUtilizadorEmpresarial(String empresa, String name, String email) throws NotPresentException {
        Optional<UtilizadorEmpresarial> currentUtilizadorEmpresarial = utilizadorEmpresarialRepository.findByEmail(email);
        if(!currentUtilizadorEmpresarial.isEmpty()){
           throw new NotPresentException("Utilizador Empresarial já existe");
        }else{
            UtilizadorEmpresarial utilizadorEmpresarial = new UtilizadorEmpresarial(empresa, name, email);
            utilizadorEmpresarialRepository.save(utilizadorEmpresarial);
            return utilizadorEmpresarial;
        }
    }

    // login de utilizador empresarial
    public UtilizadorEmpresarial loginUserEmpresarial(String email) throws NotPresentException {
        // Procurar utilizador empresarial com o username fornecido
        Optional<UtilizadorEmpresarial> currentUtilizadorEmpresarial = utilizadorEmpresarialRepository.findByEmail(email);
        // Verificar se o utilizador empresarial existe
        if (currentUtilizadorEmpresarial.isPresent()) {
            return currentUtilizadorEmpresarial.get();
        } else {
            throw new NotPresentException("Utilizador Empresarial não encontrado");
        }
    }

    public Docente loginDocente(String email) throws NotPresentException {
        Optional<Docente> currentDocente = docenteRepository.findByEmail(email);
        if(currentDocente.isPresent()){
            return currentDocente.get();
        } else {
            throw new NotPresentException("Docente não encontrado");
        }
    }
}
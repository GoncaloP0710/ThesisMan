package pt.ul.fc.css.example.demo.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import pt.ul.fc.css.example.demo.entities.*;
import pt.ul.fc.css.example.demo.repositories.*;
import pt.ul.fc.css.example.demo.dtos.*;
import pt.ul.fc.css.example.exceptions.NotPresentException;

@Component
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
    public AlunoDTO loginAluno(String email) throws NotPresentException {
        // Procurar aluno com o username fornecido
        Optional<Aluno> optAluno = alunoRepository.findByEmail(email);
        // Verificar se o aluno existe
        if(optAluno.isPresent()){
            Aluno aluno = optAluno.get();
            AlunoDTO alunoDTO = new AlunoDTO(aluno.getName(), aluno.getEmail());
            return alunoDTO;
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
    public UtilizadorEmpresarialDTO loginUserEmpresarial(String email) throws NotPresentException {
        // Procurar utilizador empresarial com o username fornecido
        Optional<UtilizadorEmpresarial> currentUtilizadorEmpresarial = utilizadorEmpresarialRepository.findByEmail(email);
        // Verificar se o utilizador empresarial existe
        if (currentUtilizadorEmpresarial.isPresent()) {
            UtilizadorEmpresarial utilizadorEmpresarial = currentUtilizadorEmpresarial.get();
            UtilizadorEmpresarialDTO utilizadorEmpresarialDTO = new UtilizadorEmpresarialDTO(utilizadorEmpresarial.getEmpresa(), utilizadorEmpresarial.getEmail(), getTemasIds(utilizadorEmpresarial.getTemasPropostos()));
            return utilizadorEmpresarialDTO;
        } else {
            throw new NotPresentException("Utilizador Empresarial não encontrado");
        }
    }

    public DocenteDTO loginDocente(String email) throws NotPresentException {
        Optional<Docente> currentDocente = docenteRepository.findByEmail(email);
        if(currentDocente.isPresent()){
            Docente docente = currentDocente.get();
            DocenteDTO docenteDTO = new DocenteDTO(docente.getId(), docente.getName(), docente.getEmail(), docente.getDepartamento(), docente.isAdmnistrador(), getTemasIds(docente.getTemasPropostos()), getProjetosIds(docente.getProjects()));
            return docenteDTO;
        } else {
            throw new NotPresentException("Docente não encontrado");
        }
    }

    private List<Integer> getTemasIds(List<Tema> temas) {
        List<Integer> result = new ArrayList<Integer>();
        for (Tema t : temas) {
            result.add(t.getId());
        }
        return result;
    }

    private List<Integer> getProjetosIds(List<Projeto> projetos) {
        List<Integer> result = new ArrayList<Integer>();
        for (Projeto p : projetos) {
            result.add(p.getId());
        }
        return result;
    }
}
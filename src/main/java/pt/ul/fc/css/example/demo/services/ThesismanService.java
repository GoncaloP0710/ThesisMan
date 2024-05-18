package pt.ul.fc.css.example.demo.services;

import pt.ul.fc.css.example.demo.dtos.AlunoDTO;
import pt.ul.fc.css.example.demo.dtos.DocenteDTO;
import pt.ul.fc.css.example.demo.dtos.UtilizadorEmpresarialDTO;
import pt.ul.fc.css.example.demo.exceptions.NotPresentException;

public interface ThesismanService {
	
    public AlunoDTO loginAluno(String email, String password) throws NotPresentException;

    public DocenteDTO loginDocente(String email, String password) throws NotPresentException;

    public UtilizadorEmpresarialDTO registerUtilizadorEmpresarial(String empresa,String name, String email) throws NotPresentException;

    public UtilizadorEmpresarialDTO loginUtilizadorEmpresarial(String email, String password) throws NotPresentException;
}

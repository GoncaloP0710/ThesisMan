package pt.ul.fc.css.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ul.fc.css.example.demo.entities.Docente;
import pt.ul.fc.css.example.demo.entities.Mestrado;
import pt.ul.fc.css.example.demo.entities.Aluno;
import pt.ul.fc.css.example.demo.handlers.AtribuicaoTemaAdminHandler;
import pt.ul.fc.css.example.demo.handlers.CandidaturaHandler;
import pt.ul.fc.css.example.demo.handlers.ListarTemasAlunosHandler;
import pt.ul.fc.css.example.demo.handlers.LoginHandler;
import pt.ul.fc.css.example.demo.handlers.MarcacaoDefesaPropostaTeseHandler;
import pt.ul.fc.css.example.demo.handlers.RegistoNotaPropostaTeseHandler;
import pt.ul.fc.css.example.demo.handlers.SubmissaoDocPropostaTeseAlunoHandler;
import pt.ul.fc.css.example.demo.handlers.SubmissaoTemaDocenteHandler;
import pt.ul.fc.css.example.demo.handlers.SubmissaoTemaUtilizadorEmpresarialHandler;
import pt.ul.fc.css.example.demo.repositories.AlunoRepository;
import pt.ul.fc.css.example.demo.repositories.CandidaturaRepository;
import pt.ul.fc.css.example.demo.repositories.DocenteRepository;
import pt.ul.fc.css.example.demo.repositories.TemaRepository;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;
import pt.ul.fc.css.example.demo.repositories.UtilizadorEmpresarialRepository;
import pt.ul.fc.css.example.exceptions.NotPresentException;
import pt.ul.fc.css.example.demo.repositories.DefesaRepository;
import pt.ul.fc.css.example.demo.repositories.JuriRepository;
import pt.ul.fc.css.example.demo.repositories.MestradoRepository;

@Service
public class ThesismanService implements IThesismanService{

    @Autowired private AtribuicaoTemaAdminHandler atribuicaoTemaAdminHandler;
    @Autowired private CandidaturaHandler candidaturaHandler;
    @Autowired private ListarTemasAlunosHandler listarTemasAlunosHandler;
    @Autowired private LoginHandler loginHandler;
    @Autowired private MarcacaoDefesaPropostaTeseHandler marcacaoDefesaPropostaTeseHandler;
    @Autowired private RegistoNotaPropostaTeseHandler registoNotaPropostaTeseHandler;
    @Autowired private SubmissaoDocPropostaTeseAlunoHandler submissaoDocPropostaTeseAlunoHandler;
    @Autowired private SubmissaoTemaDocenteHandler submissaoTemaDocenteHandler;
    @Autowired private SubmissaoTemaUtilizadorEmpresarialHandler submissaoTemaUtilizadorEmpresarialHandler;

    @Autowired private AlunoRepository alunoRepository;
    @Autowired private CandidaturaRepository candidaturaRepository;
    @Autowired private TemaRepository temaRepository;
    @Autowired private TeseRepository teseRepository;
    @Autowired private UtilizadorEmpresarialRepository utilizadorEmpresarialRepository;
    @Autowired private DocenteRepository docenteRepository;
    @Autowired private DefesaRepository defesaRepository;
    @Autowired private MestradoRepository mestradoRepository;
    @Autowired private JuriRepository juriRepository;

    public void populate(){
        alunoRepository.deleteAll();
        candidaturaRepository.deleteAll();
        temaRepository.deleteAll();
        teseRepository.deleteAll();
        utilizadorEmpresarialRepository.deleteAll();
        docenteRepository.deleteAll();
        defesaRepository.deleteAll();
        mestradoRepository.deleteAll();
        juriRepository.deleteAll();

        Mestrado mestrado1 = new Mestrado("Engenharia Informática");
        Mestrado mestrado2 = new Mestrado("Engenharia Biomédica");
        mestradoRepository.save(mestrado1);
        mestradoRepository.save(mestrado2);

        Docente docente1 = new Docente("Informática", true, "Alcides", "mock1@email.com");
        Docente docente2 = new Docente("Informática", true, "Pedro","mock2@email.com");
        docenteRepository.save(docente1);
        docenteRepository.save(docente2);

        Aluno aluno1 = new Aluno(18.1, "Maria", "fc123@alunos.pt",mestrado1);
        Aluno aluno2 = new Aluno(12.5, "João","fc345@alunos.pt", mestrado2);
        alunoRepository.save(aluno1);
        alunoRepository.save(aluno2);
    
    }

    public void loginAluno(String email, String password) throws NotPresentException{
        loginHandler.loginAluno(email, password);
    }

    public void loginDocente(String email, String password) throws NotPresentException{
        loginHandler.loginDocente(email, password);
    }

    public void registerUtilizadorEmpresarial(String empresa, String email, String password) throws NotPresentException{
        loginHandler.registerUtilizadorEmpresarial(empresa, email, password);
    }

    public void loginUtilizadorEmpresarial(String email, String password) throws NotPresentException{
        loginHandler.loginUserEmpresarial(email);
    }

    public void submeterTemaDocente(String titulo, String descricao, float remuneracaoMensal, String email) throws NotPresentException{
        submissaoTemaDocenteHandler.submeterTema(titulo, descricao, remuneracaoMensal, email);
    }

    public void submeterTemaUtilizadorEmpresarial(String titulo, String descricao, float remuneracaoMensal, String email) throws NotPresentException{
        submissaoTemaUtilizadorEmpresarialHandler.submeterTema(titulo, descricao, remuneracaoMensal, email);
    }

    public void adicionarMestradoCompativel(String nome, String titulo, String email) throws NotPresentException{
        submissaoTemaUtilizadorEmpresarialHandler.adicionarMestradoCompativel(nome, titulo, email);
    }


    
    
}

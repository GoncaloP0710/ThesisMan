package pt.ul.fc.css.example.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ul.fc.css.example.demo.exceptions.*;
import pt.ul.fc.css.example.demo.dtos.*;
import pt.ul.fc.css.example.demo.entities.*;
import pt.ul.fc.css.example.demo.handlers.*;
import pt.ul.fc.css.example.demo.repositories.*;


@Service
public class ThesismanServiceImp implements ThesismanService{

    @Autowired private AtribuicaoTemaAdminHandler atribuicaoTemaAdminHandler;
    @Autowired private CandidaturaHandler candidaturaHandler;
    @Autowired private ListarTemasAlunosHandler listarTemasAlunosHandler;
    @Autowired private LoginHandler loginHandler;
    @Autowired private MestradoHandler mestradoHandler;
    // @Autowired private MarcacaoDefesaPropostaTeseHandler marcacaoDefesaPropostaTeseHandler;
    // @Autowired private RegistoNotaPropostaTeseHandler registoNotaPropostaTeseHandler;
    @Autowired private SubmissaoDocPropostaTeseAlunoHandler submissaoDocPropostaTeseAlunoHandler;
    @Autowired private SubmissaoDocFinalTeseAlunoHandler submissaoDocFinalTeseAlunoHandler;
    @Autowired private SubmissaoTemaDocenteHandler submissaoTemaDocenteHandler;
    // @Autowired private SubmissaoTemaUtilizadorEmpresarialHandler submissaoTemaUtilizadorEmpresarialHandler;
    // @Autowired private EstatisticasHandler estatisticasHandler;

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

        Docente docente1 = new Docente("Informática", true, "Alcides", "Alcides@mockdocente.pt");
        Docente docente2 = new Docente("Informática", true, "Pedro","Pedro@mockdocente.pt");
        docenteRepository.save(docente1);
        docenteRepository.save(docente2);

        Tema tema1 = new Tema("Tema1", "Descrição1", 1000, docente1);
        Tema tema2 = new Tema("Tema2", "Descrição2", 2000, docente2);
        temaRepository.save(tema1);
        temaRepository.save(tema2);
        docente1.addTemaPropostos(tema1);
        docente2.addTemaPropostos(tema2);
        docenteRepository.save(docente1);
        docenteRepository.save(docente2);

        Aluno aluno1 = new Aluno(18.1, "Maria", "fc123@alunos.pt",mestrado1);
        Aluno aluno2 = new Aluno(12.5, "João","fc345@alunos.pt", mestrado2);
        alunoRepository.save(aluno1);
        alunoRepository.save(aluno2);

        Candidatura candidatura1 = new Candidatura(new Date(), EstadoCandidatura.EMPROCESSAMENTO, aluno1, null);
        Candidatura candidatura2 = new Candidatura(new Date(), EstadoCandidatura.EMPROCESSAMENTO, aluno2, null);
        candidaturaRepository.save(candidatura1);
        candidaturaRepository.save(candidatura2);
    }

    public DocenteDTO loginDocente(String email, String password) throws NotPresentException{
        return loginHandler.loginDocente(email);
    }

    public UtilizadorEmpresarialDTO registerUtilizadorEmpresarial(String empresa,String name, String email) throws NotPresentException{
        return loginHandler.registerUtilizadorEmpresarial(empresa, name, email);
    }

    public UtilizadorEmpresarialDTO loginUtilizadorEmpresarial(String email, String password) throws NotPresentException{
        return loginHandler.loginUserEmpresarial(email);
    }

     public void submeterTemaDocente(String titulo, String descricao, float remuneracaoMensal, List<String> mestradosCompativeis, Integer docenteId) throws NotPresentException{
         submissaoTemaDocenteHandler.submeterTema(titulo, descricao, remuneracaoMensal, mestradosCompativeis, docenteId);
    }

    public List<Integer> getMestradosId(List<String> mestrados){
         return mestradoHandler.getMestradosId(mestrados);
    }

    public List<String> getMestrados(){
        return mestradoHandler.getMestrados();
    }

    public List<TemaDTO> getTemas(){
        return submissaoTemaDocenteHandler.getTemas();
    }

    public TemaDTO getTema(String titulo) throws NotPresentException{
        return submissaoTemaDocenteHandler.getTema(titulo);
    }

    public List<AlunoDTO> getAlunos(){
        return atribuicaoTemaAdminHandler.getAlunos();
    }

    public AlunoDTO getAluno(String nome) throws NotPresentException{
        return atribuicaoTemaAdminHandler.getAluno(nome);
    }

    public List<CandidaturaDTO> getCandidaturasWithTeses(){
        List<Candidatura> c = candidaturaHandler.getCandidaturas();
        List<CandidaturaDTO> result = new ArrayList<>();
        for (Candidatura candidatura : c) {
            if(candidatura.getTese() != null){
                result.add(new CandidaturaDTO(candidatura.getId(), candidatura.getTema().getId(), candidatura.getDataCandidatura(), candidatura.getEstado().name(), candidatura.getTese().getId(), candidatura.getAluno().getId()));
            }
        }
        return result;
    }



//     public void submeterTemaUtilizadorEmpresarial(Integer temaId, String descricao, float remuneracaoMensal, String email) throws NotPresentException{
//         submissaoTemaUtilizadorEmpresarialHandler.submeterTema(temaId, descricao, remuneracaoMensal, email);
//     }

//     public void adicionarMestradoCompativel(String nome, Integer temaId, String email) throws NotPresentException{
//         submissaoTemaUtilizadorEmpresarialHandler.adicionarMestradoCompativel(nome, temaId, email);
//     }

//     public void submitPropostaTeseDocsAluno(Integer candidaturaID, byte[] document, String emailAluno) throws NotPresentException{
//         submissaoDocPropostaTeseAlunoHandler.SubmitPropostaTeseDocsAluno(candidaturaID, document, emailAluno);
//     }

    public void atribuicaoTemaAdmin(Integer temaId, Integer alunoId, Integer docenteId) throws NotPresentException{
        atribuicaoTemaAdminHandler.atribuirTemaAdmin(temaId, alunoId, docenteId);
    }

//     // public void addTemaToCandidatura(String titulo, Integer candidaturaID) throws NotPresentException{
//     //     candidaturaHandler.addTemaToCandidatura(titulo, candidaturaID);
//     // }

//     public void addTeseToCandidatura(Integer teseID, Integer candidaturaID) throws NotPresentException{
//         candidaturaHandler.addTeseToCandidatura(teseID, candidaturaID);
//     }

//     public void marcarDefesPropostaTese(Integer teseID, String emailDocente, Integer arguenteID,Integer docenteOrientadorID, Boolean isPresencial, String sala, Integer duracao, Date data) throws NotPresentException{
//         marcacaoDefesaPropostaTeseHandler.marcarDefesaPropostaTese(teseID, emailDocente, arguenteID, docenteOrientadorID, isPresencial, sala, duracao, data);
//     }

//     public void registarNotaPropostaTese(Integer teseID, String emailDocente, float nota) throws NotPresentException{
//         registoNotaPropostaTeseHandler.registarNotaPropostaTese(teseID, emailDocente, nota);
//     }

//     public void getEstatisticas(){
//         estatisticasHandler.getEstatisticas();
//     }

// [][__][__]\__ [__][][][__][__]\__ [__][][][__][__]\__ [__][][][__][__]\__ [__][][][__][__]\__ [__][][][__][__]\__ [__][][][__][__]\__ [__][][][__][__]\__ [__][]
// [__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__]
// [][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][]
// [__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__]
// [][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][]
// [][__][__]\__ [__][][][__][__]\__ [__][][][__][__]\__ [__][][][__][__]\__ [__][][][__][__]\__ [__][][][__][__]\__ [__][][][__][__]\__ [__][][][__][__]\__ [__][]
// [__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__]
// [][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][]
// [__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__]
// [][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][]


    public AlunoDTO loginAluno(String email, String password) throws NotPresentException{
        return loginHandler.loginAluno(email);
    }

    public List<TemaDTO> listarTemasAlunos(Integer alunoId) throws NotPresentException{
        return listarTemasAlunosHandler.listarTemasAluno(alunoId);
    }

    public CandidaturaDTO newCandidatura(Date dataCandidatura, EstadoCandidatura estado, Integer alunoId, Integer temaId) throws IllegalCandidaturaException, NotPresentException{
        return candidaturaHandler.newCandidatura(dataCandidatura, estado, alunoId, temaId);
    }
    
    public void cancelCandidatura(Integer id) throws NotPresentException{
        candidaturaHandler.cancelCandidatura(id);
    }
    
    public TeseDTO submitPropostaTeseDocsAluno(Integer candidaturaID, byte[] document, Integer alunoId) throws NotPresentException{
        return submissaoDocPropostaTeseAlunoHandler.SubmitPropostaTeseDocsAluno(candidaturaID, document, alunoId);
    }

    public TeseDTO submeterDocFinalTeseAluno(Integer alunoId, Integer candidaturaID, byte[] document) throws NotPresentException{
        return submissaoDocFinalTeseAlunoHandler.submeterDocFinalTeseAluno(alunoId, candidaturaID, document);
    }

    public List<CandidaturaDTO> listarCandidaturasAlunos(Integer alunoId) throws NotPresentException{
        return candidaturaHandler.listarCandidaturasAluno(alunoId);
    }
}


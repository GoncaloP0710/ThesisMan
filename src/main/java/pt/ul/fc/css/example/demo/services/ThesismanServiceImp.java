package pt.ul.fc.css.example.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired private DocenteHandler docenteHandler;
    @Autowired private MarcacaoDefesaTeseHandler marcacaoDefesaTeseHandler;
    @Autowired private RegistoNotaPropostaTeseHandler registoNotaPropostaTeseHandler;
    @Autowired private RegistoNotaFinalHandler registoNotaFinalTeseHandler;
    @Autowired private SubmissaoDocPropostaTeseAlunoHandler submissaoDocPropostaTeseAlunoHandler;
    @Autowired private SubmissaoDocFinalTeseAlunoHandler submissaoDocFinalTeseAlunoHandler;
    @Autowired private SubmissaoTemaDocenteHandler submissaoTemaDocenteHandler;
    // @Autowired private SubmissaoTemaUtilizadorEmpresarialHandler submissaoTemaUtilizadorEmpresarialHandler;
    @Autowired private EstatisticasHandler estatisticasHandler;

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
        candidaturaRepository.deleteAll();
        temaRepository.deleteAll();
        teseRepository.deleteAll();
        defesaRepository.deleteAll();

        utilizadorEmpresarialRepository.deleteAll();
        docenteRepository.deleteAll();

        alunoRepository.deleteAll();
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

        Candidatura candidatura1 = new Candidatura(new Date(), EstadoCandidatura.APROVADO, aluno1, tema1);
        Candidatura candidatura2 = new Candidatura(new Date(), EstadoCandidatura.EMPROCESSAMENTO, aluno2, tema2);
        candidaturaRepository.save(candidatura1);
        candidaturaRepository.save(candidatura2);

        Tese tese1 = new Dissertacao(candidatura1);
        teseRepository.save(tese1);

        candidatura1.setTese(tese1);
        candidatura2.setTese(tese1);
        candidaturaRepository.save(candidatura1);
        candidaturaRepository.save(candidatura2);

        tema1.addMestradosCompativeis(mestrado1);
        tema2.addMestradosCompativeis(mestrado1);
        temaRepository.save(tema1);
        temaRepository.save(tema2);
        mestradoRepository.save(mestrado1);

        Defesa defesa1 = new Defesa(false, false);
        defesaRepository.save(defesa1);
        tese1.addDefesa(defesa1);
        System.out.println("Tese1 id: " + tese1.getId());
        System.out.println("Tese1 defezas size: " + tese1.getDefesas().size());
        teseRepository.save(tese1);
        defesaRepository.save(defesa1);
        Integer defesaTeseIdInteger = defesa1.getTeseId();
        System.out.println("Defesa1 tese id: " + defesaTeseIdInteger);

        System.out.println("=============================");

        System.out.println("=============== listarCandidaturasAlunosProposta =============");
        List<Candidatura> candidaturas = candidaturaRepository.findAllByEstado(EstadoCandidatura.APROVADO);
        List<Candidatura> candidaturasWithDefesaWithoutNota = new ArrayList<Candidatura>();
        for(Candidatura c : candidaturas){
            System.out.println("candidatura id: " + c.getId());
            System.out.println("candidatura aluno id: " + c.getAluno().getId());
            System.out.println("aluno id: " + 3);
            System.out.println("tese id: " + c.getTese().getId());
            System.out.println("candidatura tese defesas size : " + c.getTese().getDefesas().size());
            if(c.getTese().getDefesas().size() > 0 && c.getAluno().getId().equals(3)){
                System.out.println("candidatura tem pelo menos uma defesa e a candidatura é do aluno"); 
                for(Defesa d : c.getTese().getDefesas()){
                    System.out.println("defesa id: " + d.getId());
                    if(!d.isFinal() && d.getNota() == -1){
                        System.out.println("defesa não é final e não tem nota");
                        candidaturasWithDefesaWithoutNota.add(c);
                    }
                }
            }
        }
    }

    public List<DocenteDTO> getDocentes(){
        List<Docente> docentes = docenteHandler.getDocentes();
        List<DocenteDTO> result = new ArrayList<>();
        for (Docente docente : docentes) {
            List<Integer> temasPropostos = new ArrayList<>();
            for(Tema t : docente.getTemasPropostos()){
                temasPropostos.add(t.getId());
            }
            List<Integer> projetosId = new ArrayList<>();
            for(Projeto m : docente.getProjects()){
                projetosId.add(m.getId());
            }
            result.add(new DocenteDTO(docente.getId(),docente.getName(), docente.getEmail(), docente.getDepartamento(), docente.isAdmnistrador(), temasPropostos, projetosId));
        }
        return result;
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
    public TemaDTO getTema(Integer id) throws NotPresentException{
        return submissaoTemaDocenteHandler.getTema(id);
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

    public List<CandidaturaDTO> getCandidaturasAceites() throws NotPresentException{
        List<Candidatura> c = candidaturaHandler.getCandidaturasAceites();
        for (Candidatura candidatura : c) {
            System.out.println("Id da candidatura: " +  candidatura.getId());
        }
        List<CandidaturaDTO> result = new ArrayList<>();
        for (Candidatura candidatura : c) {
            if(candidatura.getTese() != null && candidatura.getTese().getDocumentProposto() != null){
                System.out.println("Id da candidatura: " +  candidatura.getId());
                result.add(new CandidaturaDTO(candidatura.getId(), candidatura.getTema().getId(), candidatura.getDataCandidatura(), candidatura.getEstado().name(), candidatura.getTese().getId(), candidatura.getAluno().getId()));
        }
    }
        return result;
        
    }

    public List<CandidaturaDTO> getCandidaturawithDefesaPropostaDone() throws NoProperStateException{
        List<Candidatura> c = candidaturaHandler.getCandidaturasWithDefesaPropostaDone();
        List<CandidaturaDTO> result = new ArrayList<>();
        for (Candidatura candidatura : c) {
            result.add(new CandidaturaDTO(candidatura.getId(), candidatura.getTema().getId(), candidatura.getDataCandidatura(), candidatura.getEstado().name(), candidatura.getTese().getId(), candidatura.getAluno().getId()));
        }
        return result;
    }

    public TeseDTO getTese(Integer id) throws NotPresentException{
        Tese t = candidaturaHandler.getTese(id);
        List<Integer> defesas = new ArrayList<>();
        for (Defesa d : t.getDefesas()) {
            defesas.add(d.getId());
        }
        return new TeseDTO(t.getId(), t.getCandidatura().getId(), t.getDocumentProposto(), t.getDocumentFinal(), defesas);
    }

    //teseId,model.getAttribute("id"),data, online, room, arguente
    public void marcarDefesaPropostaTese(Integer docenteId, Integer teseId, Date data, Boolean online, String room, Integer arguente) throws NotPresentException{
        Optional<Tese> t = teseRepository.findById(teseId);
        System.out.println("TeseID: " + teseId);
        if(t.isEmpty()){
            throw new NotPresentException("Tese não encontrada");
        }
        marcacaoDefesaTeseHandler.marcarDefesaDissertacaoPropostaTese(docenteId, teseId, data, online, room, arguente);
    }

    public void marcarDefesaFinalTese(Integer docenteId, Integer teseId, Date data, Boolean online, String room, Integer arguente, Integer presidente) throws NotPresentException, NoProperStateException{
        Optional<Tese> t = teseRepository.findById(teseId);
        if(t.isEmpty()){
            throw new NotPresentException("Tese não encontrada");
        }
        marcacaoDefesaTeseHandler.marcarDefesaFinalTese(docenteId, teseId, data, online, room, arguente, presidente);
    }

    public void atribuicaoTemaAdmin(Integer temaId, Integer alunoId, Integer docenteId) throws NotPresentException{
        atribuicaoTemaAdminHandler.atribuirTemaAdmin(temaId, alunoId, docenteId);
    }

    public void registarNotaPropostaTese(Integer teseID, Integer docenteId, Integer nota) throws NotPresentException{
        registoNotaPropostaTeseHandler.registarNotaPropostaTese(teseID, docenteId, nota);
    }

    public void registarNotaFinalTese(Integer teseID, Integer docenteId, Integer nota) throws NotPresentException{
        registoNotaFinalTeseHandler.registarNotaFinal(teseID, docenteId, nota);
    }

    public Map<CandidaturaDTO, TeseDTO> getCandidaturaWithTeseWithDefesaPropostaWithoutNota(){
        Map<CandidaturaDTO, TeseDTO> result = new HashMap<>();
        List<Candidatura> candidaturas = candidaturaRepository.findAll();
        for (Candidatura candidatura : candidaturas) {
            Tese tese = candidatura.getTese();
            if (tese != null) {
                Defesa d = tese.getDefesaProposta();
                if (d != null && d.getNota() != -1) {
                    CandidaturaDTO candidaturaDTO = new CandidaturaDTO(candidatura.getId(), candidatura.getTema().getId(), candidatura.getDataCandidatura(), candidatura.getEstado().name(), candidatura.getTese().getId(), candidatura.getAluno().getId());
                    TeseDTO teseDTO = new TeseDTO(tese.getId(), tese.getCandidatura().getId(), tese.getDocumentProposto(), tese.getDocumentFinal(), tese.getDefesas().stream().map(Defesa::getId).collect(Collectors.toList()));
                    result.put(candidaturaDTO, teseDTO);
                }
            }
        }
        return result;
    }

    public Map<CandidaturaDTO, TeseDTO> getCandidaturaWithTeseWithDefesaFinalWithoutNota(){
        Map<CandidaturaDTO, TeseDTO> result = new HashMap<>();
        List<Candidatura> candidaturas = candidaturaRepository.findAll();
        for (Candidatura candidatura : candidaturas) {
            Tese tese = candidatura.getTese();
            if (tese != null) {
                Defesa d = tese.getDefesaFinal();
                if (d != null && d.getNota() != -1) {
                    CandidaturaDTO candidaturaDTO = new CandidaturaDTO(candidatura.getId(), candidatura.getTema().getId(), candidatura.getDataCandidatura(), candidatura.getEstado().name(), candidatura.getTese().getId(), candidatura.getAluno().getId());
                    TeseDTO teseDTO = new TeseDTO(tese.getId(), tese.getCandidatura().getId(), tese.getDocumentProposto(), tese.getDocumentFinal(), tese.getDefesas().stream().map(Defesa::getId).collect(Collectors.toList()));
                    result.put(candidaturaDTO, teseDTO);
                }
            }
        }

        return result;
        }

    public String getEstatisticas(){
        return estatisticasHandler.getEstatisticas();
    }



//     public void submeterTemaUtilizadorEmpresarial(Integer temaId, String descricao, float remuneracaoMensal, String email) throws NotPresentException{
//         submissaoTemaUtilizadorEmpresarialHandler.submeterTema(temaId, descricao, remuneracaoMensal, email);
//     }

//     public void addTeseToCandidatura(Integer teseID, Integer candidaturaID) throws NotPresentException{
//         candidaturaHandler.addTeseToCandidatura(teseID, candidaturaID);
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

    public List<CandidaturaDTO> listarCandidaturasAlunosProposta(Integer alunoId) throws NotPresentException{
        return candidaturaHandler.listarCandidaturasAlunosProposta(alunoId);
    }

    public List<CandidaturaDTO> listarCandidaturasAlunosFinal(Integer alunoId) throws NotPresentException{
        return candidaturaHandler.listarCandidaturasAlunosFinal(alunoId);
    }

}
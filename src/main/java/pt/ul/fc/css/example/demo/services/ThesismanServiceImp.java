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
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.example.demo.dtos.*;
import pt.ul.fc.css.example.demo.entities.*;
import pt.ul.fc.css.example.demo.exceptions.*;
import pt.ul.fc.css.example.demo.handlers.*;
import pt.ul.fc.css.example.demo.repositories.*;

@Service
@Transactional
public class ThesismanServiceImp implements ThesismanService {

  @Autowired private AtribuicaoTemaAdminHandler atribuicaoTemaAdminHandler;
  @Autowired private CandidaturaHandler candidaturaHandler;
  @Autowired private ListarTemasAlunosHandler listarTemasAlunosHandler;
  @Autowired private LoginHandler loginHandler;
  @Autowired private MestradoHandler mestradoHandler;
  @Autowired private DocenteHandler docenteHandler;
  @Autowired private MarcacaoDefesaTeseHandler marcacaoDefesaTeseHandler;
  @Autowired private RegistoNotaHandler registoNotaFinalTeseHandler;
  @Autowired private SubmissaoDocPropostaTeseAlunoHandler submissaoDocPropostaTeseAlunoHandler;
  @Autowired private SubmissaoDocFinalTeseAlunoHandler submissaoDocFinalTeseAlunoHandler;
  @Autowired private SubmissaoTemaDocenteHandler submissaoTemaDocenteHandler;
  // @Autowired private SubmissaoTemaUtilizadorEmpresarialHandler
  // submissaoTemaUtilizadorEmpresarialHandler;
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

  public void populate() {
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
    Docente docente2 = new Docente("Informática", true, "Pedro", "Pedro@mockdocente.pt");
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

    Aluno aluno1 = new Aluno(18.1, "Maria", "fc123@alunos.pt", mestrado1);
    Aluno aluno2 = new Aluno(12.5, "João", "fc345@alunos.pt", mestrado2);
    alunoRepository.save(aluno1);
    alunoRepository.save(aluno2);

    Candidatura candidatura1 =
        new Candidatura(new Date(), EstadoCandidatura.EMPROCESSAMENTO, aluno1, tema1);
    Candidatura candidatura2 =
        new Candidatura(new Date(), EstadoCandidatura.EMPROCESSAMENTO, aluno2, tema2);
    Candidatura candidatura3 =
        new Candidatura(new Date(), EstadoCandidatura.EMPROCESSAMENTO, aluno1, tema2);
    candidaturaRepository.save(candidatura1);
    candidaturaRepository.save(candidatura2);
    candidaturaRepository.save(candidatura3);

    // Tese tese1 = new Dissertacao(candidatura1);
    // Tese tese2 = new Dissertacao(candidatura3);
    // teseRepository.save(tese1);
    // teseRepository.save(tese2);

    // candidatura1.setTese(tese1);
    // candidatura2.setTese(tese1);
    // candidatura3.setTese(tese2);
    // candidaturaRepository.save(candidatura1);
    // candidaturaRepository.save(candidatura2);
    // candidaturaRepository.save(candidatura3);

    tema1.addMestradosCompativeis(mestrado1);
    tema2.addMestradosCompativeis(mestrado1);
    temaRepository.save(tema1);
    temaRepository.save(tema2);
    mestradoRepository.save(mestrado1);

    // Defesa defesa1 = new Defesa(false, false);
    // Defesa defesa2 = new Defesa(true, false);
    // defesaRepository.save(defesa1);
    // defesaRepository.save(defesa2);

    // tese1.addDefesa(defesa1);
    // tese2.addDefesa(defesa2);

    // teseRepository.save(tese1);
    // teseRepository.save(tese2);
    // defesaRepository.save(defesa1);
    // defesaRepository.save(defesa2);
    // Integer defesaTeseIdInteger = defesa1.getTeseId();
    // Integer defesaTeseIdInteger2 = defesa2.getTeseId();

    // List<Candidatura> candidaturas = candidaturaRepository.findAllByEstado(EstadoCandidatura.APROVADO);
    // List<Candidatura> candidaturasWithDefesaWithoutNota = new ArrayList<Candidatura>();
  }

  public List<DocenteDTO> getDocentes() {
    List<Docente> docentes = docenteHandler.getDocentes();
    List<DocenteDTO> result = new ArrayList<>();
    for (Docente docente : docentes) {
      List<Integer> temasPropostos = new ArrayList<>();
      for (Tema t : docente.getTemasPropostos()) {
        temasPropostos.add(t.getId());
      }
      List<Integer> projetosId = new ArrayList<>();
      for (Projeto m : docente.getProjects()) {
        projetosId.add(m.getId());
      }
      result.add(
          new DocenteDTO(
              docente.getId(),
              docente.getName(),
              docente.getEmail(),
              docente.getDepartamento(),
              docente.isAdmnistrador(),
              temasPropostos,
              projetosId));
    }
    return result;
  }

  public DocenteDTO loginDocente(String email, String password) throws NotPresentException {
    return loginHandler.loginDocente(email);
  }

  public UtilizadorEmpresarialDTO registerUtilizadorEmpresarial(
      String empresa, String name, String email) throws NotPresentException {
    return loginHandler.registerUtilizadorEmpresarial(empresa, name, email);
  }

  public UtilizadorEmpresarialDTO loginUtilizadorEmpresarial(String email, String password)
      throws NotPresentException {
    return loginHandler.loginUserEmpresarial(email);
  }

  public void submeterTemaDocente(
      String titulo,
      String descricao,
      float remuneracaoMensal,
      List<String> mestradosCompativeis,
      Integer docenteId)
      throws NotPresentException {
    submissaoTemaDocenteHandler.submeterTema(
        titulo, descricao, remuneracaoMensal, mestradosCompativeis, docenteId);
  }

  public List<Integer> getMestradosId(List<String> mestrados) {
    return mestradoHandler.getMestradosId(mestrados);
  }

  public List<String> getMestrados() {
    return mestradoHandler.getMestrados();
  }

  public List<TemaDTO> getTemas() {
    return submissaoTemaDocenteHandler.getTemas();
  }

  public TemaDTO getTema(String titulo) throws NotPresentException {
    return submissaoTemaDocenteHandler.getTema(titulo);
  }

  public TemaDTO getTema(Integer id) throws NotPresentException {
    return submissaoTemaDocenteHandler.getTema(id);
  }

  public List<AlunoDTO> getAlunos() {
    return atribuicaoTemaAdminHandler.getAlunos();
  }

  public AlunoDTO getAluno(String nome) throws NotPresentException {
    return atribuicaoTemaAdminHandler.getAluno(nome);
  }

  public List<CandidaturaDTO> getCandidaturasWithTeses() {
    List<Candidatura> c = candidaturaHandler.getCandidaturas();
    List<CandidaturaDTO> result = new ArrayList<>();
    for (Candidatura candidatura : c) {
      if (candidatura.getTese() != null) {
        result.add(
            new CandidaturaDTO(
                candidatura.getId(),
                candidatura.getTema().getId(),
                candidatura.getDataCandidatura(),
                candidatura.getEstado().name(),
                candidatura.getTese().getId(),
                candidatura.getAluno().getId()));
      }
    }
    return result;
  }

  public List<CandidaturaDTO> getCandidaturasAceites() throws NotPresentException {
    List<Candidatura> c = candidaturaHandler.getCandidaturasAceites();
    System.out.println("Size do c: " + c.size());
    for (Candidatura candidatura : c) {
      System.out.println("Id da candidatura: " + candidatura.getId());
    }
    List<CandidaturaDTO> result = new ArrayList<>();
    for (Candidatura candidatura : c) {
      if (candidatura.getTese() != null && candidatura.getEstado() == EstadoCandidatura.APROVADO) {
        System.out.println("Id da candidatura: " + candidatura.getId());
        result.add(
            new CandidaturaDTO(
                candidatura.getId(),
                candidatura.getTema().getId(),
                candidatura.getDataCandidatura(),
                candidatura.getEstado().name(),
                candidatura.getTese().getId(),
                candidatura.getAluno().getId()));
      }
    }
    System.out.println("Size result candidaturas aceites: " + result.size());
    return result;
  }

  public List<CandidaturaDTO> getCandidaturawithDefesaPropostaDone() throws NoProperStateException {
    List<Candidatura> c = candidaturaHandler.getCandidaturasWithDefesaPropostaDone();
    List<CandidaturaDTO> result = new ArrayList<>();
    for (Candidatura candidatura : c) {
      result.add(
          new CandidaturaDTO(
              candidatura.getId(),
              candidatura.getTema().getId(),
              candidatura.getDataCandidatura(),
              candidatura.getEstado().name(),
              candidatura.getTese().getId(),
              candidatura.getAluno().getId()));
    }
    return result;
  }

  public TeseDTO getTese(Integer id) throws NotPresentException {
    Tese t = candidaturaHandler.getTese(id);
    List<Integer> defesas = new ArrayList<>();
    for (Defesa d : t.getDefesas()) {
      defesas.add(d.getId());
    }
    return new TeseDTO(
        t.getId(),
        t.getCandidatura().getId(),
        t.getDocumentProposto(),
        t.getDocumentFinal(),
        defesas);
  }

  // teseId,model.getAttribute("id"),data, online, room, arguente
  public void marcarDefesaPropostaTese(
      Integer docenteId, Integer teseId, Date data, Boolean online, String room, Integer arguente)
      throws NotPresentException, IllegalArgumentException {
    Optional<Tese> t = teseRepository.findById(teseId);
    System.out.println("TeseID: " + teseId);
    if (t.isEmpty()) {
      throw new NotPresentException("Tese não encontrada");
    }
    marcacaoDefesaTeseHandler.marcarDefesaDissertacaoPropostaTese(
        docenteId, teseId, data, online, room, arguente);
  }

  public void marcarDefesaFinalTese(
      Integer docenteId,
      Integer teseId,
      Date data,
      Boolean online,
      String room,
      Integer arguente,
      Integer presidente)
      throws NotPresentException, NoProperStateException {
    Optional<Tese> t = teseRepository.findById(teseId);
    if (t.isEmpty()) {
      throw new NotPresentException("Tese não encontrada");
    }
    marcacaoDefesaTeseHandler.marcarDefesaFinalTese(
        docenteId, teseId, data, online, room, arguente, presidente);
  }

  public void atribuicaoTemaAdmin(Integer temaId, Integer alunoId, Integer docenteId)
      throws NotPresentException, PermissionDeniedException, ThemeAttributedException {
    atribuicaoTemaAdminHandler.atribuirTemaAdmin(temaId, alunoId, docenteId);
  }


  public Map<CandidaturaDTO, TeseDTO> getCandidaturaWithTeseWithDefesaPropostaWithoutNota() {
    Map<CandidaturaDTO, TeseDTO> result = new HashMap<>();
    List<Candidatura> candidaturas = candidaturaRepository.findAll();
    for (Candidatura candidatura : candidaturas) {
      Tese tese = candidatura.getTese();
      if (tese != null) {
        Defesa d = tese.getDefesaProposta();
        if (d != null && d.getNota() == -1) {
          CandidaturaDTO candidaturaDTO =
              new CandidaturaDTO(
                  candidatura.getId(),
                  candidatura.getTema().getId(),
                  candidatura.getDataCandidatura(),
                  candidatura.getEstado().name(),
                  candidatura.getTese().getId(),
                  candidatura.getAluno().getId());
          TeseDTO teseDTO =
              new TeseDTO(
                  tese.getId(),
                  tese.getCandidatura().getId(),
                  tese.getDocumentProposto(),
                  tese.getDocumentFinal(),
                  tese.getDefesas().stream().map(Defesa::getId).collect(Collectors.toList()));
          result.put(candidaturaDTO, teseDTO);
        }
      }
    }
    return result;
  }

  public List<Integer> gethDefesaPropostaWithoutNota() {
    Map<CandidaturaDTO, TeseDTO> result = new HashMap<>();
    List<Candidatura> candidaturas = candidaturaRepository.findAll();
    List<Integer> resultIds = new ArrayList<>();
    for (Candidatura candidatura : candidaturas) {
      Tese tese = candidatura.getTese();
      if (tese != null) {
        Defesa d = tese.getDefesaProposta();
        if (d != null && d.getNota() == -1) {
          CandidaturaDTO candidaturaDTO =
              new CandidaturaDTO(
                  candidatura.getId(),
                  candidatura.getTema().getId(),
                  candidatura.getDataCandidatura(),
                  candidatura.getEstado().name(),
                  candidatura.getTese().getId(),
                  candidatura.getAluno().getId());
          TeseDTO teseDTO =
              new TeseDTO(
                  tese.getId(),
                  tese.getCandidatura().getId(),
                  tese.getDocumentProposto(),
                  tese.getDocumentFinal(),
                  tese.getDefesas().stream().map(Defesa::getId).collect(Collectors.toList()));
          result.put(candidaturaDTO, teseDTO);
          resultIds.add(d.getId());
        }
      }
    }
    return resultIds;
  }

  public Map<CandidaturaDTO, TeseDTO> getCandidaturaWithTeseWithDefesaFinalWithoutNota() {
    Map<CandidaturaDTO, TeseDTO> result = new HashMap<>();
    List<Candidatura> candidaturas = candidaturaRepository.findAll();
    for (Candidatura candidatura : candidaturas) {
      Tese tese = candidatura.getTese();
      if (tese != null) {
        Defesa d = tese.getDefesaFinal();
        if (d != null && d.getNota() == -1) {
          CandidaturaDTO candidaturaDTO =
              new CandidaturaDTO(
                  candidatura.getId(),
                  candidatura.getTema().getId(),
                  candidatura.getDataCandidatura(),
                  candidatura.getEstado().name(),
                  candidatura.getTese().getId(),
                  candidatura.getAluno().getId());
          TeseDTO teseDTO =
              new TeseDTO(
                  tese.getId(),
                  tese.getCandidatura().getId(),
                  tese.getDocumentProposto(),
                  tese.getDocumentFinal(),
                  tese.getDefesas().stream().map(Defesa::getId).collect(Collectors.toList()));
          result.put(candidaturaDTO, teseDTO);
        }
      }
    }

    return result;
  }

  public List<Integer> gethDefesaFinalWithoutNota() {
    Map<CandidaturaDTO, TeseDTO> result = new HashMap<>();
    List<Candidatura> candidaturas = candidaturaRepository.findAll();
    List<Integer> resultIds = new ArrayList<>();
    for (Candidatura candidatura : candidaturas) {
      Tese tese = candidatura.getTese();
      if (tese != null) {
        Defesa d = tese.getDefesaFinal();
        if (d != null && d.getNota() == -1) {
          CandidaturaDTO candidaturaDTO =
              new CandidaturaDTO(
                  candidatura.getId(),
                  candidatura.getTema().getId(),
                  candidatura.getDataCandidatura(),
                  candidatura.getEstado().name(),
                  candidatura.getTese().getId(),
                  candidatura.getAluno().getId());
          TeseDTO teseDTO =
              new TeseDTO(
                  tese.getId(),
                  tese.getCandidatura().getId(),
                  tese.getDocumentProposto(),
                  tese.getDocumentFinal(),
                  tese.getDefesas().stream().map(Defesa::getId).collect(Collectors.toList()));
          result.put(candidaturaDTO, teseDTO);
          resultIds.add(d.getId());
        }
      }
    }

    return resultIds;
  }

  public String getEstatisticas() {
    return estatisticasHandler.getEstatisticas();
  }

  public CandidaturaDTO updateCandidaturaStatus(Integer id, String estado)
      throws NotPresentException {
    return candidaturaHandler.updateCandidaturaStatus(id, estado);
  }

  public List<CandidaturaDTO> getCandidaturas() throws NotPresentException {
    List<Candidatura> c = candidaturaHandler.getCandidaturas();
    List<CandidaturaDTO> result = new ArrayList<>();
    for (Candidatura candidatura : c) {
      Integer teseId = null;
      Integer temaId = null;
      if (candidatura.getTese() != null) {
        teseId = candidatura.getTese().getId();
      }
      if (candidatura.getTema() != null) {
        temaId = candidatura.getTema().getId();
      }
      result.add(
          new CandidaturaDTO(
              candidatura.getId(),
              temaId,
              candidatura.getDataCandidatura(),
              candidatura.getEstado().name(),
              teseId,
              candidatura.getAluno().getId()));
    }
    return result;
  }

  // [][__][__]\__ [__][][][__][__]\__ [__][][][__][__]\__ [__][][][__][__]\__ [__][][][__][__]\__
  // [__][][][__][__]\__ [__][][][__][__]\__ [__][][][__][__]\__ [__][]
  // [__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__]
  // [][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][]
  // [__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__]
  // [][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][]
  // [][__][__]\__ [__][][][__][__]\__ [__][][][__][__]\__ [__][][][__][__]\__ [__][][][__][__]\__
  // [__][][][__][__]\__ [__][][][__][__]\__ [__][][][__][__]\__ [__][]
  // [__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__]
  // [][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][]
  // [__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__][__]
  // [][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][][][__][__][__][__][]

  public AlunoDTO loginAluno(String email, String password) throws NotPresentException {
    return loginHandler.loginAluno(email);
  }

  public List<TemaDTO> listarTemasAlunos(Integer alunoId) throws NotPresentException {
    return listarTemasAlunosHandler.listarTemasAluno(alunoId);
  }

  public CandidaturaDTO newCandidatura(
      Date dataCandidatura, EstadoCandidatura estado, Integer alunoId, Integer temaId)
      throws IllegalCandidaturaException, NotPresentException {
    return candidaturaHandler.newCandidatura(dataCandidatura, estado, alunoId, temaId);
  }

  public void cancelCandidatura(Integer id) throws NotPresentException {
    candidaturaHandler.cancelCandidatura(id);
  }

  public TeseDTO submitPropostaTeseDocsAluno(
      Integer candidaturaID, byte[] document, Integer alunoId) throws NotPresentException {
    return submissaoDocPropostaTeseAlunoHandler.SubmitPropostaTeseDocsAluno(
        candidaturaID, document, alunoId);
  }

  public TeseDTO submeterDocFinalTeseAluno(Integer alunoId, Integer candidaturaID, byte[] document)
      throws NotPresentException {
    return submissaoDocFinalTeseAlunoHandler.submeterDocFinalTeseAluno(
        alunoId, candidaturaID, document);
  }

  public List<CandidaturaDTO> listarCandidaturasAlunos(Integer alunoId) throws NotPresentException {
    return candidaturaHandler.listarCandidaturasAluno(alunoId);
  }

  public List<CandidaturaDTO> listarCandidaturasAlunosProposta(Integer alunoId)
      throws NotPresentException {
    return candidaturaHandler.listarCandidaturasAlunosProposta(alunoId);
  }

  public List<CandidaturaDTO> listarCandidaturasAlunosFinal(Integer alunoId)
      throws NotPresentException {
    return candidaturaHandler.listarCandidaturasAlunosFinal(alunoId);
  }

  public void registarNotaDefesa(Integer defesaId, Integer nota) throws NotPresentException {
    registoNotaFinalTeseHandler.registarNota(defesaId, nota);
  }

  public List<String> atribuirTemaAuto() throws NotPresentException {
    return atribuicaoTemaAdminHandler.atribuirTemaAuto();
  }
}

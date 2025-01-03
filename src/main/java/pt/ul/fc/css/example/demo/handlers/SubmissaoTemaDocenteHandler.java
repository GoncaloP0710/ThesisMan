package pt.ul.fc.css.example.demo.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.example.demo.dtos.DocenteDTO;
import pt.ul.fc.css.example.demo.dtos.TemaDTO;
import pt.ul.fc.css.example.demo.entities.Docente;
import pt.ul.fc.css.example.demo.entities.Mestrado;
import pt.ul.fc.css.example.demo.entities.Projeto;
import pt.ul.fc.css.example.demo.entities.Tema;
import pt.ul.fc.css.example.demo.exceptions.NotPresentException;
import pt.ul.fc.css.example.demo.repositories.DocenteRepository;
import pt.ul.fc.css.example.demo.repositories.MestradoRepository;
import pt.ul.fc.css.example.demo.repositories.TemaRepository;

@Component
@Transactional
public class SubmissaoTemaDocenteHandler {
  private TemaRepository temaRepository;
  private DocenteRepository docenteRepository;
  private MestradoRepository mestradoRepository;

  public SubmissaoTemaDocenteHandler(
      TemaRepository temaRepository,
      DocenteRepository docenteRepository,
      MestradoRepository mestradoRepository) {
    this.temaRepository = temaRepository;
    this.docenteRepository = docenteRepository;
    this.mestradoRepository = mestradoRepository;
  }

  public DocenteDTO submeterTema(
      String titulo,
      String descricao,
      float remuneracaoMensal,
      List<String> mestradosCompativeis,
      Integer docenteId)
      throws NotPresentException {
    Optional<Docente> optDocente = docenteRepository.findById(docenteId);
    Docente docente = optDocente.get();
    Optional<Tema> optTema = temaRepository.findByTitulo(titulo);
    if (!optTema.isEmpty()) {
      throw new IllegalArgumentException("Tema já existe");
    }

    List<Mestrado> mestrados = mestradoRepository.findByNomeIn(mestradosCompativeis);
    Tema tema = new Tema(titulo, descricao, remuneracaoMensal, docente, mestrados);
    temaRepository.save(tema);

    docente.addTemaPropostos(tema);
    docenteRepository.save(docente);
    // Integer id, String nome, String contacto, String departamento, Boolean isAdministrador,
    // List<Integer> temasPropostos, List<Integer> projetosId
    List<Integer> temasPropostos = new ArrayList<Integer>();
    for (Tema t : docente.getTemasPropostos()) {
      temasPropostos.add(t.getId());
    }
    List<Integer> projetosId = new ArrayList<Integer>();
    for (Projeto m : docente.getProjects()) {
      projetosId.add(m.getId());
    }
    return new DocenteDTO(
        docente.getId(),
        docente.getName(),
        docente.getEmail(),
        docente.getDepartamento(),
        docente.isAdmnistrador(),
        temasPropostos,
        projetosId);
  }

  public void adicionarMestradoCompatível(String nome, Integer temaId, String email)
      throws NotPresentException {
    if (docenteRepository.findByEmail(email).isEmpty()) {
      throw new NotPresentException("Docente não encontrado");
    }
    Optional<Tema> optTema = temaRepository.findById(temaId);
    if (optTema.isEmpty()) {
      throw new NotPresentException("Tema não encontrado");
    }
    Tema tema = optTema.get();
    if (!tema.getSubmissor().getEmail().equals(email)) {
      throw new IllegalArgumentException("Docente não é o submissor do tema");
    }
    Optional<Mestrado> optMestrado = mestradoRepository.findByNome(nome);
    if (optMestrado.isEmpty()) {
      throw new NotPresentException("Mestrado não encontrado");
    }
    Mestrado mestrado = optMestrado.get();
    tema.addMestradosCompativeis(mestrado);
    temaRepository.save(tema);
  }

  public List<TemaDTO> getTemas() {
    List<Tema> temas = temaRepository.findAll();
    List<TemaDTO> result = new ArrayList<>();
    for (Tema t : temas) {
      List<Integer> mestradosIds = new ArrayList<>();
      for (Mestrado m : t.getMestrados()) {
        mestradosIds.add(m.getId());
      }
      result.add(
          new TemaDTO(
              t.getId(),
              t.getTitulo(),
              t.getDescricao(),
              t.getRemuneracaoMensal(),
              t.getSubmissor().getId(),
              mestradosIds));
    }
    return result;
  }

  public TemaDTO getTema(String titulo) throws NotPresentException {
    Optional<Tema> optTema = temaRepository.findByTitulo(titulo);
    if (optTema.isEmpty()) {
      throw new NotPresentException("Tema não encontrado");
    }
    Tema tema = optTema.get();
    List<Integer> mestradosIds = new ArrayList<>();
    for (Mestrado m : tema.getMestrados()) {
      mestradosIds.add(m.getId());
    }
    return new TemaDTO(
        tema.getId(),
        tema.getTitulo(),
        tema.getDescricao(),
        tema.getRemuneracaoMensal(),
        tema.getSubmissor().getId(),
        mestradosIds);
  }

  public TemaDTO getTema(Integer id) throws NotPresentException {
    Optional<Tema> optTema = temaRepository.findById(id);
    if (optTema.isEmpty()) {
      throw new NotPresentException("Tema não encontrado");
    }
    Tema tema = optTema.get();
    List<Integer> mestradosIds = new ArrayList<>();
    for (Mestrado m : tema.getMestrados()) {
      mestradosIds.add(m.getId());
    }
    return new TemaDTO(
        tema.getId(),
        tema.getTitulo(),
        tema.getDescricao(),
        tema.getRemuneracaoMensal(),
        tema.getSubmissor().getId(),
        mestradosIds);
  }
}

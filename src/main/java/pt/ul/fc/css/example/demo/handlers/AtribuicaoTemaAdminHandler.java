package pt.ul.fc.css.example.demo.handlers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.naming.NoPermissionException;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.example.demo.dtos.AlunoDTO;
import pt.ul.fc.css.example.demo.entities.Aluno;
import pt.ul.fc.css.example.demo.entities.Candidatura;
import pt.ul.fc.css.example.demo.entities.Dissertacao;
import pt.ul.fc.css.example.demo.entities.Docente;
import pt.ul.fc.css.example.demo.entities.EstadoCandidatura;
import pt.ul.fc.css.example.demo.entities.Projeto;
import pt.ul.fc.css.example.demo.entities.Tema;
import pt.ul.fc.css.example.demo.exceptions.NotPresentException;
import pt.ul.fc.css.example.demo.exceptions.PermissionDeniedException;
import pt.ul.fc.css.example.demo.exceptions.ThemeAttributedException;
import pt.ul.fc.css.example.demo.repositories.AlunoRepository;
import pt.ul.fc.css.example.demo.repositories.CandidaturaRepository;
import pt.ul.fc.css.example.demo.repositories.DocenteRepository;
import pt.ul.fc.css.example.demo.repositories.TemaRepository;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;

@Component
@Transactional
public class AtribuicaoTemaAdminHandler {
  private TemaRepository temaRepository;
  private DocenteRepository docenteRepository;
  private CandidaturaRepository candidaturaRepository;
  private AlunoRepository alunoRepository;
  private TeseRepository teseRepository;

  public AtribuicaoTemaAdminHandler(
      TemaRepository temaRepository,
      DocenteRepository docenteRepository,
      AlunoRepository alunoRepository,
      CandidaturaRepository candidaturaRepository,
      TeseRepository teseRepository) {
    this.temaRepository = temaRepository;
    this.docenteRepository = docenteRepository;
    this.candidaturaRepository = candidaturaRepository;
    this.alunoRepository = alunoRepository;
    this.teseRepository = teseRepository;
  }

  public void atribuirTemaAdmin(Integer temaId, Integer alunoId, Integer docenteId)
      throws NotPresentException, PermissionDeniedException, ThemeAttributedException {
    Optional<Docente> optDocente = docenteRepository.findById(docenteId);
    if (optDocente.isEmpty()) {
      throw new NotPresentException("Docente não encontrado");
    }
    Docente docente = optDocente.get();
    if (!docente.isAdmnistrador()) {
      throw new PermissionDeniedException("Docente não tem permissões para atribuir temas");
    }
    Optional<Aluno> optAluno = alunoRepository.findById(alunoId);
    if (optAluno.isEmpty()) {
      throw new NotPresentException("Aluno não encontrado");
    }
    List<Candidatura> candidaturas = candidaturaRepository.findAllByAlunoId(alunoId);
    if (candidaturas.isEmpty()) {
      throw new IllegalArgumentException("Aluno não tem candidaturas");
    }
    Optional<Candidatura> optCandidatura =
        candidaturas.stream().filter(candidatura -> candidatura.getTema() == null).findFirst();
    if (optCandidatura.isEmpty()) {
      throw new ThemeAttributedException("Aluno já tem um tema atribuído");
    }
    Optional<Tema> optTema = temaRepository.findById(temaId);
    if (optTema.isEmpty()) {
      throw new NotPresentException("Tema não encontrado");
    }
    Tema tema = optTema.get();
    Candidatura candidatura = optCandidatura.get();
    candidatura.setTema(tema);

    // *Set Tese da candidatura
    if (tema.getSubmissor() instanceof Docente) {
      Dissertacao tese = new Dissertacao(candidatura);
      teseRepository.save(tese);

      candidatura.setTese(tese);
      candidaturaRepository.save(candidatura);
    } else {
      Projeto tese = new Projeto(candidatura);
      teseRepository.save(tese);

      candidatura.setTese(tese);
      candidaturaRepository.save(candidatura);
    }
  }

  public List<AlunoDTO> getAlunos() {
    List<Aluno> alunos = alunoRepository.findAll();
    List<AlunoDTO> result = new ArrayList<>();
    List<Integer> candidaturasIds = new ArrayList<>();
    for (Aluno a : alunos) {
      for (Candidatura c : a.getCandidatura()) {
        candidaturasIds.add(c.getId());
      }
      result.add(
          new AlunoDTO(
              a.getId(),
              a.getName(),
              a.getEmail(),
              a.getAverage(),
              a.getMestrado().getId(),
              candidaturasIds));
    }
    return result;
  }

  public AlunoDTO getAluno(String name) throws NotPresentException {
    Optional<Aluno> a = alunoRepository.findByNome(name);
    if (a.isEmpty()) {
      throw new NotPresentException("Aluno não encontrado");
    }
    Aluno aluno = a.get();
    List<Integer> candidaturasIds = new ArrayList<>();
    for (Candidatura c : aluno.getCandidatura()) {
      candidaturasIds.add(c.getId());
    }
    return new AlunoDTO(
        aluno.getId(),
        aluno.getName(),
        aluno.getEmail(),
        aluno.getAverage(),
        aluno.getMestrado().getId(),
        candidaturasIds);
  }

  /**
   * Atribui temas automaticamente aos alunos com base na média
   * 
   * @return Lista de strings com informação dos temas atribuídos
   * @throws NotPresentException
   */
  public List<String> atribuirTemaAuto() throws NotPresentException {
    // Listar alunos pela media
    List<Aluno> alunos = alunoRepository.findAll();
    Collections.sort(alunos, (a1, a2) -> a1.getAverage().compareTo(a2.getAverage()));
    List<Integer> temasAprovadasIds = new ArrayList<>();
    List<String> output = new ArrayList<>();
    // Percorre os alunos
    for (Aluno a : alunos) {
      System.out.println("Aluno " + a.getName() + " com média " + a.getAverage());
      List<Candidatura> candidaturas = candidaturaRepository.findAllByAlunoId(a.getId());
      // Percorre as candidaturas do aluno em questão
      for (Candidatura c : candidaturas) {
        System.out.println("Candidatura " + c.getId() + " com estado " + c.getEstado());
        Calendar cal = Calendar.getInstance();
        cal.setTime(c.getDataCandidatura());
        int yearOfCandidatura = cal.get(Calendar.YEAR);
        // Se a candidatura estiver em processamento
        if (c != null && c.getTema() != null && c.getEstado() == EstadoCandidatura.EMPROCESSAMENTO) {
          boolean livre = true;
          // Verifica se o tema já foi atribuído
          for (Candidatura candidatura : candidaturaRepository.findAll()) {
            if (candidatura != null){
              Calendar cal2 = Calendar.getInstance();
              cal2.setTime(candidatura.getDataCandidatura());
              int yearOfCandidatura2 = cal2.get(Calendar.YEAR);
              // Verifica se ambas as candidaturas são do mesmo ano
              // Se o tema já tiver sido atribuído a um aluno então passa para a próxima candidatura
              if (candidatura.getTema() != null && candidatura.getTema().getId() == c.getTema().getId() && yearOfCandidatura2 == yearOfCandidatura && candidatura.getEstado() == EstadoCandidatura.APROVADO){
                System.out.println("Tema " + c.getTema().getTitulo() + " já atribuído");
                livre = false;
                break;
              }
            }
          }
          Calendar currentCal = Calendar.getInstance();
          int currentYear = currentCal.get(Calendar.YEAR);
          // Se a candidatura for do ano corrente e o tema ainda não tiver sido atribuído
          if (yearOfCandidatura == currentYear && temasAprovadasIds.contains(c.getTema().getId()) == false && livre == true) {
            c.setEstado(EstadoCandidatura.APROVADO);
            candidaturaRepository.save(c);
            temasAprovadasIds.add(c.getTema().getId());
            System.out.println("Tema " + c.getTema().getTitulo() + " atribuído ao aluno " + a.getName());
            output.add("Tema " + c.getTema().getTitulo() + " atribuído ao aluno " + a.getName());
            // Set Tese da candidatura
            if (c.getTema().getSubmissor() instanceof Docente) {
              Dissertacao tese = new Dissertacao(c);
              teseRepository.save(tese);
              c.setTese(tese);
              candidaturaRepository.save(c);
            } else {
              Projeto tese = new Projeto(c);
              teseRepository.save(tese);
              c.setTese(tese);
              candidaturaRepository.save(c);
            }
            // Passa para o próximo aluno
            break;
          } else {
            System.out.println("Tema " + c.getTema().getTitulo() + " não atribuído ao aluno " + a.getName() + " por ter sido atribuído a um aluno com melhor média ou por a candidatura não ser do ano corrente");
          }
        }
      }
    }
    return output;
  }
}

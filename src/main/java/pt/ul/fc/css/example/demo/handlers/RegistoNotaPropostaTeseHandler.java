package pt.ul.fc.css.example.demo.handlers;

import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.example.demo.entities.Tese;
import pt.ul.fc.css.example.demo.exceptions.NotPresentException;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;

@Component
@Transactional
public class RegistoNotaPropostaTeseHandler {
  private TeseRepository teseRepository;

  public RegistoNotaPropostaTeseHandler(TeseRepository teseRepository) {
    this.teseRepository = teseRepository;
  }

  public void registarNotaPropostaTese(Integer teseID, Integer docenteId, Integer nota)
      throws NotPresentException {
    if (teseID == null || docenteId == null) {
      throw new IllegalArgumentException("Tese e docente e nota são obrigatórios");
    }
    if (nota < 0 || nota > 20) {
      throw new IllegalArgumentException("Nota inválida");
    }

    Optional<Tese> optTese = teseRepository.findById(teseID);
    if (optTese.isEmpty()) {
      throw new NotPresentException("Tese não encontrada");
    }
    Tese tese = optTese.get();
    if (!(tese.getCandidatura().getTema().getSubmissor().getId() == docenteId)) {
      throw new IllegalArgumentException("Docente não é o orientador da Tese");
    }
    if (tese.getDefesaProposta() == null) {
      throw new IllegalArgumentException("Defesa de proposta não marcada");
    }
    tese.getDefesaProposta().setNota(nota);
  }
}

package pt.ul.fc.css.example.demo.handlers;

import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.example.demo.entities.Defesa;
import pt.ul.fc.css.example.demo.entities.Docente;
import pt.ul.fc.css.example.demo.entities.Juri;
import pt.ul.fc.css.example.demo.entities.Tese;
import pt.ul.fc.css.example.demo.exceptions.NotPresentException;
import pt.ul.fc.css.example.demo.repositories.DefesaRepository;
import pt.ul.fc.css.example.demo.repositories.JuriRepository;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;

@Component
@Transactional()
public class RegistoNotaHandler {

  private TeseRepository teseRepository;
  private DefesaRepository defesaRepository;

  public RegistoNotaHandler(
      DefesaRepository defesaRepository,
      TeseRepository teseRepository,
      JuriRepository juriRepository) {
    this.teseRepository = teseRepository;
    this.defesaRepository = defesaRepository;
  }

  public void registarNota(Integer defesaId, Integer nota) throws NotPresentException {
    Optional<Defesa> optDefesa = defesaRepository.findById(defesaId);
    if (!optDefesa.isPresent()) {
      throw new NotPresentException("Não foi encontrada uma defesa");
    }
    Defesa defesa = optDefesa.get();
    if (nota < 0 || nota > 20) {
      throw new IllegalArgumentException("Nota inválida");
    }
    defesa.setNota(nota);
    defesaRepository.save(defesa);
  }
}

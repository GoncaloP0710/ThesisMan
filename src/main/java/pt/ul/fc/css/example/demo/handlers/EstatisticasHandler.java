package pt.ul.fc.css.example.demo.handlers;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.example.demo.entities.Defesa;
import pt.ul.fc.css.example.demo.entities.Tese;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;

@Component
@Transactional
public class EstatisticasHandler {

  private TeseRepository teseRepository;

  public EstatisticasHandler(TeseRepository teseRepository) {
    this.teseRepository = teseRepository;
  }

  public String getEstatisticas() {
    List<Tese> teses = teseRepository.findAll();
    List<Defesa> defesasFinais =
        teses.stream().map(Tese::getDefesaFinal).filter(d -> d != null).toList();
    List<Defesa> defesasPropostas =
        teses.stream().map(Tese::getDefesaProposta).filter(d -> d != null).toList();
    Optional<Float> notasAcumuladasFinais =
        defesasFinais.stream().map(Defesa::getNota).reduce((m, n) -> m + n);
    Optional<Float> notasAcumuladasPropostas =
        defesasPropostas.stream().map(Defesa::getNota).reduce((m, n) -> m + n);
    double numeroAprovados =
        defesasFinais.stream().map(Defesa::getNota).filter(m -> m >= 9.5).toList().size();
    if (notasAcumuladasFinais.isEmpty())
      throw new IllegalArgumentException("Não foi possivel acumular as notas");
    if (notasAcumuladasPropostas.isEmpty())
      throw new IllegalArgumentException("Não foi possivel acumular as notas");
    String s =
        "Estatísticas: "
            + System.lineSeparator()
            + "Taxa de aprovação: "
            + Math.round(numeroAprovados / teses.size() * 10e4) / 10e2
            + "%"
            + System.lineSeparator()
            + "Média das propostas de tese: "
            + Math.round(notasAcumuladasPropostas.get() / defesasPropostas.size() * 10e2) / 10e2
            + System.lineSeparator()
            + "Média das defesas finais: "
            + Math.round(notasAcumuladasFinais.get() / defesasFinais.size() * 10e2) / 10e2
            + System.lineSeparator();
    return s;
  }
}

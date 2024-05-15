package pt.ul.fc.css.example.demo.handlers;

import java.util.Optional;

import org.springframework.stereotype.Component;

import java.util.List;

import pt.ul.fc.css.example.demo.repositories.TeseRepository;
import pt.ul.fc.css.example.demo.entities.Defesa;
import pt.ul.fc.css.example.demo.entities.Tese;

@Component
public class EstatisticasHandler {

    private TeseRepository teseRepository;

    public EstatisticasHandler(TeseRepository teseRepository) {
        this.teseRepository = teseRepository;
    }

    public float getEstatisticas() {
        List<Tese> teses = teseRepository.findAll();
        List<Defesa> defesas = teses.stream().map(Tese::getDefesaFinal).filter(d -> d != null).toList();
        Optional<Float> notasAcumuladas = defesas.stream().map(Defesa::getNota).reduce((m,n) -> m + n);
        if(notasAcumuladas.isEmpty())
            throw new IllegalArgumentException("Não foi possivel acumular as notas");
        return notasAcumuladas.get() / defesas.size();
    }
}
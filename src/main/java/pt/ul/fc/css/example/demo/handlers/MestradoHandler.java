package pt.ul.fc.css.example.demo.handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import pt.ul.fc.css.example.demo.entities.Mestrado;
import pt.ul.fc.css.example.demo.repositories.MestradoRepository;

@Component
public class MestradoHandler {
    MestradoRepository mestradoRepository;

    public MestradoHandler(MestradoRepository mestradoRepository) {
        this.mestradoRepository = mestradoRepository;
    }

    public List<Integer> getMestradosId(List<String> mestrados) {
        List<Mestrado> mestradosFound = mestradoRepository.findByNomeIn(mestrados);
        List<Integer> mestradosId = new ArrayList<>();
        for (Mestrado m : mestradosFound) {
            mestradosId.add(m.getId());
        }
        return mestradosId;
    }

    public List<String> getMestrados(){
        List<Mestrado> mestrados = mestradoRepository.findAll();
        List<String> mestradosNames = new ArrayList<>();
        for (Mestrado m : mestrados) {
            mestradosNames.add(m.getNome());
        }
        return mestradosNames;
    }
}

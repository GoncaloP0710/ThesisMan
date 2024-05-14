package pt.ul.fc.css.example.demo.handlers;

import java.util.Optional;

import pt.ul.fc.css.example.demo.entities.Docente;
import pt.ul.fc.css.example.demo.entities.Mestrado;
import pt.ul.fc.css.example.demo.entities.Tema;
import pt.ul.fc.css.example.demo.repositories.DocenteRepository;
import pt.ul.fc.css.example.demo.repositories.MestradoRepository;
import pt.ul.fc.css.example.demo.repositories.TemaRepository;

public class SubmissaoTemaDocenteHandler {
    private TemaRepository temaRepository;
    private DocenteRepository docenteRepository;
    private MestradoRepository mestradoRepository;

    public SubmissaoTemaDocenteHandler(TemaRepository temaRepository, DocenteRepository docenteRepository) {
        this.temaRepository = temaRepository;
        this.docenteRepository = docenteRepository;
    }

    public void submeterTema(String titulo, String descricao, float remuneracaoMensal, String email) {
        if (docenteRepository.findByEmail(email).isEmpty()) {
            throw new IllegalArgumentException("Docente não encontrado");
        }
        Tema tema = new Tema(titulo, descricao, remuneracaoMensal, docenteRepository.findByEmail(email).get());
        temaRepository.save(tema);
    }

    public void adicionarMestradoCompatível(String nome, String titulo, String email){
        if (docenteRepository.findByEmail(email).isEmpty()) {
            throw new IllegalArgumentException("Docente não encontrado");
        }
        Optional<Tema> optTema = temaRepository.findByTitulo(titulo);
        if (optTema.isEmpty()) {
            throw new IllegalArgumentException("Tema não encontrado");
        }
        Tema tema = optTema.get();
        if(!tema.getSubmissor().getEmail().equals(email)){
            throw new IllegalArgumentException("Docente não é o submissor do tema");
        }
        Optional<Mestrado> optMestrado = mestradoRepository.findByNome(nome);
        if(optMestrado.isEmpty()){
            throw new IllegalArgumentException("Mestrado não encontrado");
        }
        Mestrado mestrado = optMestrado.get();
        tema.addMestradosCompativeis(mestrado);
        temaRepository.save(tema);
    }
}

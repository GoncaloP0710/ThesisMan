package pt.ul.fc.css.example.demo.handlers;

import java.util.Optional;

import org.springframework.stereotype.Component;

import pt.ul.fc.css.example.demo.entities.Docente;
import pt.ul.fc.css.example.demo.entities.Mestrado;
import pt.ul.fc.css.example.demo.entities.Tema;
import pt.ul.fc.css.example.demo.repositories.DocenteRepository;
import pt.ul.fc.css.example.demo.repositories.MestradoRepository;
import pt.ul.fc.css.example.demo.repositories.TemaRepository;
import pt.ul.fc.css.example.exceptions.NotPresentException;

@Component
public class SubmissaoTemaDocenteHandler {
    private TemaRepository temaRepository;
    private DocenteRepository docenteRepository;
    private MestradoRepository mestradoRepository;

    public SubmissaoTemaDocenteHandler(TemaRepository temaRepository, DocenteRepository docenteRepository) {
        this.temaRepository = temaRepository;
        this.docenteRepository = docenteRepository;
    }

    public void submeterTema(String titulo, String descricao, float remuneracaoMensal, String email) throws NotPresentException {
        Optional<Docente> optDocente = docenteRepository.findByEmail(email); 
        if (optDocente.isEmpty()) {
            throw new NotPresentException("Docente não encontrado");
        }
        Docente docente = optDocente.get();
        Optional<Tema> optTema = temaRepository.findByTituloAndDescricaoAndRemuneracaoMensal(titulo, descricao, remuneracaoMensal);
        if (!optTema.isEmpty()) {
            throw new IllegalArgumentException("Tema já existe");
        }

        Tema tema = new Tema(titulo, descricao, remuneracaoMensal, docente);
        temaRepository.save(tema);

        // TODO: No ThesisManApplication nao faziamos isto mas sera que nao deviamos?
        docente.addTemaPropostos(tema);
        docenteRepository.save(docente);
    }

    public void adicionarMestradoCompatível(String nome, String titulo, String email) throws NotPresentException{
        if (docenteRepository.findByEmail(email).isEmpty()) {
            throw new NotPresentException("Docente não encontrado");
        }
        Optional<Tema> optTema = temaRepository.findByTitulo(titulo);
        if (optTema.isEmpty()) {
            throw new NotPresentException("Tema não encontrado");
        }
        Tema tema = optTema.get();
        if(!tema.getSubmissor().getEmail().equals(email)){
            throw new IllegalArgumentException("Docente não é o submissor do tema");
        }
        Optional<Mestrado> optMestrado = mestradoRepository.findByNome(nome);
        if(optMestrado.isEmpty()){
            throw new NotPresentException("Mestrado não encontrado");
        }
        Mestrado mestrado = optMestrado.get();
        tema.addMestradosCompativeis(mestrado);
        temaRepository.save(tema);
    }
}

package pt.ul.fc.css.example.demo.handlers;

import java.util.Optional;

import pt.ul.fc.css.example.demo.entities.Docente;
import pt.ul.fc.css.example.demo.entities.Mestrado;
import pt.ul.fc.css.example.demo.entities.Tema;
import pt.ul.fc.css.example.demo.entities.Utilizador;
import pt.ul.fc.css.example.demo.repositories.MestradoRepository;
import pt.ul.fc.css.example.demo.repositories.TemaRepository;
import pt.ul.fc.css.example.demo.repositories.UtilizadorEmpresarialRepository;
import pt.ul.fc.css.example.exceptions.NotPresentException;

public class SubmissaoTemaUtilizadorEmpresarialHandler {
    private TemaRepository temaRepository;
    private UtilizadorEmpresarialRepository utilizadorEmpresarialRepository;
    private MestradoRepository mestradoRepository;

    public SubmissaoTemaUtilizadorEmpresarialHandler(TemaRepository temaRepository, UtilizadorEmpresarialRepository utilizadorEmpresarialRepository,
                                                 MestradoRepository mestradoRepository) {
        this.temaRepository = temaRepository;
        this.utilizadorEmpresarialRepository = utilizadorEmpresarialRepository;
        this.mestradoRepository = mestradoRepository;
    }

    public void submeterTema(String titulo, String descricao, float remuneracaoMensal, String email) throws NotPresentException {
        if (utilizadorEmpresarialRepository.findByEmail(email).isEmpty()) {
            throw new NotPresentException("Utilizador empresarial não encontrado");
        }
        Tema tema = new Tema(titulo, descricao, remuneracaoMensal, utilizadorEmpresarialRepository.findByEmail(email).get());
        temaRepository.save(tema);
    }

    public void adicionarMestradoCompativel(String nome, String titulo, String email) throws NotPresentException{
        if (utilizadorEmpresarialRepository.findByEmail(email).isEmpty()) {
            throw new NotPresentException("Docente não encontrado");
        }
        Optional<Tema> optTema = temaRepository.findByTitulo(titulo);
        if (optTema.isEmpty()) {
            throw new NotPresentException("Tema não encontrado");
        }
        Tema tema = optTema.get();
        if(!tema.getSubmissor().getEmail().equals(email)){
            throw new IllegalArgumentException("Utilizador Empresarial não é o submissor do tema");
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

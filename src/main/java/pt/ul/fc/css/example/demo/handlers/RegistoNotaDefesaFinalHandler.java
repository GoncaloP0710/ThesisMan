package pt.ul.fc.css.example.demo.handlers;

import java.util.Optional;

import pt.ul.fc.css.example.demo.entities.Defesa;
import pt.ul.fc.css.example.demo.entities.Docente;
import pt.ul.fc.css.example.demo.entities.Juri;
import pt.ul.fc.css.example.demo.entities.Tese;
import pt.ul.fc.css.example.demo.repositories.DefesaRepository;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;
import pt.ul.fc.css.example.exceptions.NotPresentException;
import pt.ul.fc.css.example.demo.repositories.JuriRepository;



public class RegistoNotaDefesaFinalHandler {

    private TeseRepository teseRepository;

    public RegistoNotaDefesaFinalHandler(DefesaRepository defesaRepository, TeseRepository teseRepository, JuriRepository juriRepository) {
        this.teseRepository = teseRepository;                               
                                        
    }

    public void registarNota(Integer teseID, String emailPresidente, float nota) throws NotPresentException {
        if (teseID == null || emailPresidente == null) {
            throw new IllegalArgumentException("Tese e docente e nota são obrigatórios");
        }
        if (nota < 0 || nota > 20) {
            throw new IllegalArgumentException("Nota inválida");
        }
        Optional<Tese> optTese = teseRepository.findById(teseID);
        if(optTese.isPresent())
            throw new NotPresentException("Não foi encontrada uma tese");
        Tese tese = optTese.get();
        Defesa defesa = tese.getDefesaFinal();
        if(defesa == null)
            throw new IllegalArgumentException("Defesa final não marcada");
        Juri juri = defesa.getJuri();
        if(juri == null)
            throw new IllegalArgumentException("A defesa não contém um juri");
        Docente presidente = juri.getPresidente();
        if(presidente == null) 
            throw new IllegalArgumentException("O juri não tem presidente");
        if(!presidente.getEmail().equals(emailPresidente))
            throw new IllegalArgumentException("O utilizador que se está a tentar registar a nota não é o presidente");
        defesa.setNota(nota);

    }

}
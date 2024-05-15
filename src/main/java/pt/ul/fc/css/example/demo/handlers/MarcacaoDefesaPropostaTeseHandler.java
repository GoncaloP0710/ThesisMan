package pt.ul.fc.css.example.demo.handlers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import pt.ul.fc.css.example.demo.entities.Defesa;
import pt.ul.fc.css.example.demo.entities.Docente;
import pt.ul.fc.css.example.demo.entities.Juri;
import pt.ul.fc.css.example.demo.entities.Tese;
import pt.ul.fc.css.example.demo.repositories.AlunoRepository;
import pt.ul.fc.css.example.demo.repositories.DefesaRepository;
import pt.ul.fc.css.example.demo.repositories.DocenteRepository;
import pt.ul.fc.css.example.demo.repositories.JuriRepository;
import pt.ul.fc.css.example.demo.repositories.TeseRepository;
import pt.ul.fc.css.example.exceptions.NotPresentException;

@Component
public class MarcacaoDefesaPropostaTeseHandler {
    private TeseRepository teseRepository;
    private Defesa currentDefesa;
    private AlunoRepository alunoRepository;
    private DocenteRepository docenteRepository;
    private DefesaRepository defesaRepository;
    private JuriRepository juriRepository;

    public MarcacaoDefesaPropostaTeseHandler(TeseRepository teseRepository, AlunoRepository alunoRepository, DocenteRepository docenteRepository,
                                                 DefesaRepository defesaRepository, JuriRepository juriRepository) {
        this.teseRepository = teseRepository;
        this.alunoRepository = alunoRepository;
        this.docenteRepository = docenteRepository;
        this.defesaRepository = defesaRepository;
        this.juriRepository = juriRepository;
    }

    public void marcarDefesaPropostaTese(Integer teseID, String emailDocente, Integer arguenteId, 
                                        Integer docenteOrientadorId, Boolean isPresencial, 
                                        String sala, Integer duracao, Date data) throws NotPresentException {

        if (teseID == null || emailDocente == null) {
            throw new IllegalArgumentException("Tese e docente são obrigatórios");
        }
        Optional<Tese> optTese = teseRepository.findById(teseID);
        if(optTese.isEmpty()) {
            throw new IllegalArgumentException("Tese não encontrada");
        }
        Tese tese = optTese.get();
        if(!tese.getCandidatura().getTema().getSubmissor().getEmail().equals(emailDocente)) {
            throw new IllegalArgumentException("Docente não é o submissor do tema da tese");
        }
        List<Defesa> defesas = tese.getDefesas();
        // TODO: Verificar o ano da defesa
        for(Defesa defesa : defesas) {
            if(!defesa.isFinal()) {
                throw new IllegalArgumentException("Já existe uma defesa de proposta marcada");
            }
        }
        Optional<Docente> optArguente = docenteRepository.findById(arguenteId); 
        if (optArguente.isEmpty()) {
            throw new NotPresentException("Arguente não encontrado");
        }
        Docente arguente = optArguente.get();
        Optional<Docente> optDocenteOrientador = docenteRepository.findById(docenteOrientadorId); 
        if (optDocenteOrientador.isEmpty()) {
            throw new NotPresentException("Docente não encontrado");
        }
        Docente docenteOrientador = optDocenteOrientador.get();

        if (isCompativel(data, sala, duracao)) {
            currentDefesa = new Defesa(true, isPresencial);

            // Verificar se tal juri ja existe
            Optional<Juri> optJuri = juriRepository.findByJuri(arguente, docenteOrientador);
            Juri juri = null;

            if (optJuri.isEmpty()) {
                juri = new Juri(arguente, docenteOrientador);
                juriRepository.save(juri);
            } else {
                juri = optJuri.get();
            }

            currentDefesa.setJuri(juri);
            currentDefesa.setSala(sala);
            currentDefesa.setData(data);
            currentDefesa.setTese(tese);
            defesaRepository.save(currentDefesa);
        } else {
            throw new IllegalArgumentException("Data, sala e duração não são compatíveis");
        }
    }

    // TODO: Verificar se tem um comportamento correto
    private boolean isCompativel(Date data, String sala, Integer duracao) {
        List<Defesa> allDefesas = defesaRepository.findAll();
        Calendar calendar = Calendar.getInstance();
        for(Defesa defesa : allDefesas) {
            calendar.setTime(defesa.getData());
            calendar.add(Calendar.MINUTE, defesa.getDuracao());
            Date defesaEndTime = calendar.getTime();

            calendar.setTime(data);
            calendar.add(Calendar.MINUTE, duracao);
            Date newDefesaEndTime = calendar.getTime();

            if(defesa.getSala().equals(sala) && 
            !((defesa.getData().after(data) && defesa.getData().before(newDefesaEndTime)) || (defesaEndTime.after(data) && defesaEndTime.before(newDefesaEndTime)))) {
                return false;
            }
        }
        return true;
    }
}

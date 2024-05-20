package pt.ul.fc.css.example.demo.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.aspectj.weaver.ast.Not;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import pt.ul.fc.css.example.demo.dtos.AlunoDTO;
import pt.ul.fc.css.example.demo.dtos.CandidaturaDTO;
import pt.ul.fc.css.example.demo.dtos.DefesaDTO;
import pt.ul.fc.css.example.demo.dtos.DocenteDTO;
import pt.ul.fc.css.example.demo.dtos.TemaDTO;
import pt.ul.fc.css.example.demo.dtos.TeseDTO;
import pt.ul.fc.css.example.demo.entities.Aluno;
import pt.ul.fc.css.example.demo.entities.Candidatura;
import pt.ul.fc.css.example.demo.exceptions.NoProperStateException;
import pt.ul.fc.css.example.demo.exceptions.NotPresentException;
import pt.ul.fc.css.example.demo.services.ThesismanServiceImp;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@SessionAttributes({"id", "name", "isAdmin", "temas propostos", "projetos orientados", "mestrados", "temasDisponiveis",
                    "alunos", "candidaturas", "docentes"})
public class WebController {

    Logger logger = LoggerFactory.getLogger(WebController.class);
    @Autowired ThesismanServiceImp thesismanService;
    

    public WebController(){
        super();
    }

    String userEmail = "";

    @RequestMapping("/")
    public String getLogin(Model model) {
        thesismanService.populate();
        return "login";
    }

    @PostMapping("/dashboard")
    public String login(Model model, @RequestParam("email")String email){
        DocenteDTO docente = null;
        if(email.matches("^[a-zA-Z0-9]*@mockdocente[.]pt")){
            try{
               docente = thesismanService.loginDocente(email, "");
               System.out.println("Docente: " + docente.getNome());
               System.out.println("Docenteid: " + docente.getId());
            }catch(NotPresentException e){
                logger.error("Erro no login: " + e.getMessage());
                return "login";
            }
           
        }else{
            try{
                thesismanService.loginUtilizadorEmpresarial(email, "");
            }catch(NotPresentException e){
                logger.error("Necessário Registar Utilizador Empresarial");
                return "register";
            }
        }
        userEmail = email;
        model.addAttribute("id", docente.getId());
        model.addAttribute("name", docente.getNome());
        model.addAttribute("isAdmin", docente.getIsAdministrador());
        model.addAttribute("temas propostos", thesismanService.getTemas());
        model.addAttribute("projetos orientados", docente.getProjetosId());
        model.addAttribute("mestrados", thesismanService.getMestrados());
        model.addAttribute("candidaturas", thesismanService.getCandidaturasWithTeses());
        model.addAttribute("docentes", thesismanService.getDocentes());
        return "dashboard";
    }

    @GetMapping({"/dashboard"})
    public String dashboard(){
        return "dashboard";
    }

    @PostMapping({"/register"})
    public String register(){
        return "register";
    }

    @GetMapping({"/submeterTemas"})
    public String submeterTema() {
        return "submeterTemas";
    }

    @PostMapping("/submitTemaCall")
    public String submitTemaCall(final Model model, @RequestParam String title,
    @RequestParam String description, @RequestParam float compensation, @RequestParam String masters) {
        System.out.println("Model id: " + model.getAttribute("id"));
        try{
            List<String> mastersList = Arrays.asList(masters.split(","));
            Integer docenteId = (Integer) model.getAttribute("id");
            thesismanService.submeterTemaDocente(title, description, compensation, mastersList,docenteId);
        }catch(NotPresentException e){
            logger.error("Erro ao submeter tema: " + e.getMessage());
            return "submeterTema";
        }
        
        return "temaSubmetido";
    }

    @GetMapping({"/atribuicaoTema"})
    public String atribuicaoTema(Model model){
        if((Boolean) model.getAttribute("isAdmin") == true){
            List<TemaDTO> temas = thesismanService.getTemas();
            List<String> temasName = new ArrayList<String>();
            for(TemaDTO t : temas){
                temasName.add(t.getTitulo());
            }
            model.addAttribute("temasDisponiveis", temasName);
            List<AlunoDTO> alunos = thesismanService.getAlunos();
            List<String> alunosName = new ArrayList<String>();
            for(AlunoDTO a : alunos){
                alunosName.add(a.getName());
            }
            model.addAttribute("alunos", alunosName);
            return "atribuicaoTema";
        }else{
            return "notAdmin";
        }
    }

    @PostMapping({"/atribuirTemaCall"})
    public String postMethodName(@RequestParam String aluno, @RequestParam String tema) {
        try{
            AlunoDTO a = thesismanService.getAluno(aluno);
            TemaDTO t = thesismanService.getTema(tema);
            thesismanService.atribuicaoTemaAdmin(t.getId(), a.getId(), t.getId());
        }catch(NotPresentException e){
            logger.error("Erro ao atribuir tema: " + e.getMessage());
            return "NaoPreenchido";
        }catch(IllegalArgumentException e){
            logger.error("Erro ao atribuir tema: " + e.getMessage());
            return "alunoNoCandidaturas";
        }
        return "temaAtribuido";
    }
    

    @GetMapping({"/marcarDefesaProposta"})
    public String marcarDefesaProposta(Model model){
        try{
         List<CandidaturaDTO> c = thesismanService.getCandidaturasAceites();
         Map<CandidaturaDTO, TemaDTO> candidaturas = new HashMap<CandidaturaDTO, TemaDTO>();
         for(CandidaturaDTO candidatura : c){
            TeseDTO t = thesismanService.getTese(candidatura.getTeseId());
            TemaDTO tema = thesismanService.getTema(t.getId());
            candidaturas.put(candidatura, tema);
            //Assumimos que o submissor do tema é o orientador da tese
            // if(tema.getSubmissorId() == (Integer) model.getAttribute("id")){
            //     model.addAttribute("candidaturasAceites", candidaturas);
            // }
            model.addAttribute("candidaturasAceites", candidaturas);
        }
        }catch(NotPresentException e){
            logger.error("Erro ao verificar candidaturas: " + e.getMessage());
            return "dashboard";
        }
        return "marcarDefesaProposta";
    }

    @PostMapping("/marcarDefesaPropostaCall")
    public String marcarDefesaPropostaCall(Model model, @RequestParam Integer teseId, @RequestParam String data, @RequestParam(required = false) String online, @RequestParam(required = false) String room, @RequestParam Integer arguente){
        Boolean onlineBool = false;
        if(online.equals("on")){onlineBool = true;}
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date dateObj = null;
        try {
            dateObj = formatter.parse(data);
        } catch (ParseException e) {
            logger.error("Invalid date format. Expected format is dd-MM-yyyy.");
            return "marcarDefesaProposta";
        }

        try{
            thesismanService.marcarDefesaPropostaTese((Integer)model.getAttribute("id"), teseId, dateObj, onlineBool, room, arguente);
        }catch(NotPresentException e){
            logger.error("Erro ao marcar defesa: " + e.getMessage());
            return "marcarDefesaProposta";
        }
        return "marcarDefesaPropostaCall";
    }
    

    @GetMapping({"/registarNotaDefesaProposta"})
    public String registarNotaDefesaProposta(Model model){
        Map<CandidaturaDTO, TeseDTO> candidaturas = thesismanService.getCandidaturaWithTeseWithDefesaPropostaWithoutNota();
        model.addAttribute("candidaturaWithDefesaPropostaWithoutNota", candidaturas);
        return "registarNotaDefesaProposta";
    }

    @PostMapping({"/registarNotaDefesaPropostaCall"})
    public String registarNotaDefesaPropostaCall(Model model, @RequestParam Integer defesaId, @RequestParam Integer nota) throws NotPresentException{
        thesismanService.registarNotaPropostaTese(defesaId,(Integer)model.getAttribute("id"), nota);
        return "registarNotaCall";
    }

    @GetMapping({"/registarNotaDefesaFinal"})
    public String registarNotaDefesaFinal(Model model){
        Map<CandidaturaDTO, TeseDTO> candidaturas = thesismanService.getCandidaturaWithTeseWithDefesaFinalWithoutNota();
        model.addAttribute("candidaturaWithDefesaFinalWithoutNota", candidaturas);
        return "registarNotaDefesaFinal";
    }

    @PostMapping({"/registarNotaDefesaFinalCall"})
    public String registarNotaDefesaFinalCall(Model model, @RequestParam Integer defesaId, @RequestParam Integer nota) throws NotPresentException{
        thesismanService.registarNotaFinalTese(defesaId, (Integer)model.getAttribute("id"), nota);
        return "registarNotaCall";
    }

    @GetMapping({"/marcarDefesaFinal"})
    public String marcarDefesa(final Model model){
        try{
            List<CandidaturaDTO> c = thesismanService.getCandidaturawithDefesaPropostaDone();
            Map<CandidaturaDTO, TemaDTO> candidaturas = new HashMap<CandidaturaDTO, TemaDTO>();
            for(CandidaturaDTO candidatura : c){
                TeseDTO t = thesismanService.getTese(candidatura.getTeseId());
                TemaDTO tema = thesismanService.getTema(t.getId());
                candidaturas.put(candidatura, tema);
                //Assumimos que o submissor do tema é o orientador da tese
                // if(tema.getSubmissorId() == (Integer) model.getAttribute("id")){
                //     model.addAttribute("candidaturasAceites", candidaturas);
                // }
                model.addAttribute("candidaturasWithDefesasPropostasDone", candidaturas);
            }
        return "marcarDefesaFinal";

    }catch(NotPresentException e){
        logger.error("Erro ao verificar candidaturas: " + e.getMessage());
        return "dashboard";

    }catch(NoProperStateException e){
        logger.error("Erro ao verificar candidaturas: " + e.getMessage());
        return "dashboard";
    }
}

    @PostMapping("/marcarDefesaFinalCall")
    public String marcarDefesaFinalCall(final Model model, @RequestParam Integer teseId, @RequestParam String data, @RequestParam(required = false) String online, @RequestParam(required = false) String room, @RequestParam Integer arguente, @RequestParam Integer presidente){
        Boolean onlineBool = false;
        if(online.equals("on")){onlineBool = true;}
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date dateObj = null;
        try {
            dateObj = formatter.parse(data);
        } catch (ParseException e) {
            logger.error("Invalid date format. Expected format is dd-MM-yyyy.");
            return "marcarDefesaFinal";
        }
        
        try{
            thesismanService.marcarDefesaFinalTese((Integer)model.getAttribute("id"), teseId, dateObj, onlineBool, room, arguente, presidente);
        }catch(NotPresentException e){
            logger.error("Erro ao marcar defesa: " + e.getMessage());
            return "marcarDefesaFinal";
        }catch(NoProperStateException e){
            logger.error("Erro ao marcar defesa: " + e.getMessage());
            return "marcarDefesaFinal";
        }
        return "marcarDefesaFinalCall";
    }

    @GetMapping({"/estatisticas"})
    public String estatisticas(Model model){
        model.addAttribute("estatisticas", thesismanService.getEstatisticas());
        return "estatisticas";
    }

}




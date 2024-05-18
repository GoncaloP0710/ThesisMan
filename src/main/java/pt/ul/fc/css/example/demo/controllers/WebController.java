package pt.ul.fc.css.example.demo.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import pt.ul.fc.css.example.demo.dtos.DocenteDTO;
import pt.ul.fc.css.example.demo.dtos.TemaDTO;
import pt.ul.fc.css.example.demo.entities.Aluno;
import pt.ul.fc.css.example.demo.entities.Candidatura;
import pt.ul.fc.css.example.demo.exceptions.NotPresentException;
import pt.ul.fc.css.example.demo.services.ThesismanServiceImp;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@SessionAttributes({"id", "name", "isAdmin", "temas propostos", "projetos orientados", "mestrados", "temasDisponiveis",
                    "alunos", "candidaturas"})
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
    public String login(final Model model, @RequestParam("email")String email){
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
        model.addAttribute("candidaturas", thesismanService.getCandidaturas());
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

    @GetMapping({"/atribuicaoTema"})
    public String atribuicaoTema(final Model model){
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
    public String marcarDefesaProposta(final Model model){
        @SuppressWarnings("unchecked")
        List<CandidaturaDTO> c = (List<CandidaturaDTO>) model.getAttribute("candidaturas");
        List<Integer> candidaturasWithTese = new ArrayList<Integer>();
        for(CandidaturaDTO candidatura : c){
            if(candidatura.getTeseId() != null){
                candidaturasWithTese.add(candidatura.getId());
            }
        }

        return "marcarDefesaProposta";
    }

    @GetMapping({"/registarNotaDefesaProposta"})
    public String registarNotaDefesaProposta(){
        return "registarNotaDefesaProposta";
    }

    @GetMapping({"/marcarDefesa"})
    public String marcarDefesa(){
        return "marcarDefesa";
    }

    @GetMapping({"/registoNotaDefesaProposta"})
    public String registoNotaDefesaProposta(){
        return "registoNotaDefesaProposta";
    }

    @GetMapping({"/statistics"})
    public String statistics(){
        return "statistics";
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
    
 
}

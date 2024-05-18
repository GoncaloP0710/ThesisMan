package pt.ul.fc.css.example.demo.controllers;

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

import pt.ul.fc.css.example.demo.dtos.DocenteDTO;
import pt.ul.fc.css.example.demo.exceptions.NotPresentException;
import pt.ul.fc.css.example.demo.services.ThesismanServiceImp;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@SessionAttributes({"id", "name", "isAdmin", "temas propostos", "projetos orientados"})
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
        model.addAttribute("temas propostos", docente.getTemasPropostos());
        model.addAttribute("projetos orientados", docente.getProjetosId());
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
    public String atribuicaoTema(){
        return "atribuicaoTema";
    }

    @GetMapping({"/marcarDefesaProposta"})
    public String marcarDefesaProposta(){
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

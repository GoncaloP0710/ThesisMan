package pt.ul.fc.css.example.demo.controllers;

import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import pt.ul.fc.css.example.services.ThesismanService;

@Controller
public class WebThesismanController {
    
    @Autowired ThesismanService thesismanService;
    

    public WebThesismanController(){
        super();
    }

    String userEmail = "";

    @GetMapping({"/","login"})
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(){
        userEmail = "";
        return "redirect:/login";
    }
}

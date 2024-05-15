package pt.ul.fc.css.example.demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import pt.ul.fc.css.example.demo.dtos.DocenteDTO;
import pt.ul.fc.css.example.demo.dtos.UtilizadorEmpresarialDTO;
import pt.ul.fc.css.example.exceptions.NotPresentException;
import pt.ul.fc.css.example.services.ThesismanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController()
@RequestMapping("api")
public class RestThesismanController {

    @Autowired
    private ThesismanService thesismanService;

    @RequestMapping(value = "/populate", method = RequestMethod.GET)
    public void populate() {
        thesismanService.populate();
    }

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody UtilizadorEmpresarialDTO user){
        try{
            UtilizadorEmpresarialDTO newUser = thesismanService.registerUtilizadorEmpresarial(user.getEmpresa(), user.getContact());
            return ResponseEntity.ok().body(newUser);
        }catch(NotPresentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/login/utilizadorEmpresarial")
    ResponseEntity<?> login(@RequestParam String email, @RequestParam String password){
        try{
            UtilizadorEmpresarialDTO user = thesismanService.login(email, password);
            return ResponseEntity.ok().body(user);
        }catch(NotPresentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/login/docente")
    ResponseEntity<?> loginDocente(@RequestParam String email, @RequestParam String password){
        try{
            DocenteDTO user = thesismanService.loginDocente(email, password);
            return ResponseEntity.ok().body(user);
        }catch(NotPresentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
}

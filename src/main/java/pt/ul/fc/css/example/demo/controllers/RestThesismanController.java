package pt.ul.fc.css.example.demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import pt.ul.fc.css.example.demo.dtos.DocenteDTO;
import pt.ul.fc.css.example.demo.dtos.UtilizadorEmpresarialDTO;
import pt.ul.fc.css.example.exceptions.NotPresentException;
import pt.ul.fc.css.example.services.ThesismanService;

import java.util.ArrayList;

import org.hibernate.mapping.List;
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

    // @RequestMapping(value = "/populate", method = RequestMethod.GET)
    // public void populate() {
    //     thesismanService.populate();
    // }

    @PostMapping("/register")
    ResponseEntity<?> registerUEmpresarial(@RequestBody String json) throws JsonMappingException, JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode jsonNode = objectMapper.readTree(json);
            String empresa = jsonNode.get("empresa").asText();
            String nome = jsonNode.get("nome").asText();
            String contact = jsonNode.get("contact").asText();
            thesismanService.registerUtilizadorEmpresarial(empresa, nome, contact);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(NotPresentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/login/utilizadorEmpresarial")
    ResponseEntity<?> loginUEmpresarial(@RequestBody String json) throws JsonMappingException, JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode jsonNode = objectMapper.readTree(json);
            String email = jsonNode.get("email").asText();
            String password = jsonNode.get("password").asText();
            UtilizadorEmpresarialDTO utilizadorEmpresarialDTO = thesismanService.loginUtilizadorEmpresarial(email, password);
            return new ResponseEntity<>(utilizadorEmpresarialDTO, HttpStatus.OK);
        }catch(NotPresentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/login/docente")
    ResponseEntity<?> loginDocente(@RequestBody String json) throws JsonMappingException, JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode jsonNode = objectMapper.readTree(json);
            String email = jsonNode.get("email").asText();
            String password = jsonNode.get("password").asText();
            DocenteDTO docenteDTO = thesismanService.loginDocente(email, password);
            return new ResponseEntity<>(docenteDTO, HttpStatus.OK);
        }catch(NotPresentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
}

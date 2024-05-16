package pt.ul.fc.css.example.demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import pt.ul.fc.css.example.demo.dtos.CandidaturaDTO;
import pt.ul.fc.css.example.demo.dtos.DocenteDTO;
import pt.ul.fc.css.example.demo.dtos.UtilizadorEmpresarialDTO;
import pt.ul.fc.css.example.demo.entities.EstadoCandidatura;
import pt.ul.fc.css.example.exceptions.IllegalCandidaturaException;
import pt.ul.fc.css.example.exceptions.NotPresentException;
import pt.ul.fc.css.example.services.ThesismanService;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.crypto.Data;

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
            UtilizadorEmpresarialDTO utilizadorEmpresarialDTO = thesismanService.registerUtilizadorEmpresarial(empresa, nome, contact);
            return new ResponseEntity<>(utilizadorEmpresarialDTO, HttpStatus.OK);
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

    @PostMapping("/submeterTema/Docente")
    ResponseEntity<?> submeterTemaDocente(@RequestBody String json) throws JsonMappingException, JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode jsonNode = objectMapper.readTree(json);
            String titulo = jsonNode.get("titulo").asText();
            String descricao = jsonNode.get("descricao").asText();
            float remuneracaoMensal = jsonNode.get("remuneracaoMensal").floatValue();
            String email = jsonNode.get("email").asText();
            thesismanService.submeterTemaDocente(titulo, descricao, remuneracaoMensal, email);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(NotPresentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/submeterTema/UtilizadorEmpresarial")
    ResponseEntity<?> submeterTemaUtilizadorEmpresarial(@RequestBody String json) throws JsonMappingException, JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode jsonNode = objectMapper.readTree(json);
            String titulo = jsonNode.get("titulo").asText();
            String descricao = jsonNode.get("descricao").asText();
            float remuneracaoMensal = jsonNode.get("remuneracaoMensal").floatValue();
            String email = jsonNode.get("email").asText();
            thesismanService.submeterTemaUtilizadorEmpresarial(titulo, descricao, remuneracaoMensal, email);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(NotPresentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    // TODO: F. Listar os temas disponíveis neste ano lectivo, por parte dos alunos -> Falta a parte no service

    @PostMapping("/create/candidatura")
    ResponseEntity<?> createCandidatura(@RequestBody String json) throws JsonMappingException, JsonProcessingException, IllegalCandidaturaException{
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode jsonNode = objectMapper.readTree(json);
            Date dataCandidatura = new Date();
            String estadoStr = jsonNode.get("estado").asText();
            EstadoCandidatura estado = EstadoCandidatura.valueOf(estadoStr);
            String email = jsonNode.get("email").asText();
            Integer temaId = jsonNode.get("temaId").intValue();
            CandidaturaDTO candidaturaDTO = thesismanService.newCandidatura(dataCandidatura, estado, email, temaId);
            return new ResponseEntity<>(candidaturaDTO, HttpStatus.OK);
        }catch(NotPresentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // @PostMapping("/addTemaToCandidatura")
    // ResponseEntity<?> addTemaToCandidatura(@RequestBody String json) throws JsonMappingException, JsonProcessingException{
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     try{
    //         JsonNode jsonNode = objectMapper.readTree(json);
    //         String titulo = jsonNode.get("titulo").asText();
    //         Integer candidaturaID = jsonNode.get("candidaturaID").intValue();
    //         thesismanService.addTemaToCandidatura(titulo, candidaturaID);
    //         return new ResponseEntity<>(HttpStatus.OK);
    //     }catch(NotPresentException e) {
    //         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    //     }
    // }
}

package pt.ul.fc.css.example.demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import pt.ul.fc.css.example.demo.dtos.CandidaturaDTO;
import pt.ul.fc.css.example.demo.dtos.DocenteDTO;
import pt.ul.fc.css.example.demo.dtos.TeseDTO;
import pt.ul.fc.css.example.demo.dtos.TemaDTO;
import pt.ul.fc.css.example.demo.dtos.UtilizadorEmpresarialDTO;
import pt.ul.fc.css.example.demo.entities.EstadoCandidatura;
import pt.ul.fc.css.example.exceptions.IllegalCandidaturaException;
import pt.ul.fc.css.example.exceptions.NotPresentException;
import pt.ul.fc.css.example.services.ThesismanService;

import java.io.IOException;
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

    @GetMapping("/listarTemas")
    ResponseEntity<?> listarTemas(@RequestBody String json) throws JsonMappingException, JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode jsonNode = objectMapper.readTree(json);
            Integer alunoId = jsonNode.get("alunoId").intValue();
            List<TemaDTO> temas = thesismanService.listarTemasAlunos(alunoId);
            return new ResponseEntity<>(temas, HttpStatus.OK);
        }catch(NotPresentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/createCandidatura")
    ResponseEntity<?> createCandidatura(@RequestBody String json) throws JsonMappingException, JsonProcessingException, IllegalCandidaturaException{
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode jsonNode = objectMapper.readTree(json);
            Date dataCandidatura = new Date();
            String estadoStr = jsonNode.get("estado").asText();
            EstadoCandidatura estado = EstadoCandidatura.valueOf(estadoStr);
            Integer alunoId = jsonNode.get("alunoId").intValue();
            Integer temaId = jsonNode.get("temaId").intValue();
            CandidaturaDTO candidaturaDTO = thesismanService.newCandidatura(dataCandidatura, estado, alunoId, temaId);
            return new ResponseEntity<>(candidaturaDTO, HttpStatus.OK);
        }catch(NotPresentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/cancelarCandidatura")
    ResponseEntity<?> cancelarCandidatura(@RequestBody String json) throws JsonMappingException, JsonProcessingException, IllegalCandidaturaException{
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode jsonNode = objectMapper.readTree(json);
            Integer candidaturaId = jsonNode.get("candidaturaId").intValue();
            thesismanService.cancelCandidatura(candidaturaId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(NotPresentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/submeterDocTese")
    ResponseEntity<?> submeterDocTese(@RequestBody String json) throws IllegalCandidaturaException, IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode jsonNode = objectMapper.readTree(json);
            Integer candidaturaId = jsonNode.get("candidaturaId").intValue();
            Integer alunoId = jsonNode.get("alunoId").intValue();
            byte[] document = jsonNode.get("document").binaryValue();
            TeseDTO teseDTO = thesismanService.submitPropostaTeseDocsAluno(candidaturaId, document, alunoId);
            return new ResponseEntity<>(teseDTO, HttpStatus.OK);
        }catch(NotPresentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/submeterDocFinalTese")
    ResponseEntity<?> submeterDocFinalTese(@RequestBody String json) throws IllegalCandidaturaException, IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode jsonNode = objectMapper.readTree(json);
            Integer candidaturaId = jsonNode.get("candidaturaId").intValue();
            Integer alunoId = jsonNode.get("alunoId").intValue();
            byte[] document = jsonNode.get("document").binaryValue();
            TeseDTO teseDTO = thesismanService.submeterDocFinalTeseAluno(alunoId, candidaturaId, document);
            return new ResponseEntity<>(teseDTO, HttpStatus.OK);
        }catch(NotPresentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

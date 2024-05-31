package pt.ul.fc.css.example.demo.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import pt.ul.fc.css.example.demo.dtos.AlunoDTO;
import pt.ul.fc.css.example.demo.dtos.CandidaturaDTO;
import pt.ul.fc.css.example.demo.dtos.DocenteDTO;
import pt.ul.fc.css.example.demo.dtos.TeseDTO;
import pt.ul.fc.css.example.demo.dtos.TemaDTO;
import pt.ul.fc.css.example.demo.dtos.UtilizadorEmpresarialDTO;
import pt.ul.fc.css.example.demo.entities.EstadoCandidatura;
import pt.ul.fc.css.example.demo.exceptions.IllegalCandidaturaException;
import pt.ul.fc.css.example.demo.exceptions.NotPresentException;
import pt.ul.fc.css.example.demo.services.ThesismanService;
import pt.ul.fc.css.example.demo.services.ThesismanServiceImp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController()
@RequestMapping("api")
public class RestThesismanController {

    @Autowired
    private ThesismanServiceImp ThesismanServiceImp;

    @Operation(summary = "Populates the database with mock data")
    @ApiResponses(value = { 
    @ApiResponse(responseCode = "200", description = "Populated the Database")})
    @PostMapping("/populate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> populate(){
        ThesismanServiceImp.populate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Logs in a user and returns the user's data")
    @ApiResponses(value = { 
    @ApiResponse(responseCode = "200", description = "Logged in successfully", content = @Content(schema = @Schema(implementation = AlunoDTO.class))),
    @ApiResponse(responseCode = "400", description = "User not found"),})
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody String json) throws JsonMappingException, JsonProcessingException{

        ObjectMapper objectMapper = new ObjectMapper();

        try{
            JsonNode jsonNode = objectMapper.readTree(json);

            String email = jsonNode.get("email").asText();
            String password = jsonNode.get("password").asText();
            boolean wasLoggedOut = jsonNode.get("wasLoggedOut").asBoolean();

            if(!wasLoggedOut){
                ThesismanServiceImp.populate();
            }

            AlunoDTO userDTO = ThesismanServiceImp.loginAluno(email, password);

            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }catch(NotPresentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Lists all themes")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Listed all themes", content = @Content(schema = @Schema(implementation = TemaDTO.class))),
        @ApiResponse(responseCode = "400", description = "Themes could not be listed"),})
    @GetMapping("/listarTemas")
    public ResponseEntity<?> listarTemas(@RequestParam Integer alunoId) {
        try {
            List<TemaDTO> temas = ThesismanServiceImp.listarTemasAlunos(alunoId);
            return new ResponseEntity<>(temas, HttpStatus.OK);
        } catch(NotPresentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Lists all application")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Listed all applications", content = @Content(schema = @Schema(implementation = CandidaturaDTO.class))),
        @ApiResponse(responseCode = "400", description = "Applications could not be listed"),})
    @GetMapping("/listarCandidaturas")
    public ResponseEntity<?> listarCandidaturas(@RequestParam Integer alunoId) throws JsonMappingException, JsonProcessingException{
        try{
            List<CandidaturaDTO> candidaturas = ThesismanServiceImp.listarCandidaturasAlunos(alunoId);
            return new ResponseEntity<>(candidaturas, HttpStatus.OK);
        }catch(NotPresentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "List all final applications")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Listed all final applications", content = @Content(schema = @Schema(implementation = CandidaturaDTO.class))),
        @ApiResponse(responseCode = "400", description = "Final applications could not be listed"),})
    @GetMapping("/listarCandidaturasFinal")
    public ResponseEntity<?> listarCandidaturasFinal(@RequestParam Integer alunoId) throws JsonMappingException, JsonProcessingException{
        try{
            List<CandidaturaDTO> candidaturas = ThesismanServiceImp.listarCandidaturasAlunosFinal(alunoId);
            return new ResponseEntity<>(candidaturas, HttpStatus.OK);
        }catch(NotPresentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "List all proposal applications")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Listed all proposal applications", content = @Content(schema = @Schema(implementation = CandidaturaDTO.class))),
        @ApiResponse(responseCode = "400", description = "Proposal applications could not be listed"),})
    @GetMapping("/listarCandidaturasProposta")
    public ResponseEntity<?> listarCandidaturasProposta(@RequestParam Integer alunoId) throws JsonMappingException, JsonProcessingException{
        try{
            List<CandidaturaDTO> candidaturas = ThesismanServiceImp.listarCandidaturasAlunosProposta(alunoId);
            return new ResponseEntity<>(candidaturas, HttpStatus.OK);
        }catch(NotPresentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Creates a new application")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Created a new application", content = @Content(schema = @Schema(implementation = CandidaturaDTO.class))),
        @ApiResponse(responseCode = "400", description = "Could not create a new application"),})
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

            CandidaturaDTO candidaturaDTO = ThesismanServiceImp.newCandidatura(dataCandidatura, estado, alunoId, temaId);
            return new ResponseEntity<>(candidaturaDTO, HttpStatus.OK);
        }catch(NotPresentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (IllegalCandidaturaException a) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "cancels an application")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Canceled an application"),
        @ApiResponse(responseCode = "400", description = "Could not cancel an application"),})
    @PostMapping("/cancelarCandidatura")
    ResponseEntity<?> cancelarCandidatura(@RequestBody String json) throws JsonMappingException, JsonProcessingException, IllegalCandidaturaException{
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode jsonNode = objectMapper.readTree(json);
            Integer candidaturaId = jsonNode.get("candidaturaId").intValue();
            ThesismanServiceImp.cancelCandidatura(candidaturaId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(NotPresentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Submits a proposal thesis document by getting the thesis associated with an also given application")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Submitted a proposal thesis document", content = @Content(schema = @Schema(implementation = TeseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Could not submit a proposal thesis document"),})
    @PostMapping("/submeterDocTese")
    ResponseEntity<?> submeterDocTese(@RequestBody String json) throws IllegalCandidaturaException, IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode jsonNode = objectMapper.readTree(json);
            Integer candidaturaId = jsonNode.get("candidaturaId").intValue();
            Integer alunoId = jsonNode.get("alunoId").intValue();
            byte[] document = jsonNode.get("document").binaryValue();
            TeseDTO teseDTO = ThesismanServiceImp.submitPropostaTeseDocsAluno(candidaturaId, document, alunoId);
            return new ResponseEntity<>(teseDTO, HttpStatus.OK);
        }catch(NotPresentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Submits a final thesis document by getting the thesis associated with an also given application")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Submitted a final thesis document", content = @Content(schema = @Schema(implementation = TeseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Could not submit a final thesis document"),})
    @PostMapping("/submeterDocFinalTese")
    ResponseEntity<?> submeterDocFinalTese(@RequestBody String json) throws IllegalCandidaturaException, IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode jsonNode = objectMapper.readTree(json);
            Integer candidaturaId = jsonNode.get("candidaturaId").intValue();
            Integer alunoId = jsonNode.get("alunoId").intValue();
            byte[] document = jsonNode.get("document").binaryValue();
            TeseDTO teseDTO = ThesismanServiceImp.submeterDocFinalTeseAluno(alunoId, candidaturaId, document);
            return new ResponseEntity<>(teseDTO, HttpStatus.OK);
        }catch(NotPresentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

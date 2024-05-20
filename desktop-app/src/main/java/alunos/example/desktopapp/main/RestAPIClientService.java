package alunos.example.desktopapp.main;

import alunos.example.desktopapp.dtos.AlunoDTO;
import alunos.example.desktopapp.dtos.CandidaturaDTO;
import alunos.example.desktopapp.dtos.TemaDTO;
import alunos.example.desktopapp.dtos.TeseDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RestAPIClientService {

  private Integer alunoId;

  private static RestAPIClientService instance;

  private RestAPIClientService() {}

  public static RestAPIClientService getInstance() {
    if (instance == null) instance = new RestAPIClientService();
    return instance;
  }

  public boolean logIn(String email, String password) {
    try {
      System.out.println("email: " + email + " password: " + password);
      URL url = new URL("http://localhost:8080/api/login");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "application/json");
      con.setDoOutput(true);
      String requestBody = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";

      System.out.println("7");
      OutputStream outputStream = con.getOutputStream();
      System.out.println("8");
      outputStream.write(requestBody.getBytes("UTF-8"));
      System.out.println("9");
      outputStream.close();
      System.out.println("10");

      int responseCode = con.getResponseCode();
      System.out.println("11");
      System.out.println("responseCode: " + responseCode);
      if (responseCode == HttpURLConnection.HTTP_OK) {
        System.out.println("12");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        System.out.println("13");
        String inputLine;
        System.out.println("14");
        StringBuffer content = new StringBuffer();
        System.out.println("15");
        while ((inputLine = in.readLine()) != null) {
          content.append(inputLine);
        }
        System.out.println("16");
        in.close();

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("17");
        AlunoDTO aluno = objectMapper.readValue(content.toString(), AlunoDTO.class);
        //System.out.println("Aluno: "aluno.getName());
        
        System.out.println("18");


        alunoId = aluno.getId();
        System.out.println("19");
        return true;
      } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.getDialogPane().getStylesheets().add("/JavaFX.css");
        alert.setTitle("Erro");
        alert.setHeaderText("Aluno nao existe");
        alert.show();
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      Alert alert = new Alert(AlertType.ERROR);
      alert.getDialogPane().getStylesheets().add("/JavaFX.css");
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao estabelecer conexao com o servidor");
      alert.show();
      return false;
    }
  }

  public List<TemaDTO> listarTemas() {
    try {
      System.out.println(1);
      URL url = new URL("http://localhost:8000/api/listarTemas");
      System.out.println(2);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      System.out.println(3);
      con.setRequestMethod("GET");
      System.out.println(4);
      con.setRequestProperty("Content-Type", "application/json");
      System.out.println(5);
      con.setDoOutput(true);

      System.out.println(6);
      String requestBody = "{\"alunoId\": " + alunoId + "}";
      System.out.println(7);
      OutputStream outputStream = con.getOutputStream();
      System.out.println(8);
      outputStream.write(requestBody.getBytes("UTF-8"));
      System.out.println(9);
      outputStream.close();
      System.out.println(10);

      int responseCode = con.getResponseCode();
      System.out.println(11);
      if (responseCode == HttpURLConnection.HTTP_OK) {
        System.out.println(12);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
          content.append(inputLine);
        }
        in.close();

        ObjectMapper objectMapper = new ObjectMapper();
        List<TemaDTO> temasDOT =
            objectMapper.readValue(content.toString(), new TypeReference<List<TemaDTO>>() {});
        return temasDOT;
      } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.getDialogPane().getStylesheets().add("/JavaFX.css");
        alert.setTitle("Erro");
        alert.setHeaderText("Erro ao listar temas");
        alert.show();
        return null;
      }
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.getDialogPane().getStylesheets().add("/JavaFX.css");
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao estabelecer conexao com o servidor");
      alert.show();
      return null;
    }
  }

  public List<CandidaturaDTO> listarCandidaturas() {
    try {
      URL url = new URL("http://localhost:8000/api/listarCandidaturas");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      con.setRequestProperty("Content-Type", "application/json");
      con.setDoOutput(true);

      String requestBody = "{\"alunoId\": " + alunoId + "}";
      OutputStream outputStream = con.getOutputStream();
      outputStream.write(requestBody.getBytes("UTF-8"));
      outputStream.close();

      int responseCode = con.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
          content.append(inputLine);
        }
        in.close();

        ObjectMapper objectMapper = new ObjectMapper();
        List<CandidaturaDTO> candidaturasDOT =
            objectMapper.readValue(
                content.toString(), new TypeReference<List<CandidaturaDTO>>() {});
        return candidaturasDOT;
      } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.getDialogPane().getStylesheets().add("/JavaFX.css");
        alert.setTitle("Erro");
        alert.setHeaderText("Erro ao listar candidaturas");
        alert.show();
        return null;
      }
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.getDialogPane().getStylesheets().add("/JavaFX.css");
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao estabelecer conexao com o servidor");
      alert.show();
      return null;
    }
  }

  public CandidaturaDTO createCandidatura(Integer temaId, String estado) {
    try {
      URL url = new URL("http://localhost:8000/api/createCandidatura");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "application/json");
      con.setDoOutput(true);

      String requestBody =
          "{\"alunoId\": "
              + alunoId
              + ", \"temaId\": "
              + temaId
              + ", \"estado\": \""
              + estado
              + "\"}";
      OutputStream outputStream = con.getOutputStream();
      outputStream.write(requestBody.getBytes("UTF-8"));
      outputStream.close();

      int responseCode = con.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
          content.append(inputLine);
        }
        in.close();

        ObjectMapper objectMapper = new ObjectMapper();
        CandidaturaDTO candidaturaDTO =
            objectMapper.readValue(content.toString(), CandidaturaDTO.class);
        return candidaturaDTO;
      } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.getDialogPane().getStylesheets().add("/JavaFX.css");
        alert.setTitle("Erro");
        alert.setHeaderText("Erro ao submeter candidatura");
        alert.show();
        return null;
      }
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.getDialogPane().getStylesheets().add("/JavaFX.css");
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao estabelecer conexao com o servidor");
      alert.show();
      return null;
    }
  }

  public boolean cancelarCandidatura(Integer candidaturaId) {
    try {
      URL url = new URL("http://localhost:8000/api/cancelarCandidatura");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "application/json");
      con.setDoOutput(true);

      String requestBody = "{\"candidaturaId\": " + candidaturaId + "}";
      OutputStream outputStream = con.getOutputStream();
      outputStream.write(requestBody.getBytes("UTF-8"));
      outputStream.close();

      int responseCode = con.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        return true;
      } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.getDialogPane().getStylesheets().add("/JavaFX.css");
        alert.setTitle("Erro");
        alert.setHeaderText("Erro ao cancelar candidatura");
        alert.show();
        return false;
      }
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.getDialogPane().getStylesheets().add("/JavaFX.css");
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao estabelecer conexao com o servidor");
      alert.show();
      return false;
    }
  }

  public TeseDTO submeterDocTese(Integer candidaturaId, byte[] document) {
    try {
      URL url = new URL("http://localhost:8000/api/submeterDocTese");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "application/json");
      con.setDoOutput(true);

      String documentBase64 = Base64.getEncoder().encodeToString(document);
      String requestBody =
          "{\"candidaturaId\": "
              + candidaturaId
              + ", \"alunoId\": "
              + alunoId
              + ", \"document\": \""
              + documentBase64
              + "\"}";
      OutputStream outputStream = con.getOutputStream();
      outputStream.write(requestBody.getBytes("UTF-8"));
      outputStream.close();

      int responseCode = con.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
          content.append(inputLine);
        }
        in.close();

        ObjectMapper objectMapper = new ObjectMapper();
        TeseDTO teseDTO = objectMapper.readValue(content.toString(), TeseDTO.class);
        return teseDTO;
      } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.getDialogPane().getStylesheets().add("/JavaFX.css");
        alert.setTitle("Erro");
        alert.setHeaderText("Erro ao submeter documento");
        alert.show();
        return null;
      }
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.getDialogPane().getStylesheets().add("/JavaFX.css");
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao estabelecer conexao com o servidor");
      alert.show();
      return null;
    }
  }

  public TeseDTO submeterDocFinalTese(Integer candidaturaId, byte[] document) {
    try {
      URL url = new URL("http://localhost:8000/api/submeterDocFinalTese");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "application/json");
      con.setDoOutput(true);

      String documentBase64 = Base64.getEncoder().encodeToString(document);
      String requestBody =
          "{\"candidaturaId\": "
              + candidaturaId
              + ", \"alunoId\": "
              + alunoId
              + ", \"document\": \""
              + documentBase64
              + "\"}";
      OutputStream outputStream = con.getOutputStream();
      outputStream.write(requestBody.getBytes("UTF-8"));
      outputStream.close();

      int responseCode = con.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
          content.append(inputLine);
        }
        in.close();

        ObjectMapper objectMapper = new ObjectMapper();
        TeseDTO teseDTO = objectMapper.readValue(content.toString(), TeseDTO.class);
        return teseDTO;
      } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.getDialogPane().getStylesheets().add("/JavaFX.css");
        alert.setTitle("Erro");
        alert.setHeaderText("Erro ao submeter documento");
        alert.show();
        return null;
      }
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.getDialogPane().getStylesheets().add("/JavaFX.css");
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao estabelecer conexao com o servidor");
      alert.show();
      return null;
    }
  }
}

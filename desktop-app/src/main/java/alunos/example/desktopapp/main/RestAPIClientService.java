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

import java.io.InputStream;

public class RestAPIClientService {

  private Integer alunoId;

  private static RestAPIClientService instance;

  private RestAPIClientService() {}

  public static RestAPIClientService getInstance() {
    if (instance == null) instance = new RestAPIClientService();
    return instance;
  }

  public boolean logIn(String email, String password, boolean wasLoggedOut) {
    try {
      System.out.println("email: " + email + " password: " + password);
      URL url = new URL("http://localhost:8080/api/login");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "application/json");
      con.setDoOutput(true);
      String requestBody = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\", \"wasLoggedOut\": \"" + wasLoggedOut + "\"}";

      OutputStream outputStream = con.getOutputStream();
      outputStream.write(requestBody.getBytes("UTF-8"));
      outputStream.close();

      int responseCode = con.getResponseCode();
      System.out.println("responseCode: " + responseCode);
      if (responseCode == HttpURLConnection.HTTP_OK) {
        InputStream inputStream = con.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder responseBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
          responseBody.append(line);
        }
        reader.close();

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("Antes de obter resposta");
        AlunoDTO alunoDTO = objectMapper.readValue(responseBody.toString(), AlunoDTO.class);
        System.out.println("Depois de obter resposta");
        alunoId = alunoDTO.getId();
        System.out.println("alunoId: " + alunoId);
        return true;
      } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText("Aluno nao existe");
        alert.show();
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao estabelecer conexao com o servidor");
      alert.show();
      return false;
    }
  }

  public List<TemaDTO> listarTemas() {
    try {
      System.out.println(alunoId.toString());
      URL url = new URL("http://localhost:8080/api/listarTemas?alunoId=" + alunoId);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      con.setRequestProperty("Content-Type", "application/json");
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
        List<TemaDTO> temasDOT = objectMapper.readValue(content.toString(), new TypeReference<List<TemaDTO>>() {});
        System.out.println(temasDOT);
        return temasDOT;
      } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText("Erro ao listar temas");
        alert.show();
        return null;
      }
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao estabelecer conexao com o servidor");
      alert.show();
      return null;
    }
  }

  public List<CandidaturaDTO> listarCandidaturas() {
    try {
      URL url = new URL("http://localhost:8080/api/listarCandidaturas?alunoId=" + alunoId);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      con.setRequestProperty("Content-Type", "application/json");
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
        alert.setTitle("Erro");
        alert.setHeaderText("Erro ao listar candidaturas");
        alert.show();
        return null;
      }
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao estabelecer conexao com o servidor");
      alert.show();
      return null;
    }
  }

  public List<CandidaturaDTO> listarCandidaturasFinal() {
    try {
      URL url = new URL("http://localhost:8080/api/listarCandidaturasFinal?alunoId=" + alunoId);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      con.setRequestProperty("Content-Type", "application/json");
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
        alert.setTitle("Erro");
        alert.setHeaderText("Erro ao listar candidaturas");
        alert.show();
        return null;
      }
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao estabelecer conexao com o servidor");
      alert.show();
      return null;
    }
  }

  public List<CandidaturaDTO> listarCandidaturasProposta() {
    try {
      URL url = new URL("http://localhost:8080/api/listarCandidaturasProposta?alunoId=" + alunoId);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      con.setRequestProperty("Content-Type", "application/json");
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
        alert.setTitle("Erro");
        alert.setHeaderText("Erro ao listar candidaturas");
        alert.show();
        return null;
      }
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao estabelecer conexao com o servidor");
      alert.show();
      return null;
    }
  }

  public boolean createCandidatura(Integer temaId, String estado) {
    try {
      URL url = new URL("http://localhost:8080/api/createCandidatura");
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
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Candidatura submetida com sucesso");
        alert.show();
        return true;
      } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText("Erro ao submeter candidatura");
        alert.show();
        return false;
      } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText("Já existe uma candidatura submetida para este tema");
        alert.show();
        return false;
      }
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao estabelecer conexao com o servidor");
      alert.show();
      return false;
    }
  }

  public boolean cancelarCandidatura(Integer candidaturaId) {
    try {
      URL url = new URL("http://localhost:8080/api/cancelarCandidatura");
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
        alert.setTitle("Erro");
        alert.setHeaderText("Erro ao cancelar candidatura");
        alert.show();
        return false;
      }
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao estabelecer conexao com o servidor");
      alert.show();
      return false;
    }
  }

  public Boolean submeterDocTese(Integer candidaturaId, byte[] document) {
    try {
      System.out.println("alunoId: " + alunoId);
      URL url = new URL("http://localhost:8080/api/submeterDocTese");
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
        return true;
      } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText("Erro ao submeter documento");
        alert.show();
        return false;
      }
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao estabelecer conexao com o servidor");
      alert.show();
      return false;
    }
  }

  public Boolean submeterDocFinalTese(Integer candidaturaId, byte[] document) {
    try {
      URL url = new URL("http://localhost:8080/api/submeterDocFinalTese");
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
        return true;
      } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText("Erro ao submeter documento");
        alert.show();
        return false;
      }
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao estabelecer conexao com o servidor");
      alert.show();
      return false;
    }
  }

  public void setAlunoIdNull () {
    this.alunoId = null;
  }
}

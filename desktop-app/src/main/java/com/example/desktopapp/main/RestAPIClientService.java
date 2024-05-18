package pt.ul.fc.css.example.demo.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import pt.ul.fc.css.example.demo.dtos.AlunoDTO;
import pt.ul.fc.css.example.demo.dtos.CandidaturaDTO;
import pt.ul.fc.css.example.demo.dtos.TemaDTO;
import pt.ul.fc.css.example.demo.dtos.TeseDTO;

public class RestAPIClientService {
    
    private Integer alunoId;

    private static RestAPIClientService instance;

    private RestAPIClientService() {

	}

    public static RestAPIClientService getInstance() {
		if (instance == null)
			instance = new RestAPIClientService();
		return instance;
	}

    public AlunoDTO logIn(String email, String password) {
		try {
			URL url = new URL("http://localhost:8000/api/login");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoOutput(true);

			String requestBody = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";
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
                AlunoDTO aluno = objectMapper.readValue(content.toString(), AlunoDTO.class);
                // TODO: O AlunoDTO nao tem atributo do id. Devemos adicionar ou usar o email como era dantes?
                alunoId = aluno.getId();
                return aluno;
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.getDialogPane().getStylesheets().add("/JavaFX.css");
				alert.setTitle("Erro");
				alert.setHeaderText("Aluno nao existe");
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

    public List<TemaDTO> listarTemas() {
        try {
            URL url = new URL("http://localhost:8000/api/listarTemas");
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
                List<TemaDTO> temasDOT = objectMapper.readValue(content.toString(), new TypeReference<List<TemaDTO>>() {});
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

    public CandidaturaDTO createCandidatura(Integer temaId, String estado) {
        try {
            URL url = new URL("http://localhost:8000/api/createCandidatura");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            String requestBody = "{\"alunoId\": " + alunoId + ", \"temaId\": " + temaId + ", \"estado\": \"" + estado + "\"}";
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
                CandidaturaDTO candidaturaDTO = objectMapper.readValue(content.toString(), CandidaturaDTO.class);
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

    public void cancelarCandidatura(Integer candidaturaId) {
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
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.getDialogPane().getStylesheets().add("/JavaFX.css");
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao cancelar candidatura");
                alert.show();
            }
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.getDialogPane().getStylesheets().add("/JavaFX.css");
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao estabelecer conexao com o servidor");
            alert.show();
        }
    }

    public TeseDTO submeterDocTese(Integer candidaturaId, Integer alunoId, byte[] document) {
        try {
            URL url = new URL("http://localhost:8000/api/submeterDocTese");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            String documentBase64 = Base64.getEncoder().encodeToString(document);
            String requestBody = "{\"candidaturaId\": " + candidaturaId + ", \"alunoId\": " + alunoId + ", \"document\": \"" + documentBase64 + "\"}";
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

    public TeseDTO submeterDocFinalTese(Integer candidaturaId, Integer alunoId, byte[] document) {
        try {
            URL url = new URL("http://localhost:8000/api/submeterDocFinalTese");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            String documentBase64 = Base64.getEncoder().encodeToString(document);
            String requestBody = "{\"candidaturaId\": " + candidaturaId + ", \"alunoId\": " + alunoId + ", \"document\": \"" + documentBase64 + "\"}";
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

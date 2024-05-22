package alunos.example.desktopapp.menu;

import alunos.example.desktopapp.Main;
import alunos.example.desktopapp.cancelar_candidatura.CancelarCandidaturaController;
import alunos.example.desktopapp.create_candidatura.CreateCandidaturaController;
import alunos.example.desktopapp.list_temas.ListTemasController;
import alunos.example.desktopapp.login.LoginController;
import alunos.example.desktopapp.main.RestAPIClientService;
import alunos.example.desktopapp.submeterDocFinalTese.SubmeterDocFinalTeseController;
import alunos.example.desktopapp.submeterDocTese.SubmeterDocTeseController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MenuController {
  private double height;
  private double width;
  private Stage primaryStage;

  @FXML private HBox bottomHbox;

  @FXML private Button logOut;

  @FXML private GridPane menuGrid;

  @FXML private Button listTemas;

  @FXML private Label title;

  @FXML private Button createCandidatura;

  @FXML private Button cancelarCandidatura;

  @FXML private Button submeterDoc;

  @FXML private Button submeterDocFinal;

  public void setUp(Stage primaryStage) {
    this.primaryStage = primaryStage;

    height = Screen.getPrimary().getBounds().getHeight();
    width = Screen.getPrimary().getBounds().getWidth();
  }

  @FXML
  public void logOut() throws Exception {
    RestAPIClientService.getInstance().setAlunoIdNull();
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/login.fxml"));
    BorderPane login = loader.load();
    LoginController controller = loader.<LoginController>getController();
    controller.setUp(primaryStage);
    primaryStage.setHeight(300);
    controller.setWasLogout();
    primaryStage.getScene().setRoot(login);
  }

  @FXML
  public void listTemas() throws Exception {
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/list_temas.fxml"));
    StackPane listTemas = loader.load();
    ListTemasController controller = loader.<ListTemasController>getController();
    controller.setUp(primaryStage);
    primaryStage.setWidth(800);
    primaryStage.setX(primaryStage.getX() - 250);
    primaryStage.getScene().setRoot(listTemas);
  }

  @FXML
  public void createCandidatura() throws Exception {
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/create_candidatura.fxml"));
    BorderPane createCandidatura = loader.load();
    CreateCandidaturaController controller = loader.<CreateCandidaturaController>getController();
    controller.setUp(primaryStage);
    primaryStage.setWidth(800);
    primaryStage.setX(primaryStage.getX() - 250);
    primaryStage.getScene().setRoot(createCandidatura);
  }

  @FXML
  public void cancelCandidatura() throws Exception {
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/cancel_candidatura.fxml"));
    BorderPane cancelCandidatura = loader.load();
    CancelarCandidaturaController controller =
            loader.<CancelarCandidaturaController>getController();
    primaryStage.setWidth(800);
    primaryStage.setX(primaryStage.getX() - 250);
    controller.setUp(primaryStage);
    primaryStage.getScene().setRoot(cancelCandidatura);
  }

  @FXML
  public void submeterDoc() throws Exception {

    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/submeter_doc.fxml"));
    BorderPane submeterDoc = loader.load();
    SubmeterDocTeseController controller = loader.<SubmeterDocTeseController>getController();
    controller.setUp(primaryStage);
    primaryStage.setWidth(800);
    primaryStage.setX(primaryStage.getX() - 250);
    primaryStage.getScene().setRoot(submeterDoc);
  }

  @FXML
  public void submeterDocFinal() throws Exception {
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/submeter_doc_final.fxml"));
    BorderPane submeterDocFinal = loader.load();
    SubmeterDocFinalTeseController controller =
            loader.<SubmeterDocFinalTeseController>getController();
    controller.setUp(primaryStage);
    primaryStage.setWidth(800);
    primaryStage.setX(primaryStage.getX() - 250);
    primaryStage.getScene().setRoot(submeterDocFinal);
  }
}

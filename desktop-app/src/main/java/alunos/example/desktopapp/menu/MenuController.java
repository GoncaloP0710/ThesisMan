package alunos.example.desktopapp.menu;

import alunos.example.desktopapp.Main;
import alunos.example.desktopapp.create_candidatura.CreateCandidaturaController;
import alunos.example.desktopapp.list_temas.ListTemasController;

import alunos.example.desktopapp.submeterDocTese.SubmeterDocTeseController;
import alunos.example.desktopapp.submeterDocFinalTese.SubmeterDocFinalTeseController;

import alunos.example.desktopapp.login.LoginController;
import alunos.example.desktopapp.main.RestAPIClientService;
import alunos.example.desktopapp.cancelar_candidatura.CancelarCandidaturaController;

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

  @FXML private StackPane pane;

  @FXML private HBox bottomHbox;

  @FXML private Button logOut;

  @FXML private Label title;

  @FXML private GridPane menuGrid;

  @FXML private Button listTemas;

  @FXML private Button createCandidatura;

  @FXML private Button cancelarCandidatura;

  @FXML private Button submeterDoc;

  @FXML private Button submeterDocFinal;

  public void setUp(Stage primaryStage) {
    this.primaryStage = primaryStage;

    height = Screen.getPrimary().getBounds().getHeight();
    width = Screen.getPrimary().getBounds().getWidth();

    setUpGrid();
    setUpButtons();
    setUpbottomHbox();
  }

  private void setUpbottomHbox() {
    // StackPane.setMargin(bottomHbox, new Insets(height * 0.06, 0, 0, width * 0.04));
    // HBox.setMargin(title, new Insets(0, 0, 0, width * 0.04));
  }

  private void setUpGrid() {
    // StackPane.setMargin(menuGrid, new Insets(height * 0.11, 0, 0, 0));
    // menuGrid.setPadding(new Insets(height / 8, width * 0.15, height / 5, width * 0.15));
    // menuGrid.setHgap(width * 0.005);
    // menuGrid.setVgap(width * 0.005);
  }

  private void setUpButtons() {
    // logOut.setPrefSize(width / 20, height / 20);
    // listTemas.setPrefSize(width / 4, height / 5);
    // createCandidatura.setPrefSize(width / 4, height / 5);
  }

  @FXML
  public void logOut() throws Exception {
    RestAPIClientService.getInstance().setAlunoIdNull();
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/login.fxml"));
    StackPane login = loader.load();
    LoginController controller = loader.<LoginController>getController();
    controller.setUp(primaryStage);
    primaryStage.setWidth(600);
    primaryStage.setX(primaryStage.getX() - 150);
    primaryStage.getScene().setRoot(login);
  }

  @FXML
  public void listTemas() throws Exception {
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/list_temas.fxml"));
    StackPane listTemas = loader.load();
    ListTemasController controller = loader.<ListTemasController>getController();
    controller.setUp(primaryStage);
    primaryStage.setWidth(600);
    primaryStage.setX(primaryStage.getX() - 150);
    primaryStage.getScene().setRoot(listTemas);
  }

  @FXML
  public void createCandidatura() throws Exception {
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/create_candidatura.fxml"));
    StackPane createCandidatura = loader.load();
    CreateCandidaturaController controller = loader.<CreateCandidaturaController>getController();
    controller.setUp(primaryStage);
    primaryStage.getScene().setRoot(createCandidatura);
  }

  @FXML
  public void cancelCandidatura() throws Exception {
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/cancel_candidatura.fxml"));
    StackPane cancelCandidatura = loader.load();
    CancelarCandidaturaController controller = loader.<CancelarCandidaturaController>getController();
    controller.setUp(primaryStage);
    primaryStage.getScene().setRoot(cancelCandidatura);
  }

  @FXML
  public void submeterDoc() throws Exception {
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/submeter_doc.fxml"));
    BorderPane submeterDoc = loader.load();
    SubmeterDocTeseController controller = loader.<SubmeterDocTeseController>getController();
    controller.setUp(primaryStage);
    primaryStage.setWidth(600);
    primaryStage.setX(primaryStage.getX() - 150);
    primaryStage.getScene().setRoot(submeterDoc);
  }

  @FXML
  public void submeterDocFinal() throws Exception {
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/submeter_doc_final.fxml"));
    BorderPane submeterDocFinal = loader.load();
    SubmeterDocFinalTeseController controller = loader.<SubmeterDocFinalTeseController>getController();
    controller.setUp(primaryStage);
    primaryStage.setWidth(600);
    primaryStage.setX(primaryStage.getX() - 150);
    primaryStage.getScene().setRoot(submeterDocFinal);
  }
}

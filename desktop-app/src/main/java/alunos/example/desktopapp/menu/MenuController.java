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
    System.out.println("1");
    RestAPIClientService.getInstance().setAlunoIdNull();
    System.out.println("2");
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/login.fxml"));
    System.out.println("3");
    BorderPane login = loader.load();
    System.out.println("4");
    LoginController controller = loader.<LoginController>getController();
    System.out.println("5");
    controller.setUp(primaryStage);
    System.out.println("6");
    primaryStage.setHeight(300);
    System.out.println("7");
    System.out.println("8");
    controller.setWasLogout();
    System.out.println("9");
    primaryStage.getScene().setRoot(login);
    System.out.println("10");
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
    System.out.println("1");
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/create_candidatura.fxml"));
    System.out.println("2");
    BorderPane createCandidatura = loader.load();
    System.out.println("3");
    CreateCandidaturaController controller = loader.<CreateCandidaturaController>getController();
    System.out.println("4");
    controller.setUp(primaryStage);
    primaryStage.setWidth(800);
    primaryStage.setX(primaryStage.getX() - 250);
    System.out.println("5");
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

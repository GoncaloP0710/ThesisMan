package alunos.example.desktopapp.menu;

import alunos.example.desktopapp.Main;
import alunos.example.desktopapp.create_candidatura.CreateCandidaturaController;
import alunos.example.desktopapp.list_temas.ListTemasController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

  public void setUp(Stage primaryStage) {
    this.primaryStage = primaryStage;

    height = Screen.getPrimary().getBounds().getHeight();
    width = Screen.getPrimary().getBounds().getWidth();

    setUpGrid();
    setUpButtons();
    setUpbottomHbox();
  }

  private void setUpbottomHbox() {
    StackPane.setMargin(bottomHbox, new Insets(height * 0.06, 0, 0, width * 0.04));
    HBox.setMargin(title, new Insets(0, 0, 0, width * 0.04));
  }

  private void setUpGrid() {
    StackPane.setMargin(menuGrid, new Insets(height * 0.11, 0, 0, 0));
    menuGrid.setPadding(new Insets(height / 8, width * 0.15, height / 5, width * 0.15));
    menuGrid.setHgap(width * 0.005);
    menuGrid.setVgap(width * 0.005);
  }

  private void setUpButtons() {
    // logOut.setPrefSize(width / 20, height / 20);
    // listTemas.setPrefSize(width / 4, height / 5);
    // createCandidatura.setPrefSize(width / 4, height / 5);
  }

  @FXML
  public void listTemas() throws Exception {
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/list_temas.fxml"));
    StackPane listTemas = loader.load();
    ListTemasController controller = loader.<ListTemasController>getController();
    controller.setUp(primaryStage);
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
}

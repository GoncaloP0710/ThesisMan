package alunos.example.desktopapp.create_candidatura;

import alunos.example.desktopapp.Main;
import alunos.example.desktopapp.TableRow;
import alunos.example.desktopapp.dtos.TemaDTO;
import alunos.example.desktopapp.main.RestAPIClientService;
import alunos.example.desktopapp.menu.MenuController;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class CreateCandidaturaController {

  private double height;
  private double width;
  private Stage primaryStage;
  private TableRow currentTema;

  @FXML private BorderPane pane;

  @FXML private HBox topHbox;

  @FXML private Button goBack;

  @FXML private Button createCandidatura;

  @FXML private TableView<TableRow> table;

  public void setUp(Stage primaryStage) {
    this.primaryStage = primaryStage;

    height = Screen.getPrimary().getBounds().getHeight();
    width = Screen.getPrimary().getBounds().getWidth();
    setUpTable();
  }

  private void setUpTable() {
    BorderPane.setMargin(
            table, new Insets(height * 0.16, width * 0.08, height * 0.07, width * 0.08));
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<TableRow, String> tc1 = new TableColumn<>("id");
    TableColumn<TableRow, String> tc2 = new TableColumn<>("titulo");
    TableColumn<TableRow, String> tc3 = new TableColumn<>("descricao");

    tc1.setCellValueFactory(new PropertyValueFactory<>("col1"));
    tc2.setCellValueFactory(new PropertyValueFactory<>("col2"));
    tc3.setCellValueFactory(new PropertyValueFactory<>("col3"));

    table.getColumns().addAll(tc1, tc2, tc3);

    List<TemaDTO> temas = RestAPIClientService.getInstance().listarTemas();

    if (temas == null) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao obter lista de temas");
      alert.showAndWait();
      return;
    }

    for (TemaDTO tema : temas) {
      TableRow t = new TableRow();
      t.setCol1(String.valueOf(tema.getId()));
      t.setCol2(tema.getTitulo());
      t.setCol3(tema.getDescricao());

      table.getItems().add(t);
    }

    table.setOnMouseClicked(
            event -> {
              if (event.getButton() == MouseButton.PRIMARY) {
                currentTema = table.getSelectionModel().getSelectedItem();
              }
            });
  }

  @FXML
  public void goBack() throws Exception {
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/menu.fxml"));
    StackPane root = loader.load();
    MenuController controller = loader.<MenuController>getController();
    controller.setUp(primaryStage);
    primaryStage.setWidth(300);
    primaryStage.setX(primaryStage.getX() + 250);
    primaryStage.getScene().setRoot(root);
  }

  @FXML
  public void createCandidatura() throws Exception {
    if (currentTema == null) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Erro");
      alert.setHeaderText("Tema não selecionado");
      alert.setContentText("Por favor selecione um tema");
      alert.showAndWait();
      return;
    }

    if (RestAPIClientService.getInstance()
            .createCandidatura(Integer.valueOf(currentTema.getCol1()), "EMPROCESSAMENTO")) {
    } else {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao criar candidatura");
      alert.showAndWait();
    }
  }
}

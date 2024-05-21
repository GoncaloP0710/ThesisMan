package alunos.example.desktopapp.cancelar_candidatura;

import alunos.example.desktopapp.Main;
import alunos.example.desktopapp.TableRow;
import alunos.example.desktopapp.dtos.CandidaturaDTO;
import alunos.example.desktopapp.main.RestAPIClientService;
import alunos.example.desktopapp.menu.MenuController;
import java.text.SimpleDateFormat;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class CancelarCandidaturaController {
  private double height;
  private double width;
  private Stage primaryStage;
  private TableRow currentCandidatura;

  @FXML private BorderPane pane;

  @FXML private HBox topHbox;

  @FXML private Button goBack;

  @FXML private Label title;

  @FXML private TableView<TableRow> table;

  @FXML private Button cancelarCandidatura;

  public void setUp(Stage primaryStage) {
    this.primaryStage = primaryStage;

    height = Screen.getPrimary().getBounds().getHeight();
    width = Screen.getPrimary().getBounds().getWidth();

    setUpTopHbox();
    setUpDeleteButton();
    setUpGoBackButton();
    setUpTable();
  }

  private void setUpTopHbox() {
    BorderPane.setMargin(topHbox, new Insets(height * 0.06, 0, 0, width * 0.04));
    HBox.setMargin(title, new Insets(0, 0, 0, width * 0.04));
  }

  private void setUpDeleteButton() {
    cancelarCandidatura.setPrefSize(width / 20, height / 20);
  }

  private void setUpGoBackButton() {
    goBack.setPrefSize(width / 20, height / 20);
  }

  private void setUpTable() {
    BorderPane.setMargin(
        table, new Insets(height * 0.16, width * 0.08, height * 0.07, width * 0.08));
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<TableRow, String> tc1 = new TableColumn<>("id");
    TableColumn<TableRow, String> tc2 = new TableColumn<>("temaId");
    TableColumn<TableRow, String> tc3 = new TableColumn<>("dataCandidatura");
    TableColumn<TableRow, String> tc4 = new TableColumn<>("estado");
    TableColumn<TableRow, String> tc5 = new TableColumn<>("teseId");
    TableColumn<TableRow, String> tc6 = new TableColumn<>("alunoId");
    TableColumn<TableRow, String> tc7 = new TableColumn<>("selected");

    tc1.setCellValueFactory(new PropertyValueFactory<>("col1"));
    tc2.setCellValueFactory(new PropertyValueFactory<>("col2"));
    tc3.setCellValueFactory(new PropertyValueFactory<>("col3"));
    tc4.setCellValueFactory(new PropertyValueFactory<>("col4"));
    tc5.setCellValueFactory(new PropertyValueFactory<>("col5"));
    tc6.setCellValueFactory(new PropertyValueFactory<>("col6"));
    tc7.setCellValueFactory(new PropertyValueFactory<>("col7"));

    table.getColumns().addAll(tc1, tc2, tc3, tc4, tc5, tc6, tc7);

    List<CandidaturaDTO> candidaturas = RestAPIClientService.getInstance().listarCandidaturas();

    for (CandidaturaDTO candidatura : candidaturas) {
      TableRow t = new TableRow();
      t.setCol1(String.valueOf(candidatura.getId()));
      t.setCol2(String.valueOf(candidatura.getTemaId()));
      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
      String dateString = formatter.format(candidatura.getDataCandidatura());
      t.setCol3(dateString);
      t.setCol4(candidatura.getEstado());
      t.setCol5(String.valueOf(candidatura.getTeseId()));
      t.setCol6(String.valueOf(candidatura.getAlunoId()));
      t.setCol7("");

      table.getItems().add(t);
    }

    table.setOnMouseClicked(
        event -> {
          if (event.getButton() == MouseButton.PRIMARY) {
            currentCandidatura = table.getSelectionModel().getSelectedItem();
            for (TableRow row : table.getItems()) {
              row.setCol7("");
            }
            table.getSelectionModel().getSelectedItem().setCol7("X");
          }
        });
  }

  @FXML
  public void goBack() throws Exception {
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("/menu.fxml"));
    StackPane root = loader.load();
    MenuController controller = loader.<MenuController>getController();
    controller.setUp(primaryStage);
    primaryStage.getScene().setRoot(root);
  }

  @FXML
  public void deleteCandidatura() throws Exception {
    if (currentCandidatura == null) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Erro");
      alert.setHeaderText("Candidatura não selecionada");
      alert.setContentText("Por favor selecione uma candidatura");
      alert.showAndWait();
      return;
    }

    if (RestAPIClientService.getInstance().cancelarCandidatura(Integer.valueOf(currentCandidatura.getCol1()))) {
      setUpTable();
    } else {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao cancelar candidatura");
      alert.showAndWait();
    }
  }
}

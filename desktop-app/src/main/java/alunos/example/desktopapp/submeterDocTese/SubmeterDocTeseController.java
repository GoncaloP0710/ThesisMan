package alunos.example.desktopapp.submeterDocTese;

import alunos.example.desktopapp.Main; // Add this line
import alunos.example.desktopapp.TableRow;
import alunos.example.desktopapp.dtos.CandidaturaDTO;
import alunos.example.desktopapp.main.RestAPIClientService;
import alunos.example.desktopapp.menu.MenuController;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SubmeterDocTeseController {
  private double height;
  private double width;
  private Stage primaryStage;
  private TableRow currentCandidatura;

  @FXML private HBox topHbox;

  @FXML private Button goBack;

  @FXML private Label title;

  @FXML private TableView<TableRow> table;

  @FXML private Button submeterDocTese;

  @FXML private Button choseFileButton;

  @FXML private VBox middleVBox;

  private byte[] fileBytes;

  public void setUp(Stage primaryStage) {
    this.primaryStage = primaryStage;

    height = Screen.getPrimary().getBounds().getHeight();
    width = Screen.getPrimary().getBounds().getWidth();

    setUpTopHbox();
    setUpSubmeterDocTeseButton();
    setUpGoBackButton();
    setUpChoseFileButton();
    setUpTable();
  }

  private void setUpTopHbox() {
    BorderPane.setMargin(topHbox, new Insets(height * 0.06, 0, 0, width * 0.04));
    HBox.setMargin(title, new Insets(0, 0, 0, width * 0.04));
  }

  private void setUpSubmeterDocTeseButton() {
    submeterDocTese.setPrefSize(width / 20, height / 20);
  }

  private void setUpGoBackButton() {
    goBack.setPrefSize(width / 20, height / 20);
  }

  private void setUpChoseFileButton() {
    // choseFileButton.setPrefSize(width / 20, height / 20);

    choseFileButton.setOnAction(
        e -> {
          // Create a FileChooser
          FileChooser fileChooser = new FileChooser();

          // Show open file dialog
          File file = fileChooser.showOpenDialog(null);

          if (file != null) {
            try {
              // Read file to byte array and assign to fileBytes
              fileBytes = Files.readAllBytes(file.toPath());
            } catch (IOException ex) {
              // Handle exception
              System.out.println("Error reading file: " + ex.getMessage());
            }
          }
        });
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

    // List<CandidaturaDTO> candidaturas = RestAPIClientService.getInstance().listarCandidaturas();

    List<CandidaturaDTO> candidaturas = new ArrayList<>();
    candidaturas.add(new CandidaturaDTO(1, 1, new Date(), "EMPROCESSAMENTO", 1, 1));
    candidaturas.add(new CandidaturaDTO(2, 2, new Date(), "EMPROCESSAMENTO", 2, 2));
    candidaturas.add(new CandidaturaDTO(3, 3, new Date(), "EMPROCESSAMENTO", 3, 3));

    table.getColumns().addAll(tc1, tc2, tc3, tc4, tc5, tc6, tc7);

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
    primaryStage.setWidth(300);
    primaryStage.setX(primaryStage.getX() + 150);
    primaryStage.getScene().setRoot(root);
  }

  @FXML
  public void submeterDocTese() throws Exception {
    if (currentCandidatura == null || fileBytes == null) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao submeter documento de tese");
      alert.setContentText("Por favor selecione uma candidatura e um ficheiro");
      alert.showAndWait();
      return;
    }

    if (RestAPIClientService.getInstance()
            .submeterDocTese(Integer.valueOf(currentCandidatura.getCol1()), fileBytes)
        != null) {
    } else {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Erro");
      alert.setHeaderText("Erro ao submeter um documento na candidatura");
      alert.showAndWait();
    }
  }
}

package alunos.example.desktopapp.list_temas;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ListTemasController {
  private double height;
  private double width;
  private Stage primaryStage;

  @FXML private StackPane pane;

  @FXML private HBox topHbox;

  @FXML private Button goBack;

  @FXML private TableView<TableRow> table;

  public void setUp(Stage primaryStage) {
    this.primaryStage = primaryStage;

    height = Screen.getPrimary().getBounds().getHeight();
    width = Screen.getPrimary().getBounds().getWidth();

    setUpTable();
  }

  private void setUpTable() {
    StackPane.setMargin(
            table, new Insets(height * 0.16, width * 0.08, height * 0.07, width * 0.08));
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<TableRow, String> tc1 = new TableColumn<>("id");
    TableColumn<TableRow, String> tc2 = new TableColumn<>("titulo");
    TableColumn<TableRow, String> tc3 = new TableColumn<>("descricao");
    TableColumn<TableRow, String> tc4 = new TableColumn<>("remuneracaoMensal");
    TableColumn<TableRow, String> tc5 = new TableColumn<>("submissorId");
    TableColumn<TableRow, String> tc6 = new TableColumn<>("mestradosCompativeisId");
    tc1.setCellValueFactory(new PropertyValueFactory<>("col1"));
    tc2.setCellValueFactory(new PropertyValueFactory<>("col2"));
    tc3.setCellValueFactory(new PropertyValueFactory<>("col3"));
    tc4.setCellValueFactory(new PropertyValueFactory<>("col4"));
    tc5.setCellValueFactory(new PropertyValueFactory<>("col5"));
    tc6.setCellValueFactory(new PropertyValueFactory<>("col6"));

    table.getColumns().addAll(tc1, tc2, tc3, tc4, tc5, tc6);

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
      t.setCol4(String.valueOf(tema.getRemuneracaoMensal()));
      t.setCol5(String.valueOf(tema.getSubmissorId()));
      t.setCol6(tema.getMestradosCompativeisId().toString());

      table.getItems().add(t);
    }
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
}

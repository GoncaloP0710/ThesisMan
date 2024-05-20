package alunos.example.desktopapp.list_temas;

import alunos.example.desktopapp.Main;
import alunos.example.desktopapp.TableRow;
import alunos.example.desktopapp.dtos.TemaDTO;
import alunos.example.desktopapp.menu.MenuController;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

  @FXML private Label title;

  @FXML private TableView<TableRow> table;

  public void setUp(Stage primaryStage) {
    this.primaryStage = primaryStage;

    height = Screen.getPrimary().getBounds().getHeight();
    width = Screen.getPrimary().getBounds().getWidth();

    setUpTopHbox();
    setUpGoBackButton();
    setUpTable();
  }

  private void setUpTopHbox() {
    StackPane.setMargin(topHbox, new Insets(height * 0.06, 0, 0, width * 0.04));
    HBox.setMargin(title, new Insets(0, 0, 0, width * 0.04));
  }

  private void setUpGoBackButton() {
    goBack.setPrefSize(width / 20, height / 20);
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

    // TODO: descomentar quando a rest api estiver ligada
    // List<TemaDTO> temas = RestAPIClientService.getInstance().listarTemas();

    List<TemaDTO> temas = new ArrayList<>();
    // TemaDTO(Integer id, String titulo, String descricao, float remuneracaoMensal, Integer
    // submissorId, List<Integer> mestradosCompativeisId)
    TemaDTO tema1 = new TemaDTO(1, "Tema 1", "Descrição do tema 1", 1000, 1, List.of(1, 2));
    TemaDTO tema2 = new TemaDTO(2, "Tema 2", "Descrição do tema 2", 2000, 2, List.of(2, 3));
    TemaDTO tema3 = new TemaDTO(3, "Tema 3", "Descrição do tema 3", 3000, 3, List.of(3, 4));
    temas.add(tema1);
    temas.add(tema2);
    temas.add(tema3);

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
    primaryStage.setX(primaryStage.getX() + 150);
    primaryStage.getScene().setRoot(root);
  }
}

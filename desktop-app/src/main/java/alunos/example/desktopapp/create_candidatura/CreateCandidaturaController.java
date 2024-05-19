package alunos.example.desktopapp.create_candidatura;

import java.util.List;

import alunos.example.desktopapp.TableRow;
import alunos.example.desktopapp.dtos.TemaDTO;
import alunos.example.desktopapp.main.RestAPIClientService;
import alunos.example.desktopapp.menu.MenuController;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class CreateCandidaturaController {
    
	private double height;
	private double width;
	private Stage primaryStage;
	private TableRow currentTema;

	@FXML
	private StackPane pane;

	@FXML
	private HBox topHbox;

	@FXML
	private Button goBack;

	@FXML
	private Button createCandidatura;

	@FXML
	private Label title;

	@FXML
	private TableView<TableRow> table;

	public void setUp(Stage primaryStage) {
		this.primaryStage = primaryStage;

		height = Screen.getPrimary().getBounds().getHeight();
		width = Screen.getPrimary().getBounds().getWidth();

		setUpTopHbox();
		setUpCreateButton();
		setUpGoBackButton();
		setUpTable();
	}

	private void setUpTopHbox() {
		StackPane.setMargin(topHbox, new Insets(height * 0.06, 0, 0, width * 0.04));
		HBox.setMargin(title, new Insets(0, 0, 0, width * 0.04));
	}

	private void setUpCreateButton() {
        createCandidatura.setPrefSize(width / 20, height / 20);
    }

	private void setUpGoBackButton() {
		goBack.setPrefSize(width / 20, height / 20);
	}

	private void setUpTable() {
		StackPane.setMargin(table, new Insets(height * 0.16, width * 0.08, height * 0.07, width * 0.08));
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<TableRow, String> tc1 = new TableColumn<>("id");
		TableColumn<TableRow, String> tc2 = new TableColumn<>("titulo");
		TableColumn<TableRow, String> tc3 = new TableColumn<>("descricao");
		TableColumn<TableRow, String> tc4 = new TableColumn<>("selecionado");

		tc1.setCellValueFactory(new PropertyValueFactory<>("col1"));
		tc2.setCellValueFactory(new PropertyValueFactory<>("col2"));
		tc3.setCellValueFactory(new PropertyValueFactory<>("col3"));
		tc4.setCellValueFactory(new PropertyValueFactory<>("col4"));

		table.getColumns().addAll(tc1, tc2, tc3, tc4);

		List<TemaDTO> temas = RestAPIClientService.getInstance().listarTemas();

		for(TemaDTO tema : temas) {
			TableRow t = new TableRow();
			t.setCol1(String.valueOf(tema.getId()));
            t.setCol2(tema.getTitulo());
            t.setCol3(tema.getDescricao());
			t.setCol4("");

			table.getItems().add(t);
		}

		table.setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				currentTema = table.getSelectionModel().getSelectedItem();
				for (TableRow row : table.getItems()) {
					row.setCol4("");
				}
				table.getSelectionModel().getSelectedItem().setCol4("X");
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
	public void createCandidatura() throws Exception {
		if(currentTema == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Tema não selecionado");
			alert.setContentText("Por favor selecione um tema");
			alert.showAndWait();
			return;
		}

		if(RestAPIClientService.getInstance().createCandidatura(Integer.valueOf(currentTema.getCol1()), null)) {
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro ao criar candidatura");
			alert.showAndWait();
		}
	}


}

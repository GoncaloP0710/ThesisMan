package com.example.desktopapp.list_temas;

import java.util.List;

import com.example.desktopapp.dtos.TemaDTO;
import com.example.desktopapp.Main;
import com.example.desktopapp.main.RestAPIClientService;
import com.example.desktopapp.menu.MenuController;
import com.example.desktopapp.TableRow;

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

	@FXML
	private StackPane pane;

	@FXML
	private HBox topHbox;

	@FXML
	private Button goBack;

	@FXML
	private Label title;

	@FXML
	private TableView<TableRow> table;

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
		StackPane.setMargin(table, new Insets(height * 0.16, width * 0.08, height * 0.07, width * 0.08));
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

		for(TemaDTO tema : temas) {
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

    // TODO: goBack

}

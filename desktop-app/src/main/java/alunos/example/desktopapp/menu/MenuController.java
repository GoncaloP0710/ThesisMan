package alunos.example.desktopapp.menu;

import desktopapp.cancelarCandidatura.CancelarCandidaturaController;
import desktopapp.createCandidatura.CreateCandidaturaController;
import desktopapp.listTemas.ListTemasController;
import desktopapp.main.RestAPIClientService;
import com.example.desktopapp.Main;

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

	@FXML
	private StackPane pane;

	@FXML
	private HBox topHbox;

	@FXML
	private Button logOut;

	@FXML
	private Label title;

	@FXML
	private GridPane menuGrid;

	@FXML
	private Button listTemas;

	@FXML
	private Button createCandidatura;

	@FXML
	private Button cancelarCandidatura;

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

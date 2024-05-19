package alunos.example.desktopapp.login;

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

public class LoginController {
    private double height;
    private double width;
    private Stage primaryStage;

    @FXML
    private BorderPane pane;

    @FXML
    private HBox topHbox;

    @FXML
    private Label title;

    @FXML
    private VBox mainVbox;

    @FXML
    private Label loginLabel;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    public void setUp(Stage primaryStage) {
        this.primaryStage = primaryStage;

        height = Screen.getPrimary().getBounds().getHeight();
        width = Screen.getPrimary().getBounds().getWidth();

        setUpTopHbox();
        setUpLoginButton();
        setUpMainVbox();
        setUpPassword();
        setUpEmail();
    }

    private void setUpTopHbox() {
        BorderPane.setMargin(topHbox, new Insets(height * 0.06, 0, 0, width * 0.04));
        HBox.setMargin(title, new Insets(0, 0, 0, width * 0.04));
    }

    private void setUpLoginButton() {
        loginButton.setPrefSize(width / 20, height / 20);
    }

    private void setUpMainVbox() {
        mainVbox.setMaxHeight(height / 5); // to not interfere with back button
        mainVbox.setSpacing(height * 0.02);
    }

    private void setUpPassword() {
        password.setMaxWidth(width * 0.2);
    }

    private void setUpEmail() {
        email.setMaxWidth(width * 0.2);
    }

	@FXML
	public void loginHandler() throws Exception {
		if(RestAPIClientService.getInstance().logIn(email.getText(), password.getText())){
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/menu.fxml"));
			StackPane root = loader.load();
            // TODO: MenuController
			MenuController controller = loader.<MenuController>getController();
			controller.setUp(primaryStage);
			primaryStage.getScene().setRoot(root);
		}
	}
}

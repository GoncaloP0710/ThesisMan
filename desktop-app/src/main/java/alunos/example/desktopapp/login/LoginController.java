package alunos.example.desktopapp.login;

import alunos.example.desktopapp.Main;
import alunos.example.desktopapp.main.RestAPIClientService;
import alunos.example.desktopapp.menu.MenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

// import org.springframework.http.ResponseEntity;
// import org.springframework.http.HttpStatus;

public class LoginController {
  private double height;
  private double width;
  private Stage primaryStage;

  @FXML private BorderPane pane;

  @FXML private VBox mainVbox;

  @FXML private Label loginLabel;

  @FXML private TextField email;

  @FXML private PasswordField password;

  @FXML private Button loginButton;

  private boolean wasLoggedOut = false;

  public void setUp(Stage primaryStage) {
    this.primaryStage = primaryStage;

    height = Screen.getPrimary().getBounds().getHeight();
    width = Screen.getPrimary().getBounds().getWidth();

    setUpLoginButton();
    setUpMainVbox();
    setUpPassword();
    setUpEmail();
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

    if (RestAPIClientService.getInstance()
            .logIn(email.getText(), password.getText(), getWasLogout())) {
      FXMLLoader loader = new FXMLLoader(Main.class.getResource("/menu.fxml"));
      StackPane root = loader.load();
      MenuController controller = loader.<MenuController>getController();
      controller.setUp(primaryStage);
      // Set the width and height of the Stage
      primaryStage.setHeight(600);
      primaryStage.setWidth(300);
      primaryStage.getScene().setRoot(root);
    }
  }

  public void setWasLogout() {
    this.wasLoggedOut = true;
  }

  public boolean getWasLogout() {
    return wasLoggedOut;
  }
}

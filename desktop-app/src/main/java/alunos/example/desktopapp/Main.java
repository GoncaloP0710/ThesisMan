package alunos.example.desktopapp;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class Main extends  Application{

	// RestThesismanController controller;
    @Override
    public void start(Stage primaryStage) throws Exception {
    	
    	// controller = new RestThesismanController();
    	primaryStage.setResizable(false);

    	primaryStage.setTitle("ThesisMan");
         
        Scene loginScene = loginPage();
        
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public Scene loginPage() {
    	BorderPane root = new BorderPane();
    	
    	Label title = new Label("Login");
    	Label emailLabel = new Label("E-mail:");
    	TextField email = new TextField();
    	Label passwordLabel = new Label("Password:");
    	PasswordField password = new PasswordField();
    	Button btn = new Button("Sign-up");
    	btn.setOnAction(m -> System.out.println(m));
    	
        VBox vbox = new VBox(10);
        
        vbox.getChildren().addAll(emailLabel, email, passwordLabel, password, btn);
        
        for (javafx.scene.Node node : vbox.getChildren()) {
            node.setStyle("-fx-pref-width: 300px; -fx-pref-height: 30px;");
        }
        
        root.setTop(title);
        root.setCenter(vbox);
        Scene scene = new Scene(root, 800, 600);
        return scene;
    }
    
    // public Scene ThemePage() {
    // 	BorderPane root = new BorderPane();
    	
    // 	Label title = new Label("Temas");
    	
    // 	ResponseEntity<?> temas = controller.listarTemas("ola");
    	
    // 	LinkedList<String> stemas = temas.getBody();
    	
    //     Scene scene = new Scene(root, 800, 600);
    //     return scene;
    // }
    
    // public Scene createCandidaturaPage() {
    // 	BorderPane root = new BorderPane();
    	
    	
    // }
 
}

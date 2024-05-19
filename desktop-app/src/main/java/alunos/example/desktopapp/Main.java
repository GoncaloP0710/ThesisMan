package alunos.example.desktopapp;


import alunos.example.desktopapp.login.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends  Application{

	// RestThesismanController controller;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.setTitle("ThesisMan");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        BorderPane root = loader.load();
        
        LoginController controller = loader.getController();
        controller.setUp(primaryStage);

        Scene loginScene = new Scene(root);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
 
}

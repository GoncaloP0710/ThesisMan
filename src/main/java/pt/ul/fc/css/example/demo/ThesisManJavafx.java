package pt.ul.fc.css.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ul.fc.css.example.demo.controllers.RestAPIClientService;
import pt.ul.fc.css.example.demo.controllers.RestThesismanController;
import javafx.scene.layout.BorderPane;

public class ThesisManJavafx extends  Application{

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);
        RestThesismanController controler = new RestThesismanController();
        RestAPIClientService service = RestAPIClientService.getInstance();

        FXMLLoader listLoader = new FXMLLoader();
        root.setCenter(listLoader.load());
        
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

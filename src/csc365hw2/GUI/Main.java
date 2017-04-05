package csc365hw2.GUI;

import csc365hw2.Metrics.KMeans;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/csc365hw2/GUI/GUI.fxml"));
        primaryStage.setTitle("Loader");
        primaryStage.setScene(new Scene(root, 390, 548));
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}

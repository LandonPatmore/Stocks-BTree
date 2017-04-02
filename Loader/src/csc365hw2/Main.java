package csc365hw2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
//        primaryStage.setTitle("Loader");
//        primaryStage.setScene(new Scene(root, 390, 548));
//        primaryStage.setResizable(false);
//        primaryStage.show();

        BTree b = new BTree(4);
        b.insert(1, new Double[]{50.0, 60.0, 70.0});
        b.insert(2, new Double[]{50.0, 60.0, 70.0});
        b.insert(3, new Double[]{50.0, 60.0, 70.0});
        b.insert(4, new Double[]{50.0, 60.0, 70.0});
        b.insert(5, new Double[]{50.0, 60.0, 70.0});
        b.insert(6, new Double[]{50.0, 60.0, 70.0});
        b.insert(7, new Double[]{50.0, 60.0, 70.0});
        b.insert(8, new Double[]{50.0, 60.0, 70.0});
        b.insert(9, new Double[]{50.0, 60.0, 70.0});
        b.insert(10, new Double[]{50.0, 60.0, 70.0});
        b.insert(11, new Double[]{50.0, 60.0, 70.0});
        b.insert(12, new Double[]{50.0, 60.0, 70.0});
        b.insert(13, new Double[]{50.0, 60.0, 70.0});
        b.insert(14, new Double[]{50.0, 60.0, 70.0});
        b.insert(15, new Double[]{50.0, 60.0, 70.0});
        b.insert(16, new Double[]{50.0, 60.0, 70.0});
        b.insert(17, new Double[]{50.0, 60.0, 70.0});
        b.insert(18, new Double[]{50.0, 60.0, 70.0});
        b.showRoot();



    }


    public static void main(String[] args) {
        launch(args);
    }
}

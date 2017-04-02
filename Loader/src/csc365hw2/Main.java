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

//        BTree b = new BTree();
//        b.insert("Lanodn", new Double[]{50.0});
//        b.insert("Apples", new Double[]{50.0});
//        b.insert("Daniel", new Double[]{50.0});
//        b.cache();
//        BTree c;
//        c = b.retrieve();
//        c.showRoot();

        DataPuller d = new DataPuller();
        d.getStockData();



    }


    public static void main(String[] args) {
        launch(args);
    }
}

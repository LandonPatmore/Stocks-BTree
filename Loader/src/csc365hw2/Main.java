package csc365hw2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

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

        DataCacher c = new DataCacher();
        File checkData = new File("data.ser");
        BTree b = new BTree();
        if(checkData.exists()){
            System.out.println("Cache exists.");
            b.insertCacheData(c.retrieveData());
            System.out.println("done");
            b.showRoot();
        } else {
            System.out.println("Cache does not exist...pulling from internet");
            DataPuller d = new DataPuller();
            d.getStockData();
        }



    }


    public static void main(String[] args) {
        launch(args);
    }
}

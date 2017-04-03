package csc365hw2;

import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import java.io.File;

public class GUIController {
    private DataCacher c;
    private BTree b;

    public GUIController(){
        c = new DataCacher();
        b = new BTree();
    }

    @FXML
    private Label tLabel;

    @FXML
    private Label lLoad;

    @FXML
    public void handleButtonClick() throws UnirestException{
        File checkData = new File("data.ser");
        if(checkData.exists()){
            System.out.println("Cache exists.");
            b.insertCacheData(c.retrieveData());
            System.out.println("done");
            tLabel.setText("Cache and Btree already exist!");
            lLoad.setText("Loaded!");
            b.traverse(b.getRoot());
        } else {
            System.out.println("Cache does not exist...pulling from internet");
            DataPuller d = new DataPuller();
            b = d.getStockData();
            tLabel.setText("Data Pulled, Btree created, and Cached!");
            lLoad.setText("Loaded!");
            b.traverse(b.getRoot());
        }
    }
}
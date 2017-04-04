package csc365hw2;

import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

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

    public void getKeysData() throws IOException {
        DataCacher c = new DataCacher();
        b = c.readKeys(new RandomAccessFile("Keys", "rw"), new RandomAccessFile("Data", "rw"));
    }

    @FXML
    public void handleButtonClick() throws UnirestException, IOException {
        File checkData = new File("Keys");
        if(checkData.exists()){
            System.out.println("Cache exists.");
            getKeysData();
            lLoad.setText("Loaded!");
        } else {
            System.out.println("Cache does not exist...pulling from internet");
            DataPuller d = new DataPuller();
            d.getStockData();
            getKeysData();
            tLabel.setText("Data Pulled, Btree created, and Cached!");
        }
    }
}
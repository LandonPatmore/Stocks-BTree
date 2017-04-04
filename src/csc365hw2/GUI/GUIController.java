package csc365hw2.GUI;

import com.mashape.unirest.http.exceptions.UnirestException;
import csc365hw2.Btree.BTree;
import csc365hw2.Caching.DataCacher;
import csc365hw2.Btree.DataPuller;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.sql.Timestamp;

public class GUIController {
    private BTree b;
    private DataCacher c;
    private int aKeys;

    public GUIController() throws FileNotFoundException {
        b = new BTree();
        c = new DataCacher();
    }

    @FXML
    private Label tLabel;

    @FXML
    private Label BtreeLabel;

    @FXML
    private Label lLoad;

    @FXML
    private Label amountKeys;

    @FXML
    private TextField preLoadText;



    public void getKeysData() throws IOException {
        b = c.readKeys(new RandomAccessFile("Keys.ser","rw"), new RandomAccessFile("Data.ser","rw"), Integer.parseInt(preLoadText.getText()));
    }

    public void getAmountKeys() throws IOException {
        aKeys = c.readAmountKeys(new RandomAccessFile("Keys.ser","rw"));
    }

    @FXML
    public void handleCacheCheck() throws UnirestException, IOException {
        Timestamp t = c.readTimeStamp(new RandomAccessFile("Timestamp.ser","rw"));
        Timestamp current = new Timestamp(System.currentTimeMillis());
        t.setTime(t.getTime() + (((60 * 60) * 72) * 1000));

        if(current.after(t)){
            Files.deleteIfExists(FileSystems.getDefault().getPath("Keys.ser"));
            Files.deleteIfExists(FileSystems.getDefault().getPath("Data.ser"));
            Files.deleteIfExists(FileSystems.getDefault().getPath("HashCache.ser"));
        }

        File checkData = new File("Keys.ser");

        if(checkData.exists()){
            tLabel.setText("Cache exists already.");
            getAmountKeys();
            amountKeys.setText("Enter " + aKeys + " or below to preload.");
        } else {
            DataPuller d = new DataPuller();
            d.getStockData();
            tLabel.setText("Data.ser Pulled and Cached!");
            getAmountKeys();
            amountKeys.setText("Enter " + aKeys + " or below to preload.");
        }
    }

    @FXML
    public void handleSubmitClick() throws IOException {
        if(Integer.parseInt(preLoadText.getText()) <= aKeys){
            getKeysData();
            BtreeLabel.setText("Btree preloaded!");
            lLoad.setText("All set!");
        } else {
            BtreeLabel.setText("Not a valid key amount.");
            lLoad.setText("Error");
        }
    }


}
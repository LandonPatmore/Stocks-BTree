package csc365hw2.GUI;

import com.mashape.unirest.http.exceptions.UnirestException;
import csc365hw2.Btree.BTree;
import csc365hw2.Btree.Data;
import csc365hw2.Caching.DataCacher;
import csc365hw2.Btree.DataPuller;
import csc365hw2.Metrics.KMeans;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Controller for the GUI
 */

public class GUIController {
    private BTree b;
    private DataCacher c;
    private DataPuller d;
    private KMeans k;
    private int aKeys;

    /**
     * Initializer that is run when the GUI is created
     * @throws FileNotFoundException
     */
    public void initialize() throws FileNotFoundException {
        b = new BTree();
        c = new DataCacher();
        d = new DataPuller();
        k = new KMeans();
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

    @FXML
    private ComboBox<String> listBox;

    @FXML
    private ListView<Data> closest;


    /**
     * Used to get keys and values to then create the BTree from the Cached Data
     * @throws IOException
     */
    public void getKeysData() throws IOException {
        b = c.readKeys(new RandomAccessFile("Keys.ser", "rw"), Integer.parseInt(preLoadText.getText()));
    }

    /**
     * Used to get the amount of keys to allow for custom preloading
     * @throws IOException
     */
    public void getAmountKeys() throws IOException {
        aKeys = c.readAmountKeys(new RandomAccessFile("Keys.ser", "rw"));
    }

    /**
     * Removes the files when the cache is invalidated
     * @throws IOException
     */
    public void removeFiles() throws IOException {
        Files.deleteIfExists(FileSystems.getDefault().getPath("Keys.ser"));
        Files.deleteIfExists(FileSystems.getDefault().getPath("Data.ser"));
        Files.deleteIfExists(FileSystems.getDefault().getPath("HashCache.ser"));
    }

    /**
     * Checks to see if the cache exists and if it does, read the RandomAccessFiles, and if not, pull the data and
     * cache it
     * @throws UnirestException
     * @throws IOException
     */
    @FXML
    public void handleCacheCheck() throws UnirestException, IOException {
        File checkData = new File("Keys.ser");

        if(checkData.exists()){
            tLabel.setText("Cache exists already.");

            Timestamp t = c.readTimeStamp(new RandomAccessFile("Timestamp.ser", "rw"));
            Timestamp current = new Timestamp(System.currentTimeMillis());
            t.setTime(t.getTime() + (((60 * 60) * 72) * 1000));

            if(current.after(t)){
                tLabel.setText("Cache invalidated.  Pulling Data...");
                removeFiles();
                d.getStockData();
            }

            getAmountKeys();
            amountKeys.setText("Enter " + aKeys + " or below to preload.");
        } else {
            d.getStockData();
            tLabel.setText("Pulled and Cached!");
            getAmountKeys();
            amountKeys.setText("Enter " + aKeys + " or below to preload.");
        }

    }

    /**
     * Checks to see if the user has entered a proper number and if they have, then the BTree is preloaded with the
     * amount of keys specified
     * @throws IOException
     */
    @FXML
    public void handleSubmitClick() throws IOException {
        int userInput = Integer.parseInt(preLoadText.getText());
        if(userInput <= aKeys){
            getKeysData();
            BtreeLabel.setText("Btree preloaded!");

            ArrayList<String> numClusters = new ArrayList<>();
            for(int i = 0; i < k.getNUM_CLUSTERS(); i++){
                numClusters.add(String.valueOf(i));
            }

            ObservableList<String> obList = FXCollections.observableList(numClusters);
            listBox.setItems(obList);
            listBox.getSelectionModel().selectFirst();

            lLoad.setText("All set!");
        } else {
            BtreeLabel.setText("Not a valid key amount.");
            lLoad.setText("Error");
        }

    }

    /**
     * Used to show the similar keys to the current one selected in the Combobox
     */
    @FXML
    public void handleListSimilaritySubmit() throws IOException {
        b.traverse(b.getRoot());
        k.addPoints(b.getPoints());
        k.init();
        k.calculate();

        System.out.println(k.getClusters().get(1).getId());



//        b.getClosest().clear();
//
//        String output = listBox.getSelectionModel().getSelectedItem();
//        b.traverse(b.getRoot(), output);
//
//        ObservableList<Data> items =FXCollections.observableArrayList (b.getClosest());
//
//        closest.setItems(items);
    }




}
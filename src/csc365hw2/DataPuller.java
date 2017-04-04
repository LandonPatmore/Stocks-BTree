package csc365hw2;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by landon on 3/23/17.
 */

/**
 * Custom class to pull data from stock site
 */
public class DataPuller {
    private String URL = "https://www.quandl.com/api/v3/datatables/WIKI/PRICES.json?date.gte=20110101" +
            "&date.lt=20160101&ticker=MSFT,FB,GOOGL,INTC,CSCO,AAPL,AMZN,AMD&";
    private String KEY = "api_key=aWGH5wHqiKkFgKFSSEuB";
    private DataCacher c;
    private BTree b;
    private NodeData n;
    private RandomAccessFile keys;
    private RandomAccessFile values;

    /**
     * creates a new ArrayList when instantiated
     */

    public DataPuller() throws FileNotFoundException {
        c = new DataCacher();
        b = new BTree();
        keys = new RandomAccessFile("Keys", "rw");
        values = new RandomAccessFile("Data", "rw");
    }

    /**
     * Pulls down Stock Info and then parses it into an ArrayList of KeyVals
     * @return ArrayList of KeyVals for GUI
     * @throws UnirestException in the case that Unirest can't reach the server for any reason so the app does not crash
     */

    public void getStockData() throws UnirestException {
        HttpResponse<JsonNode> jsonResponse;
        try {
            jsonResponse = Unirest.get(URL + KEY)
                    .header("Accept", "application/json")
                    .asJson();

            c.add(URL + KEY, jsonResponse.getBody().toString());

            JSONArray data = jsonResponse.getBody().getObject().getJSONObject("datatable").getJSONArray("data");

            c.cacheData();

            for (int i = 0; i < data.length(); i++) {
                String key = data.getJSONArray(i).get(0) + " " + data.getJSONArray(i).get(1);
                Double[] info = new Double[5];
                for (int j = 2; j <= 6; j++) {
                    info[j - 2] = data.getJSONArray(i).getDouble(j);
                }
                c.writeKeys(keys, key, c.writeValues(values, info));
            }
        } catch (UnirestException | IOException e) {
            e.printStackTrace();
        }
    }

}

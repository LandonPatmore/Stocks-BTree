package csc365hw2.Btree;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import csc365hw2.Caching.DataCacher;
import csc365hw2.Metrics.KMeans;
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
    private DataCacher c;
    private int amountKeys;
    private RandomAccessFile keys;
    private RandomAccessFile values;
    private RandomAccessFile timestamp;

    /**
     * Constructor for DataPuller
     */

    public DataPuller() throws FileNotFoundException {
        c = new DataCacher();
    }

    /**
     * Gets the data, parses it, puts it into seperate files based on Keys and Values and also records a timestamp
     * for Cache reloading
     * @throws UnirestException if the site cannot be reached
     */
    public void getStockData() throws UnirestException, FileNotFoundException {
        keys = new RandomAccessFile("Keys.ser", "rw");
        values = new RandomAccessFile("Data.ser", "rw");
        timestamp = new RandomAccessFile("Timestamp.ser", "rw");
        HttpResponse<JsonNode> jsonResponse;

        try {
            String KEY = "api_key=aWGH5wHqiKkFgKFSSEuB";
            String URL = "https://www.quandl.com/api/v3/datatables/WIKI/PRICES.json?date.gte=20110101" +
                    "&date.lt=20160101&ticker=MSFT,FB,GOOGL,INTC,CSCO,AAPL,AMZN,AMD&";
            jsonResponse = Unirest.get(URL + KEY)
                    .header("Accept", "application/json")
                    .asJson();

            c.add(URL + KEY, jsonResponse.getBody().toString());

            JSONArray data = jsonResponse.getBody().getObject().getJSONObject("datatable").getJSONArray("data");

            c.cacheData();

            for (int i = 0; i < data.length(); i++) {
                String key = data.getJSONArray(i).get(0) + " " + data.getJSONArray(i).get(1);
                Double[] info = new Double[2];
                info[0] = data.getJSONArray(i).getDouble(2);
                info[1] = data.getJSONArray(i).getDouble(6);
                amountKeys++;
                c.writeKeys(keys, key, c.writeValues(values, info));
            }
            c.writeAmountKeys(keys, amountKeys);
            c.writeTimestamp(timestamp, System.currentTimeMillis());
        } catch (UnirestException | IOException e) {
            e.printStackTrace();
        }
    }

}

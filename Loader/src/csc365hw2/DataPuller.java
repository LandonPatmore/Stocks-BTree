package csc365hw2;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;

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
    private Cacher c;

    /**
     * creates a new ArrayList when instantiated
     */

    public DataPuller() {
        c = new Cacher();

    }

    /**
     * Pulls down Stock Info and then parses it into an ArrayList of KeyVals
     * @return ArrayList of KeyVals for GUI
     * @throws UnirestException in the case that Unirest can't reach the server for any reason so the app does not crash
     */

    public Cacher getStockData() throws UnirestException {
        HttpResponse<JsonNode> jsonResponse;
        try {
            jsonResponse = Unirest.get(URL + KEY)
                    .header("Accept", "application/json")
                    .asJson();

            JSONArray data = jsonResponse.getBody().getObject().getJSONObject("datatable").getJSONArray("data");

            c.add(URL + KEY,data.toString());
            c.cache();

            return c;
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }

}

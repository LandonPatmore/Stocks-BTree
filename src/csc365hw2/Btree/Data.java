package csc365hw2.Btree;

/**
 * Created by landon on 4/3/17.
 */

import csc365hw2.Caching.DataCacher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Data class
 */
public class Data implements Comparable<Data>{
    private String key;
    private long dataPosition;
    private DataCacher d;
    private double distance;
    private DecimalFormat df;

    /**
     * Constructor to create a Data Object
     * @param k - String key
     * @param v - Double[] values
     */
    public Data(String k, long dPos) throws FileNotFoundException {
        key = k;
        dataPosition = dPos;
        distance = 0.0;
        d = new DataCacher();
        df = new DecimalFormat("#.00");
    }

    /**
     *
     * @return String of key
     */
    public String getKey() {
        return key;
    }

    /**
     *
     * @return Double[] of values
     */
    public Double[] getValues() throws IOException {
        return d.readValues(new RandomAccessFile("Data.ser", "rw"), dataPosition);
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * Custom compareTo method to comapre two Data Objects against each other for sorting
     * @param o - Data Object
     * @return Integer of whether Data Object is before or after another Data Object
     */
    public int compareTo(Data o) {
        return this.getKey().compareTo(o.getKey());
    }

    @Override
    public String toString() {
        return key + " ||||| " + df.format(distance);
    }
}

package csc365hw2.Caching;

import csc365hw2.Btree.BTree;
import csc365hw2.Btree.Data;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by landon on 3/23/17.
 */


/**
 * Class used to Cache and Read the data pulled down from API
 */
public class DataCacher {
    private HashMap<String, String> map = new HashMap<>();
    private ArrayList<String> keyStrings = new ArrayList<>();

    /**
     *
     * @param url - String url
     * @param response - String raw response from API
     */
    public void add(String url, String response){
        map.put(url, response);
    }

    /**
     * Caches the raw data pulled from the API
     */
    public void cacheData(){
        try {
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("HashCache.ser"));
            o.writeObject(map);
//            o.close();
        } catch (IOException e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    /**
     * Not implemented because couldn't find use for it.
     * @return The full String of Raw Data
     */
    public String retrieveData(){
        try{
            ObjectInputStream o = new ObjectInputStream(new FileInputStream("HashCache.ser"));
            HashMap<String, String> h = (HashMap<String, String>) o.readObject();
            o.close();

            Map.Entry<String, String> entry = h.entrySet().iterator().next();

            return entry.getValue();
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Error");
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param r - RandomAccessFile
     * @param t - Current system time
     * @throws IOException
     */
    public void writeTimestamp(RandomAccessFile r, long t) throws IOException {
        r.writeLong(t);
    }

    /**
     *
     * @param r - RandomAccessFile
     * @return The Timestamp stored in the Timestamp RandomAccessFile
     * @throws IOException
     */
    public Timestamp readTimeStamp(RandomAccessFile r) throws IOException {
        r.seek(0);
        Timestamp t = new Timestamp(r.readLong());
        r.close();
        return t;
    }

    /**
     *
     * @param r - RandomAccessFile
     * @param v - Double[] values
     * @return The Long pointer of the RandomAccessFile
     * @throws IOException
     */
    public long writeValues(RandomAccessFile r, Double[] v) throws IOException{
        for(Double d : v) {
            r.writeDouble(d);
        }
        return r.getFilePointer() - (Double.BYTES * 5);
    }

    /**
     *
     * @param r - RandomAccessFile
     * @param pos - The Long position of the Double Value
     * @return Double[] values
     * @throws IOException
     */
    public Double[] readValues(RandomAccessFile r, long pos) throws IOException{
        r.seek(pos);
        Double[] d = new Double[5];
        int i = 0;
        while(r.getFilePointer() < pos + (Double.BYTES * 5)){
            d[i] = r.readDouble();
            i++;
        }
        r.close();
        return d;
    }

    /**
     *
     * @param keys - RandomAccessFile
     * @param amountKeys - The Integer amount of keys
     * @throws IOException
     */
    public void writeAmountKeys(RandomAccessFile r, int amountKeys) throws IOException {
        r.writeInt(amountKeys);
    }

    /**
     *
     * @param keys - RandomAccessFile
     * @return Integer of amount of keys in BTree
     * @throws IOException
     */
    public int readAmountKeys(RandomAccessFile r) throws IOException {
        long amountPos = r.length() - Integer.BYTES;
        r.seek(amountPos);
        int kAmount = r.readInt();
        r.close();
        return kAmount;
    }

    /**
     *
     * @param keys - RandomAccessFile
     * @param k - String key
     * @param dPos- Long position of Values in Data file
     * @throws IOException
     */
    public void writeKeys(RandomAccessFile r, String k, long dPos) throws IOException{
        r.writeUTF(k);
        r.writeLong(dPos);
    }

    /**
     *
     * @param keys - RandomAccessFile
     * @param data - RandomAccessFile
     * @param preloadAmount - Integer amount of keys to pull out of RandomAccessFile
     * @return BTree created from keys
     * @throws IOException
     */
    public BTree readKeys(RandomAccessFile r, int preloadAmount) throws IOException{
        BTree b = new BTree();
        r.seek(0);
        String s;
        int i = 0;
        while(i < preloadAmount){
            s = r.readUTF();
            keyStrings.add(s);
            b.insert(new Data(s, r.readLong()));
            i++;
        }
        r.close();
        return b;
    }

    public ArrayList<String> getKeyStrings(){
        return keyStrings;
    }
}

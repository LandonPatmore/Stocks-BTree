package csc365hw2.Caching;

import csc365hw2.Btree.BTree;
import csc365hw2.Btree.NData;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by landon on 3/23/17.
 */


public class DataCacher {
    private HashMap<String, String> map = new HashMap<>();

    public DataCacher(){
    }

    public void add(String url, String response){
        map.put(url, response);
    }

    public void cacheData(){
        try {
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("HashCache.ser"));
            o.writeObject(map);
            o.close();
            System.out.println("Data.ser Serialized");
        } catch (IOException e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public String retrieveData(){
        try{
            ObjectInputStream o = new ObjectInputStream(new FileInputStream("HashCache.ser"));
            HashMap<String, String> h = (HashMap<String, String>) o.readObject();
            o.close();

            System.out.println("Data.ser Deserialized");
            Map.Entry<String, String> entry = h.entrySet().iterator().next();

            return entry.getValue();
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Error");
            e.printStackTrace();
        }
        return null;
    }

    public void writeTimestamp(RandomAccessFile r, long t) throws IOException {
        r.writeLong(t);
    }

    public Timestamp readTimeStamp(RandomAccessFile r) throws IOException {
        r.seek(0);
        return new Timestamp(r.readLong());
    }

    public long writeValues(RandomAccessFile r, Double[] v) throws IOException{
        for(Double d : v){
            r.writeDouble(d);
        }
        return r.getFilePointer() - (Double.BYTES * 5);
    }

    public Double[] readValues(RandomAccessFile r, long pos) throws IOException{
        r.seek(pos);
        Double[] d = new Double[5];
        int i = 0;
        while(r.getFilePointer() < pos + (Double.BYTES * 5)){
            d[i] = r.readDouble();
            i++;
        }
        return d;
    }

    public void writeAmountKeys(RandomAccessFile keys, int amountKeys) throws IOException {
        keys.writeInt(amountKeys);
    }

    public int readAmountKeys(RandomAccessFile keys) throws IOException {
        long amountPos = keys.length() - Integer.BYTES;
        keys.seek(amountPos);
        return keys.readInt();
    }

    public void writeKeys(RandomAccessFile keys, String k, long dPos) throws IOException{
        keys.writeUTF(k);
        keys.writeLong(dPos);
    }

    public BTree readKeys(RandomAccessFile keys, RandomAccessFile data, int preloadAmount) throws IOException{
        BTree b = new BTree();
        keys.seek(0);
        String s;
        long l;
        int i = 0;
        while(i < preloadAmount){
            s = keys.readUTF();
            l = keys.readLong();
            b.insert(new NData(s, readValues(data, l)));
            i++;
        }
        return b;
    }
}

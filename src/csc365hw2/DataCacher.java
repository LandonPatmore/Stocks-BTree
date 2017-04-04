package csc365hw2;

import java.io.*;
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
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("data.ser"));
            o.writeObject(map);
            o.close();
            System.out.println("Data Serialized");
        } catch (IOException e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public String retrieveData(){
        try{
            ObjectInputStream o = new ObjectInputStream(new FileInputStream("data.ser"));
            HashMap<String, String> h = (HashMap<String, String>) o.readObject();
            o.close();

            System.out.println("Data Deserialized");
            Map.Entry<String, String> entry = h.entrySet().iterator().next();

            return entry.getValue();
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Error");
            e.printStackTrace();
        }
        return null;
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

    public void writeKeys(RandomAccessFile keys, String k, long dPos) throws IOException{
        keys.writeUTF(k);
        keys.writeLong(dPos);
    }

    public BTree readKeys(RandomAccessFile keys, RandomAccessFile data) throws IOException{
        BTree b = new BTree();
        keys.seek(0);
        String s;
        long l;
        int i = 0;
        while(i < 100){
            s = keys.readUTF();
            l = keys.readLong();
            b.insert(new NodeData(s, readValues(data, l)));
            i++;
        }
        return b;
    }
}

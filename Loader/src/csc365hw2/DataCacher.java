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

    public void cacheBtree(BTree b){
        try {
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("Btree.ser"));
            o.writeObject(b);
            o.close();
            System.out.println("Btree Serialized");
        } catch (IOException e){
            System.out.println("Btree Error");
            e.printStackTrace();
        }
    }

    public BTree retrieveBtree(){
        try{
            ObjectInputStream o = new ObjectInputStream(new FileInputStream("Btree.ser"));
            BTree b = (BTree) o.readObject();
            o.close();

            System.out.println("Btree Deserialized");
            return b;
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Error");
            e.printStackTrace();
        }
        return null;
    }






}

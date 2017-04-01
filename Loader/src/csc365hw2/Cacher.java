package csc365hw2;

import java.io.*;
import java.util.*;

/**
 * Created by landon on 3/23/17.
 */


public class Cacher{
    private HashMap<String, String> map;

    public Cacher(){
        map = new HashMap<>();
    }

    public void add(String url, String response){
        map.put(url, response);
    }

    public void cache(){
        try {
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("cache.ser"));
            o.writeObject(map);
            o.close();
            System.out.println("Serialized");
        } catch (IOException e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public String retrieve(){
        HashMap<String, String> des;

        try{
            ObjectInputStream o = new ObjectInputStream(new FileInputStream("cache.ser"));
            //get checked
            des = (HashMap<String, String>) o.readObject();
            o.close();


            Map.Entry<String, String> entry = des.entrySet().iterator().next();

            return entry.getValue();
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }




}

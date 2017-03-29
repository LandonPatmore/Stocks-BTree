package csc365hw2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by landon on 3/23/17.
 */
public class Cacher implements Serializable{
    private HashMap<String, Double[]> map;

    public Cacher(){
        map = new HashMap<String, Double[]>();
    }

    public void add(KeyVal keyVal){
        map.put(keyVal.getKey(), keyVal.getVal());
    }

    public void cache(){
        try {
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("cache.ser"));
            o.writeObject(map);
            o.close();
            System.out.println("Serialized");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void retrieve(){
        System.out.println("Deserialized HashMap..");
        // Display content using Iterator
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            System.out.print("key: "+ mentry.getKey() + " & Value: ");
            System.out.println(mentry.getValue());
        }
    }




}

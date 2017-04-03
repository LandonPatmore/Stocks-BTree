package csc365hw2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.Arrays;

/**
 * Created by landon on 3/23/17.
 */

public class BTree implements Serializable {

    private Node root;
    private int minDegree;

    public BTree() {
        this.minDegree = 16;
        root = null;
    }

    public Node getRoot() {
        return root;
    }

    public void insertCacheData(String cache){
        JSONObject obj = new JSONObject(cache);

        JSONArray cacheData = obj.getJSONObject("datatable").getJSONArray("data");

        for (int i = 0; i < cacheData.length(); i++) {
            String key;
            key = cacheData.getJSONArray(i).get(0) + " " + cacheData.getJSONArray(i).get(1);
            Double[] info = new Double[5];
            for (int j = 2; j <= 6; j++) {
                info[j - 2] = cacheData.getJSONArray(i).getDouble(j);
            }

            insert(key, info);
        }
    }

    public void traverse(Node p){
        int i;
        for(i = 0; i < p.getKeys(); i++){
            if(!p.isLeaf()){
                traverse(p.getChildren()[i]);
            }
            System.out.println(p.getData()[i].getKey());
        }

        if(!p.isLeaf()){
            traverse(p.getChildren()[i]);
        }
    }

    public void insert(String k, Double[] v) {
        if (root == null) {
            root = new Node(true);
            root.insertData(new Data(k, v));
        } else {
            if (root.isFull()) {
                Node s = new Node(false);
                s.insertChildren(root);
                root = s;
                split(s, 0);
                insertNonFull(s, new Data(k, v));

            } else {
                insertNonFull(root, new Data(k, v));
            }
        }
    }

    public void insertNonFull(Node x, Data d) {
        int i = x.getKeys() - 1;

        if(x.isLeaf()){
            x.insertData(d);
        } else {
            while(i > 0 && d.getKey().compareTo(x.getData()[i - 1].getKey()) < 0){
                i -= 1;
            }
            i += 1;

            if(x.getChildren()[i].isFull()){
                split(x, i);
                if(d.getKey().compareTo(x.getData()[i].getKey()) > 0){
                    i += 1;
                }
            }
            insertNonFull(x.getChildren()[i], d);
        }
    }

    public void split(Node parent, int i) {
        Node left = parent.getChildren()[i];
        Node right = new Node(left.isLeaf());
        int median = left.medianKey();

        parent.insertData(left.getData()[median]);

        for (int j = 1; j <= median; j++) {
            right.insertData(left.getData()[j + median]);
        }

        for (int j = left.getKeys() - 1; j >= median; j--) {
            left.removeData(j);
        }

        if (!left.isLeaf()) {
            for (int j = 1; j <= median + 1; j++) {
                right.insertChildren(left.getChildren()[j + median]);
            }

            for (int j = left.getCh() - 1; j >= median + 1; j--) {
                left.removeChildren(j);
            }
        }

        parent.insertChildren(right);
    }

    public String search(Node n, String k) {
        int i = 0;

        while (i < n.getKeys() && k.compareTo(n.data[i].getKey()) > 0) {
            i += 1;
        }
        if (i < n.getKeys() && k.compareTo(n.data[i].getKey()) == 0) {
            return Arrays.toString(n.data[i].getValues());
        } else if (n.isLeaf()) {
            return null;
        } else {
            return search(n.children[i], k);
        }
    }

    public void showRoot() {
        for (Data d : root.getData()) {
            if (d != null) {
                System.out.println(d.getKey());
            }
        }
    }

    public class Data implements Comparable<Data>, Serializable {
        private String key;
        private Double[] values;

        private Data(String k, Double[] v) {
            key = k;
            values = v;
        }

        public String getKey() {
            return key;
        }

        public Double[] getValues() {
            return values;
        }

        public int compareTo(Data o) {
            return this.getKey().compareTo(o.getKey());
        }
    }

    public class Node implements Serializable {
        private Data[] data;
        private Node[] children;
        private Integer keys;
        private Integer ch;
        private boolean isLeaf;

        private Node(boolean leaf) {
            data = new Data[2 * minDegree - 1];
            children = new Node[2 * minDegree];
            keys = 0;
            ch = 0;
            isLeaf = leaf;
        }

        private Node[] getChildren() {
            return children;
        }

        private Data[] getData() {
            return data;
        }

        private boolean isFull() {
            return keys == ((2 * minDegree) - 1);
        }

        private boolean isLeaf() {
            return isLeaf;
        }

        public int getKeys() {
            return keys;
        }

        private void insertData(Data d) {
            data[keys] = d;
            keys++;
            sortData();
        }

        private void insertChildren(Node n) {
            children[ch] = n;
            ch++;
        }

        private void removeChildren(int i) {
            children[i] = null;
            ch--;
        }

        private int getCh() {
            return ch;
        }

        private void removeData(int i) {
            data[i] = null;
            keys--;
        }

        public int medianKey() {
            return getData().length / 2;
        }

        private void sortData() {
            Arrays.sort(getData(), 0, keys);
        }
    }
}

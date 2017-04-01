package csc365hw2;

import java.util.Arrays;

/**
 * Created by landon on 3/23/17.
 */

public class BTree {

    private Node root;
    private int minDegree;

    public BTree(int minDegree){
        this.minDegree = minDegree;
        root = null;
    }

    public Node getRoot(){
        return root;
    }

    public void insert(String k, Double[] v){
        if(root == null){
            root = new Node(true);
            root.insert(new Data(k, v));
        } else {
            if(root.isFull()){
                Node r = root;
                Node s = new Node(false);
                root = s;
                s.getChildren()[0] = r;
                split(s,0);
                insertNonFull(s, new Data(k,v));

            }
            insertNonFull(root, new Data(k,v));
        }
    }

    public void insertNonFull(Node x, Data d){
        int i = x.getKeys() - 1;

        if(x.isLeaf()){
            x.insert(d);
        } else{
            while(i >= 1 && d.getKey().compareTo(x.getData()[i].getKey()) < 0){
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

    public void split(Node x, int i){
        Node y = x.children[i];
        Node z = new Node(y.isLeaf());
        int median = z.medianKey();

        z.setLeaf(y.isLeaf());
        z.setKeys(median);

        for(int j = 0; j < median; j++){
            z.getData()[j] = y.getData()[j + median];
        }

        if(!y.isLeaf()){
            for(int j = 0; j < median; j++){
                z.getChildren()[j] = y.getChildren()[j + median];
            }
        }

        y.setKeys(median);

        for(int j = x.getKeys() + 1; j > i + 1; j--){
            x.getChildren()[j + 1] = x.getChildren()[j];
        }
        x.getChildren()[i + 1] = z;

        for(int j = x.getKeys(); j > i; j--){
            x.getData()[j + 1] = x.getData()[j];
        }
        x.getData()[i] = y.getData()[i];
        x.setKeys(x.getKeys() + 1);
    }

    public String search(Node n, String k){
        int i = 0;

        while (i <= n.getKeys() && k.compareTo(n.data[i].getKey()) > 0){
            i += 1;
        }
        if(i <= n.getKeys() && k.equals(n.data[i].getKey())){
            return n.data[i].getKey();
        } else if (n.isLeaf()){
            return null;
        } else{
            return search(n.children[i], k);
        }
    }

    public void showRoot(){
        for(Data d : root.getData()){
            if(d != null) {
                System.out.println(d.getKey());
            }
        }
    }

    public class Data implements Comparable<Data>{
        private String key;
        private Double[] values;

        private Data(String k, Double[] v){
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

    public class Node {
        private Data[] data;
        private Node[] children;
        private int keys;
        private boolean isLeaf;

        private Node(boolean leaf){
            data = new Data[2 * minDegree - 1];
            children = new Node[2 * minDegree];
            keys = 0;
            isLeaf = leaf;
        }

        private Node[] getChildren(){
            return children;
        }

        private Data[] getData() {
            return data;
        }

        private boolean isFull(){
            return keys == ((2 * minDegree) - 1);
        }

        private boolean isLeaf() {
            return isLeaf;
        }

        private void setLeaf(boolean leaf) {
            isLeaf = leaf;
        }

        private void setKeys(int keys) {
            this.keys = keys;
        }

        public int getKeys() {
            return keys;
        }

        private void insert(Data d){
            data[keys] = d;
            keys++;
            sortData();
        }

        public int medianKey(){
            return getData().length / 2;
        }

        private void sortData(){
            Arrays.sort(getData(), 0, keys);
        }
    }
}

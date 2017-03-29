package csc365hw2;

import java.util.Arrays;

/**
 * Created by landon on 3/23/17.
 */
public class BTree {

    private Node root;
    private int minDegree;

    public BTree(int d){
        minDegree = d;
        root = null;
    }

    public void insert(String k, Double[] v){
        if(root == null){
            root = new Node(true);
            root.insert(new Data(k, v));
        } else {
            root.insert(new Data(k, v));
        }
    }

    public void insert(){
        
    }

    public void split(){

    }

    public void get(){

    }

    public void showRoot(){
        for(Data d : root.getData()){
            System.out.println(d.getKey());
        }
    }

    private class Data implements Comparable<Data>{
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

    private class Node {
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

        private void insert(Data d){
            data[keys] = d;
            keys++;
            sortData();
        }

        private void sortData(){
            Arrays.sort(getData(), 0, keys);
        }
    }



}

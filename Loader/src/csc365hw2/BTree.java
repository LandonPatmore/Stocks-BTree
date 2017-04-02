package csc365hw2;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by landon on 3/23/17.
 */

public class BTree implements Serializable{

    private Node root;
    private int minDegree;

    public BTree(int minDegree){
        this.minDegree = minDegree;
        root = null;
    }

    public Node getRoot(){
        return root;
    }

    public void insert(Integer k, Double[] v){
        if(root == null){
            root = new Node(true);
            root.insertData(new Data(k, v));
        } else {
            if(root.isFull()){
                Node s = new Node(false);
                s.getChildren()[0] = root;
                root = s;
                split(s,0);
                insertNonFull(s, new Data(k,v));

            } else {
                insertNonFull(root, new Data(k, v));
            }
        }
    }

    public void insertNonFull(Node x, Data d){
        int i = x.getKeys() - 1;

        if(x.isLeaf()){
            x.insertData(d);
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

    public void split(Node parent, int i){
        Node left = parent.children[i];
        Node right = new Node(left.isLeaf());
        int median = left.medianKey();

        parent.insertData(left.getData()[median]);

        for(int j = 1; j <= median; j++){
            right.insertData(left.getData()[j + median]);
        }

        for(int j = left.getKeys() - 1; j >= median; j--){
            left.removeData(j);
        }

        if(!left.isLeaf()){
            for(int j = median; j < left.getChildren().length - 1; j++){
                right.getChildren()[j - 1] = left.getChildren()[j+median];
                left.getChildren()[j+median] = null;
            }
        }

        parent.getChildren()[i + 1] = right;
    }

    public Integer search(Node n, Integer k){
        int i = 0;

        while (i <= n.getKeys() && k > n.data[i].getKey()){
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
        private Integer key;
        private Double[] values;

        private Data(Integer k, Double[] v){
            key = k;
            values = v;
        }

        public Integer getKey() {
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
        private Integer keys;
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

        public int getKeys() {
            return keys;
        }

        private void insertData(Data d){
            data[keys] = d;
            keys++;
            sortData();
        }

        private void removeData(int i){
            data[i] = null;
            keys--;
        }

        public int medianKey(){
            return getData().length / 2;
        }

        private void sortData(){
            Arrays.sort(getData(), 0, keys);
        }
    }
}

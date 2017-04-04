package csc365hw2.Btree;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by landon on 3/23/17.
 */

public class BTree{

    private Node root;
    private static int minDegree;
    private int height;
    private ArrayList<String> closest;

    public BTree() {
        minDegree = 16;
        root = null;
        closest = new ArrayList<>();
    }

    public Node getRoot() {
        return root;
    }

    public void traverse(Node p, String k){
        int i;
        for(i = 0; i < p.getKeys(); i++){
            if(!p.isLeaf()){
                traverse(p.getChildren()[i], k);
            }
            ManhattanDistance(p.getData()[i].getKey(),search(getRoot(), k), p.getData()[i].getValues());
        }

        if(!p.isLeaf()){
            traverse(p.getChildren()[i], k);
        }
    }

    public void insert(NData n) {
        if (root == null) {
            root = new Node(true);
            root.insertData(n);
            height++;
        } else {
            if (root.isFull()) {
                Node s = new Node(false);
                s.insertChildren(root);
                root = s;
                split(s, 0);
                insertNonFull(s, n);
                height++;
            } else {
                insertNonFull(root, n);
            }
        }
    }

    public int getHeight(){
        return height;
    }

    public void insertNonFull(Node x, NData d) {
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

    public Double[] search(Node n, String k) {
        int i = 0;

        while (i < n.getKeys() && k.compareTo(n.data[i].getKey()) > 0) {
            i += 1;
        }
        if (i < n.getKeys() && k.compareTo(n.data[i].getKey()) == 0) {
            return n.data[i].getValues();
        } else if (n.isLeaf()) {
            return null;
        } else {
            return search(n.children[i], k);
        }
    }

    private void ManhattanDistance(String k, Double[] x, Double[] y){
        Double check = 200000.0;
        Double sumx, sumy;
        sumx = sumy = 0.0;

        for(int i = 0; i < x.length; i++){
            sumx += x[i];
            sumy += y[i];
        }

        Double distance = Math.abs(sumx - sumy);

        if(distance < check){
            closest.add(k);
        }
    }

    public ArrayList<String> getClosest(){
        return closest;
    }

    public void showRoot() {
        for (NData d : root.getData()) {
            if (d != null) {
                System.out.println(d.getKey());
            }
        }
    }

    public static class Node {
        private NData[] data;
        private Node[] children;
        private Integer keys;
        private Integer ch;
        private boolean isLeaf;

        private Node(boolean leaf) {
            data = new NData[2 * minDegree - 1];
            children = new Node[2 * minDegree];
            keys = 0;
            ch = 0;
            isLeaf = leaf;
        }

        private Node[] getChildren() {
            return children;
        }

        private NData[] getData() {
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

        private void insertData(NData d) {
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

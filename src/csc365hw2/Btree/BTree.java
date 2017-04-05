package csc365hw2.Btree;

import csc365hw2.Metrics.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by landon on 3/23/17.
 */

/**
 * BTree class
 */
public class BTree{

    private Node root;
    private static int minDegree;
    private int height;
    private ArrayList<Point> points;

    /**
     * Constructor to create a BTree
     */
    public BTree() {
        minDegree = 16;
        root = null;
        points = new ArrayList<>();
    }

    /**
     *
     * @return The root of the tree
     */
    public Node getRoot() {
        return root;
    }

    /**
     *
     * @param p - The Node to start from to traverse down a subtree
     */
    public void traverse(Node p) throws IOException {
        int i;
        for(i = 0; i < p.getKeys(); i++){
            if(!p.isLeaf()){
                traverse(p.getChildren()[i]);
            }
            Data d = p.getData()[i];
            points.add(new Point(d.getKey(), d.getValues()[0], d.getValues()[1]));
        }

        if(!p.isLeaf()){
            traverse(p.getChildren()[i]);
        }
    }

    public ArrayList<Point> getPoints(){
        return points;
    }

    /**
     *
     * @param n - Data to insert into the tree (key, val) pair
     */
    public void insert(Data n) {
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

    /**
     *
     * @return The height of the tree
     */
    public int getHeight(){
        return height;
    }

    /**
     *
     * @param x - The Node that the data will be inserted into
     * @param d - The Data that will be inserted
     */
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

    /**
     *
     * @param parent - The Parent Node that the median key will be inserted into
     * @param i - The Integer index of the child that will be used to populate the parent and right key
     */
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

    /**
     *
     * @param n - The Node that will be traversed from to find the key asked for
     * @param k - The String key to search for
     * @return Double[] of values
     */
    public Data search(Node n, String k) throws IOException {
        int i = 0;

        while (i < n.getKeys() && k.compareTo(n.data[i].getKey()) > 0) {
            i += 1;
        }
        if (i < n.getKeys() && k.compareTo(n.data[i].getKey()) == 0) {
            return n.data[i];
        } else if (n.isLeaf()) {
            return null;
        } else {
            return search(n.children[i], k);
        }
    }

    /**
     * Shows the keys inside the current root Node
     */
    public void showRoot() {
        for (Data d : root.getData()) {
            if (d != null) {
                System.out.println(d.getKey());
            }
        }
    }

    /**
     * Node class is used as helper class to be inserted into the Btree
     */
    private static class Node {
        private Data[] data;
        private Node[] children;
        private Integer keys;
        private Integer ch;
        private boolean isLeaf;

        /**
         * Constructor to create a Node
         * @param leaf - Boolean to determine weather the Node is a leaf or not
         */
        private Node(boolean leaf) {
            data = new Data[2 * minDegree - 1];
            children = new Node[2 * minDegree];
            keys = 0;
            ch = 0;
            isLeaf = leaf;
        }

        /**
         *
         * @return Node[] of children Nodes
         */
        private Node[] getChildren() {
            return children;
        }

        /**
         *
         * @return Data[] of data within the Node
         */
        private Data[] getData() {
            return data;
        }

        /**
         *
         * @return Boolean on whether or not the Node is full
         */
        private boolean isFull() {
            return keys == ((2 * minDegree) - 1);
        }

        /**
         *
         * @return Boolean on whether or not the Node is a leaf
         */
        private boolean isLeaf() {
            return isLeaf;
        }

        /**
         *
         * @return Integer of amount of keys within Node
         */
        public int getKeys() {
            return keys;
        }

        /**
         *
         * @param d - Data to be inserted into the Node
         */
        private void insertData(Data d) {
            data[keys] = d;
            keys++;
            sortData();
        }

        /**
         *
         * @param n - Node to be added to the Node[] Children
         */
        private void insertChildren(Node n) {
            children[ch] = n;
            ch++;
        }

        /**
         *
         * @param i - Integer of the index of the child to be removed
         */
        private void removeChildren(int i) {
            children[i] = null;
            ch--;
        }

        /**
         *
         * @return Integer of amount of children in the Node
         */
        private int getCh() {
            return ch;
        }

        /**
         *
         * @param i - Integer of the index of the data to be removed
         */
        private void removeData(int i) {
            data[i] = null;
            keys--;
        }

        /**
         *
         * @return Integer of the index of the median key in the Node
         */
        private int medianKey() {
            return getData().length / 2;
        }

        /**
         * Sorts the data inside the Node
         */
        private void sortData() {
            Arrays.sort(getData(), 0, keys);
        }
    }
}

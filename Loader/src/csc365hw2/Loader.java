package csc365hw2;

/**
 * Created by landon on 3/23/17.
 */
public class Loader {

    public static void main(String args[]){
//        Double[] d = {50.0, 75.0, 100.0};
//        KeyVal k = new KeyVal("Landon", d);
//
//        Cacher c = new Cacher();
//        c.add(k);
//        c.cache();
//        c.retrieve();

        BTree b = new BTree(2);
        b.insert("Landon", new Double[]{50.0, 60.0, 70.0});
        b.insert("Bob", new Double[]{80.0, 90.0, 1000.0});
        b.insert("Dylan", new Double[]{110.0, 120.0, 130.0});
        b.insert("Landon", new Double[]{50.0, 60.0, 70.0});
        b.insert("Bob", new Double[]{80.0, 90.0, 1000.0});
        b.insert("Dylan", new Double[]{110.0, 120.0, 130.0});
        b.insert("Landon", new Double[]{50.0, 60.0, 70.0});
        b.insert("Bob", new Double[]{80.0, 90.0, 1000.0});
        b.insert("Dylan", new Double[]{110.0, 120.0, 130.0});

        b.showRoot();

    }

}

package csc365hw2.Btree;

/**
 * Created by landon on 4/3/17.
 */
public class NData implements Comparable<NData>{
    private String key;
    private Double[] values;

    public NData(String k, Double[] v) {
        key = k;
        values = v;
    }

    public String getKey() {
        return key;
    }

    public Double[] getValues() {
        return values;
    }

    public int compareTo(NData o) {
        return this.getKey().compareTo(o.getKey());
    }
}

package csc365hw2;

/**
 * Created by landon on 4/3/17.
 */
public class NodeData implements Comparable<NodeData>{
    private String key;
    private Double[] values;

    public NodeData(String k, Double[] v) {
        key = k;
        values = v;
    }

    public String getKey() {
        return key;
    }

    public Double[] getValues() {
        return values;
    }

    public int compareTo(NodeData o) {
        return this.getKey().compareTo(o.getKey());
    }
}

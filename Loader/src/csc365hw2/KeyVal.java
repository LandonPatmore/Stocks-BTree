package csc365hw2;

/**
 * Created by landon on 3/23/17.
 */
public class KeyVal {
    private String key;
    private Double[] val;
    private Double mD;

    private KeyVal next;

    /**
     *
     * @param k key string
     * @param v double[] values
     */
    public KeyVal(String k, Double[] v) {
        key = k;
        val = v;
        mD = 0.0;
    }

    /**
     *
     * @return gets the key
     */

    public String getKey() {
        return key;
    }

    /**
     *
     * @return gets the values
     */

    public Double[] getVal() {
        return val;
    }

    /**
     *
     * @return gets the next KeyVal for the CLinkedList
     */

    public KeyVal getNext() {
        return next;
    }

    /**
     *
     * @param next sets the next KeyVal for the CLinkedList
     */

    public void setNext(KeyVal next) {
        this.next = next;
    }

    /**
     *
     * @return custom toString method for GUI
     */

    @Override
    public String toString() {
        return key;
    }

    /**
     *
     * @return gets the ManhattanDistance for the specified KeyVal
     */

    public Double getmD() {
        return mD;
    }

    /**
     *
     * @param mD sets the ManhattanDistance when comparing KeyVals
     */

    public void setmD(Double mD) {
        this.mD = mD;
    }
}

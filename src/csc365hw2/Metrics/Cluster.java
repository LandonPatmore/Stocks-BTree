package csc365hw2.Metrics;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by landon on 4/3/17.
 */

/**
 * Class to create Clusters
 */
public class Cluster {

    private ArrayList<Point> points;
    private Point center;
    private DecimalFormat df;

    /**
     * Constructor
     */
    public Cluster() {
        points = new ArrayList<>();
        center = null;
        df = new DecimalFormat("#.00");
    }

    /**
     *
     * @return ArrayList<Point> Objects
     */
    public ArrayList<Point> getPoints() {
        return points;
    }

    /**
     *
     * @param point - Point Object
     */
    public void addPoint(Point point) {
        points.add(point);
    }

    /**
     *
     * @return Point Object
     */
    public Point getCenter() {
        return center;
    }

    /**
     *
     * @return String of Cluster Center
     */
    public String centerCoord(){
        return df.format(center.getX()) + ", " + df.format(center.getY());
    }

    /**
     *
     * @param centro - Point Object
     */
    public void setCenter(Point centro) {
        center = centro;
    }

    /**
     * Clears the points
     */
    public void clear() {
        points.clear();
    }

}

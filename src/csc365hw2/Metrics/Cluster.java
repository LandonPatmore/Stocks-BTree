package csc365hw2.Metrics;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by landon on 4/3/17.
 */

/**
 * Class to create Clusters (NOT IMPLEMENTED YET)
 */
public class Cluster {

    private ArrayList<Point> points;
    private Point center;
    private int id;
    private DecimalFormat df;

    public Cluster() {
        points = new ArrayList<>();
        center = null;
        df = new DecimalFormat("#.00");
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public Point getCenter() {
        return center;
    }

    public String centerCoord(){
        return df.format(center.getX()) + ", " + df.format(center.getY());
    }

    public void setCenter(Point centroid) {
        this.center = centroid;
    }

    public void clear() {
        points.clear();
    }

}

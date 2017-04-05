package csc365hw2.Metrics;

import java.util.ArrayList;

/**
 * Created by landon on 4/3/17.
 */

/**
 * Class to create Clusters (NOT IMPLEMENTED YET)
 */
public class Cluster {

    public ArrayList<Point> points;
    public Point center;
    public int id;

    public Cluster(int id) {
        this.id = id;
        this.points = new ArrayList<>();
        this.center = null;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public void setPoints(ArrayList points) {
        this.points = points;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point centroid) {
        this.center = centroid;
    }

    public int getId() {
        return id;
    }

    public void clear() {
        points.clear();
    }

    public void plotCluster() {
        System.out.println("[Cluster: " + id+"]");
        System.out.println("[Center: " + center + "]");
        System.out.println("[Points: \n");
        for(Point p : points) {
            System.out.println(p);
        }
        System.out.println("]");
    }

}

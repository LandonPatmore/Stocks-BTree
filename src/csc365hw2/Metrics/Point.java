package csc365hw2.Metrics;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by landon on 4/3/17.
 */

/**
 * Class to create Points (NOT IMPLEMENTED YET)
 */
public class Point {

    private double x = 0;
    private double y = 0;
    private String key;
    private int cluster = 0;

    public Point(String k, double x, double y)
    {
        key = k;
        setX(x);
        setY(y);
    }

    public void setX(double xco) {
        x = xco;
    }

    public double getX()  {
        return x;
    }

    public void setY(double yco) {
        y = yco;
    }

    public double getY() {
        return y;
    }

    public void setCluster(int n) {
        cluster = n;
    }

    protected static double distance(Point p, Point center) {
        return Math.sqrt(Math.pow((center.getY() - p.getY()), 2) + Math.pow((center.getX() - p.getX()), 2));
    }

    protected static Point createRandomPoint(int min, int max) {
        Random r = new Random();
        double x = min + 1000 * r.nextDouble();
        double y = min + (max - min) * r.nextDouble();
        return new Point("Random Point",x,y);
    }

    public String toString() {
        return key;
    }
}
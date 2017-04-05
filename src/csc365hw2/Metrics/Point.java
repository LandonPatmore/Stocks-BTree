package csc365hw2.Metrics;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by landon on 4/3/17.
 */

/**
 * Class to create Points
 */
public class Point {

    private double x = 0;
    private double y = 0;
    private String key;
    private int cluster = 0;

    /**
     *
     * @param k - String key
     * @param x - Double coord
     * @param y - Double coord
     */
    public Point(String k, double x, double y)
    {
        key = k;
        setX(x);
        setY(y);
    }

    /**
     *
     * @param xco - Double x coord
     */
    public void setX(double xco) {
        x = xco;
    }

    /**
     *
     * @return Double x coord
     */
    public double getX()  {
        return x;
    }

    /**
     *
     * @param yco - Double y coord
     */
    public void setY(double yco) {
        y = yco;
    }

    /**
     *
     * @return Double y coord
     */
    public double getY() {
        return y;
    }

    /**
     *
     * @param n - Integer cluster number
     */
    public void setCluster(int n) {
        cluster = n;
    }

    /**
     *
     * @param p - Point Object
     * @param center - Point Object Center
     * @return Double distance between Point and Center
     */
    public static double distance(Point p, Point center) {
        return Math.sqrt(Math.pow((center.getY() - p.getY()), 2) + Math.pow((center.getX() - p.getX()), 2));
    }

    /**
     *
     * @param min - Integer Minimum value
     * @param max - Integer Maximum Value
     * @return Point Object
     */
    public static Point createRandomPoint(int min, int max) {
        Random r = new Random();
        double x = min + 1000 * r.nextDouble();
        double y = min + (max - min) * r.nextDouble();
        return new Point("Random Point",x,y);
    }

    /**
     *
     * @return String key
     */
    public String toString() {
        return key;
    }
}
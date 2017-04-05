package csc365hw2.Metrics;

import java.util.ArrayList;

/**
 * Created by landon on 4/3/17.
 */

/**
 * Class to create KMeans (NOT IMPLEMENTED YET)
 */
public class KMeans {

    private int NUM_CLUSTERS;
    private static final int MIN_COORDINATE = 0;
    private static final int MAX_COORDINATE = 100000000;

    private ArrayList<Point> points;
    private ArrayList<Cluster> clusters;

    /**
     * Constructor
     */
    public KMeans() {
        points = new ArrayList<>();
        clusters = new ArrayList<>();
    }

    /**
     *
     * @param n - Integer amount of clusters
     */
    public void setClusters(int n){
        NUM_CLUSTERS = n;
    }

    /**
     *
     * @param p - ArrayList<Point> of Point Objects
     */
    public void addPoints(ArrayList<Point> p){
        points = p;
    }

    /**
     * Clears the points
     */
    public void clearPoints(){
        points.clear();
    }

    /**
     *
     * @return Integer number of Clusters
     */
    public int getNUM_CLUSTERS(){
        return NUM_CLUSTERS;
    }

    /**
     *
     * @return ArrayList<Cluster> of Cluster Objects
     */
    public ArrayList<Cluster> getClusters(){
        return clusters;
    }

    /**
     * Initializes the clusters
     */
    public void init() {
        for (int i = 0; i < NUM_CLUSTERS; i++) {
            Cluster cluster = new Cluster();
            Point centroid = Point.createRandomPoint(MIN_COORDINATE,MAX_COORDINATE);
            cluster.setCenter(centroid);
            clusters.add(cluster);
        }
    }

    /**
     * Calculates the clusters' points away from the centers and the centers themselves
     */
    public void calculate() {
        boolean finish = false;

        while(!finish) {
            clearClusters();

            ArrayList<Point> lastCenter = getCenters();

            assignCluster();

            calculateCenters();

            ArrayList<Point> currentCenters = getCenters();

            double distance = 0;
            for(int i = 0; i < lastCenter.size(); i++) {
                distance += Point.distance(lastCenter.get(i),currentCenters.get(i));
            }

            if(distance == 0) {
                finish = true;
            }
        }
    }

    /**
     * Clears the clusters
     */
    private void clearClusters() {
        for(Cluster cluster : clusters) {
            cluster.clear();
        }
    }

    /**
     *
     * @return ArrayList<Point> of Point Objects
     */
    private ArrayList<Point> getCenters() {
        ArrayList<Point> centers = new ArrayList<>(NUM_CLUSTERS);
        for(Cluster cluster : clusters) {
            Point aux = cluster.getCenter();
            Point point = new Point("",aux.getX(),aux.getY());
            centers.add(point);
        }
        return centers;
    }

    /**
     * Assigns points to clusters
     */
    private void assignCluster() {
        double max = Double.MAX_VALUE;
        double min;
        int cluster = 0;
        double distance;

        for(Point point : points) {
            min = max;
            for(int i = 0; i < NUM_CLUSTERS; i++) {
                Cluster c = clusters.get(i);
                distance = Point.distance(point, c.getCenter());
                if(distance < min){
                    min = distance;
                    cluster = i;
                }
            }
            point.setCluster(cluster);
            clusters.get(cluster).addPoint(point);
        }
    }

    /**
     * Calculates the centers of the clusters
     */
    private void calculateCenters() {
        for(Cluster cluster : clusters) {
            double sumX = 0;
            double sumY = 0;
            ArrayList<Point> list = cluster.getPoints();
            int n_points = list.size();

            for(Point point : list) {
                sumX += point.getX();
                sumY += point.getY();
            }

            Point center = cluster.getCenter();
            if(n_points > 0) {
                double newX = sumX / n_points;
                double newY = sumY / n_points;
                center.setX(newX);
                center.setY(newY);
            }
        }
    }
}
package csc365hw2.Metrics;

import java.util.ArrayList;

/**
 * Created by landon on 4/3/17.
 */

/**
 * Class to create KMeans (NOT IMPLEMENTED YET)
 */
public class KMeans {

    private int NUM_CLUSTERS = 7;
    private static final int MIN_COORDINATE = 0;
    private static final int MAX_COORDINATE = 100;

    private ArrayList<Point> points;
    private ArrayList<Cluster> clusters;

    public KMeans() {
        this.points = new ArrayList();
        this.clusters = new ArrayList();
    }

    public static void main(String[] args) {

        KMeans kmeans = new KMeans();
        kmeans.init();
        kmeans.calculate();
    }

    public void init() {
    }

    private void plotClusters() {
        for (int i = 0; i < NUM_CLUSTERS; i++) {
            Cluster c = clusters.get(i);
            c.plotCluster();
        }
    }

    public void calculate() {
        boolean finish = false;
        int iteration = 0;

        while(!finish) {
            clearClusters();

            ArrayList<Point> lastCentroids = getCentroids();

            assignCluster();

            calculateCentroids();

            iteration++;

            ArrayList<Point> currentCentroids = getCentroids();

            double distance = 0;
            for(int i = 0; i < lastCentroids.size(); i++) {
                distance += Point.distance(lastCentroids.get(i),currentCentroids.get(i));
            }
            System.out.println("#################");
            System.out.println("Iteration: " + iteration);
            System.out.println("Centroid distances: " + distance);
            plotClusters();

            if(distance == 0) {
                finish = true;
            }
        }
    }

    private void clearClusters() {
        for(Cluster cluster : clusters) {
            cluster.clear();
        }
    }

    private ArrayList<Point> getCentroids() {
        ArrayList<Point> centroids = new ArrayList(NUM_CLUSTERS);
        for(Cluster cluster : clusters) {
            Point aux = cluster.getCentroid();
            Point point = new Point(aux.getX(),aux.getY());
            centroids.add(point);
        }
        return centroids;
    }

    private void assignCluster() {
        double max = Double.MAX_VALUE;
        double min;
        int cluster = 0;
        double distance;

        for(Point point : points) {
            min = max;
            for(int i = 0; i < NUM_CLUSTERS; i++) {
                Cluster c = clusters.get(i);
                distance = Point.distance(point, c.getCentroid());
                if(distance < min){
                    min = distance;
                    cluster = i;
                }
            }
            point.setCluster(cluster);
            clusters.get(cluster).addPoint(point);
        }
    }

    private void calculateCentroids() {
        for(Cluster cluster : clusters) {
            double sumX = 0;
            double sumY = 0;
            ArrayList<Point> list = cluster.getPoints();
            int n_points = list.size();

            for(Point point : list) {
                sumX += point.getX();
                sumY += point.getY();
            }

            Point centroid = cluster.getCentroid();
            if(n_points > 0) {
                double newX = sumX / n_points;
                double newY = sumY / n_points;
                centroid.setX(newX);
                centroid.setY(newY);
            }
        }
    }
}
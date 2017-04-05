package csc365hw2.Metrics;

import java.util.ArrayList;

/**
 * Created by landon on 4/3/17.
 */

/**
 * Class to create KMeans (NOT IMPLEMENTED YET)
 */
public class KMeans {

    private int NUM_CLUSTERS = 31;
    private static final int MIN_COORDINATE = 0;
    private static final int MAX_COORDINATE = 100000000;

    private ArrayList<Point> points;
    private ArrayList<Cluster> clusters;

    public KMeans() {
        this.points = new ArrayList();
        this.clusters = new ArrayList();
    }

    public void addPoints(ArrayList<Point> p){
        points = p;
    }

    public int getNUM_CLUSTERS(){
        return NUM_CLUSTERS;
    }

    public ArrayList<Cluster> getClusters(){
        return clusters;
    }

    //Initializes the process
    public void init() {
        for (int i = 0; i < NUM_CLUSTERS; i++) {
            Cluster cluster = new Cluster(i);
            Point centroid = Point.createRandomPoint(MIN_COORDINATE,MAX_COORDINATE);
            cluster.setCenter(centroid);
            clusters.add(cluster);
        }

        //Print Initial state
        plotClusters();
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

            ArrayList<Point> lastCenter = getCenters();

            assignCluster();

            calculateCenters();

            iteration++;

            ArrayList<Point> currentCenters = getCenters();

            double distance = 0;
            for(int i = 0; i < lastCenter.size(); i++) {
                distance += Point.distance(lastCenter.get(i),currentCenters.get(i));
            }
            System.out.println("#################");
            System.out.println("Iteration: " + iteration);
            System.out.println("Center distances: " + distance);
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

    private ArrayList<Point> getCenters() {
        ArrayList<Point> centers = new ArrayList(NUM_CLUSTERS);
        for(Cluster cluster : clusters) {
            Point aux = cluster.getCenter();
            Point point = new Point(aux.getX(),aux.getY());
            centers.add(point);
        }
        return centers;
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
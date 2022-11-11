import java.util.ArrayList;
import java.lang.Math;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.GrahamScan;

class Airport {

    // Output the smallest average distance with optimal selection of airport location.
    private Point2D old;
    public double airport(List<int[]> houses) {

        if (houses.size() == 2)
            return 0;

        double avgDistance = 999999999;
        double avgX = 0;
        double avgY = 0;
        int[] tmp = houses.get(0);
        final int numPoints = houses.size();
        Point2D minY = new Point2D(tmp[0], tmp[1]);
        Point2D[] points = new Point2D[numPoints];
        for (int i=0; i<numPoints; i++) {
            int[] x = houses.get(i);
            avgX += x[0];
            avgY += x[1];
            points[i] = new Point2D(x[0], x[1]);
            if (x[1] < minY.y())
                minY = points[i];
        }

        Point2D avg = new Point2D(avgX/numPoints, avgY/numPoints);

        GrahamScan graham = new GrahamScan(points);
        int thres=0;
        for (Point2D p : graham.hull()) {
            if (thres==0)
                old=p;
            thres++;
            if (thres == 2)
                break;
        }
        if (thres<2)
            return 0;

        Point2D ori = old;
        for (Point2D p : graham.hull()) {
            if (p==old)
                continue;
            // System.out.println("old: "+old+" p: "+p);
            double cos = Math.abs((avg.x() - p.x())*(old.x() - p.x()) + (avg.y() - p.y())*(old.y() - p.y()))/
                    (Math.sqrt(avg.distanceSquaredTo(p)*old.distanceSquaredTo(p)));
            double sin = Math.sqrt(1-Math.pow(cos, 2));
            // System.out.println("cos: "+cos+" sin: "+sin);
            avgDistance = Math.min(Math.sqrt(avg.distanceSquaredTo(p))*sin, avgDistance);
            // System.out.println(Math.sqrt(avg.distanceSquaredTo(p))*sin);
            old = p;
        }
        double cos = Math.abs((avg.x() - ori.x())*(old.x() - ori.x()) + (avg.y() - ori.y())*(old.y() - ori.y()))/
                (Math.sqrt(avg.distanceSquaredTo(ori)*old.distanceSquaredTo(ori)));
        double sin = Math.sqrt(1-Math.pow(cos, 2));
        // System.out.println("cos: "+cos+" sin: "+sin);
        avgDistance = Math.min(Math.sqrt(avg.distanceSquaredTo(ori))*sin, avgDistance);

        return avgDistance;
    }

    public static void main(String[] args) {

        System.out.println(new Airport().airport(new ArrayList<int[]>(){{
            add(new int[]{9,9});
            add(new int[]{8,9});
            add(new int[]{7,9});
            add(new int[]{11,13});
            add(new int[]{15,15});
            add(new int[]{15,10});
            add(new int[]{15,11});
        }}));
        System.out.println(new Airport().airport(new ArrayList<int[]>(){{
            add(new int[]{0,0});
            add(new int[]{1,0});
        }}));
        System.out.println(new Airport().airport(new ArrayList<int[]>(){{
            add(new int[]{0,0});
            add(new int[]{1,0});
            add(new int[]{0,1});
        }}));
        System.out.println(new Airport().airport(new ArrayList<int[]>(){{
            add(new int[]{3,3});
            add(new int[]{3,4});
            add(new int[]{3,5});
            add(new int[]{4,3});
            add(new int[]{5,3});
        }}));
        System.out.println(new Airport().airport(new ArrayList<int[]>(){{
            add(new int[]{3,3});
            add(new int[]{3,2});
            add(new int[]{3,1});
            add(new int[]{1,3});
            add(new int[]{2,3});
            add(new int[]{3,4});
            add(new int[]{3,5});
            add(new int[]{4,3});
            add(new int[]{5,3});
        }}));
        System.out.println(new Airport().airport(new ArrayList<int[]>(){{
            add(new int[]{3,3});
            add(new int[]{4,4});
            add(new int[]{5,5});
            add(new int[]{6,6});
            add(new int[]{7,7});
        }}));
        System.out.println(new Airport().airport(new ArrayList<int[]>(){{
            add(new int[]{17,1});
            add(new int[]{18,5});
            add(new int[]{19,3});
            add(new int[]{2,15});
            add(new int[]{7,7});
            add(new int[]{16,0});
            add(new int[]{1,11});
            add(new int[]{2,16});
            add(new int[]{5,19});
            add(new int[]{8,10});
        }}));
        System.out.println(new Airport().airport(new ArrayList<int[]>(){{
            add(new int[]{7,3});
            add(new int[]{7,21});
            add(new int[]{1,21});
            add(new int[]{1,3});
            add(new int[]{4,12});
        }}));
        System.out.println(new Airport().airport(new ArrayList<int[]>(){{
            add(new int[]{3,3});
            add(new int[]{1,3});
            add(new int[]{3,5});
            add(new int[]{4,3});
            add(new int[]{3,1});
        }}));
    }
}
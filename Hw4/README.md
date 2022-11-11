111-1 PDSA HW4
---
Due date: 10/14 21:00

Assignment: Airport
---
There is a small town with n houses. The town needs an airport. An airport is basically a very long, very straight road. Think of it as an infinite line. We need to build the airport such that the average distance from each house to the airport is as small as possible. However, no one wants to walkacross the runway, so all of the houses must be on the same side of the airport. Where should we build the airport, and what will be the average distance?

(Some houses may be a distance of zero away from the runway, but that’s ok; we’ll give them some free ear plugs.)

Template
---

import java.util.List;
import java.util.ArrayList;
import java.lang.Math;


class Airport {
    
    // Output smallest average distance with optimal selection of airport location.
    public double airport(List<int[]> houses) {
        double avgDistance;
        // TODO
        
        return avgDistance; 
    }   

    public static void main(String[] args) {

        System.out.println(new Airport().airport(new ArrayList<int[]>(){{
            add(new int[]{0,0});
            add(new int[]{1,0});
            add(new int[]{0,1});
        }}));
    }   
}

Example
---
class Airport {

    public static void main(String[] args) {
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
            add(new int[]{0,0});
            add(new int[]{2,0});
            add(new int[]{0,2});
            add(new int[]{1,1});
            add(new int[]{2,2});
        }}));
    }   
}
Expected output:

0.0
0.2357022603955159
1.0


TestCase
---
Time Limit: 0.5s

0 <= x,y <= M, x and y are integer.
We guarantee the coordinates are unique
N is the number of houses
We will check your answer by abs(output - answer) <= 1e-4
Cases:

case1: 20 points: N = 10, M <= 100
case2: 20 points: N = 25000, M < 25000 Special Case(Four samples)
case3: 20 points: N = 100, M <= 10
case4: 20 points: N = 1000, M <= 1000
case5: 20 points: N = 100000, M <= 100000

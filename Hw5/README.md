111-1 PDSA HW5
---
Due date HW5-1: 11/4 21:00

HW5-1: Warriors - Top k Kings
---
Please recall HW3: Warriors
There are warriors standing in a line with various strength and attack ranges. The attack rules remain the same as in HW3, i.e. the i-th warrior can attack the j-th warrior if and only if the following conditions are satisfied:

|
j
−
i
|
≤
r
i
,
s
j
<
s
i
{
s
k
}
<
s
i
, for all k between i and j
Now in HW5, if a warrior cannot be knocked down under any circumstances, we call them “Kings”.

So again there are N warriors in this contest. An index i (i=0, 1, …, N-1) indicates one’s position (an integer coordinate). Suppose that there are M Kings among all warriors, we wish you to find them and return the indexes of the top k strongest kings in the descending order of strength. If there are more than two kings with the same strength, return them in the order of ascending indices.
if K is larger than the number of kings, just return all the kings.

Hint
---
To find top K use one of the following:
Sort
Max Priority Queue
Min Priority Queue with K element
Partial Sort with Quick Select.
To find Kings use the following:
Two Key subproblems:
which {k}<i can attack i?
which {k}<i would be attack by i?
(k and i are both index.)
Data Structure used to solve:
Stack and Priority Queue
Stack and Vector.
Template
---
import java.util.Arrays;

class King{
    // optional, for reference only  
    int Strength;
    int Range;
    int Index;
    King(int str,int rng, int i){
        Strength=str;
        Range=rng;
        Index=i;
    }
}

class Kings {
    public Kings(int[] strength, int[] range){
        // Given the attributes of each warrior
    }
    public int[] topKKings(int k) {
          
        return ....; 
        // complete the code by returning an int[]
        // remember to return the array of indexes in the descending order of strength         
    }

    public static void main(String[] args) {
        Kings sol = new Kings(new int[] {15, 3, 26, 2, 5, 19, 12, 8}
                                       , new int[] { 1, 6, 1, 3, 2, 0, 1, 5});
        System.out.println(Arrays.toString(sol.topKKings(3)));
        // In this case, the kings are [0, 2, 4, 5, 6] (without sorting, only by the order of ascending indices)
        // Output: [2, 5, 0]
    }
}
Test Data
---
N is the number of warriors
0 <= strength <= 1000000000
0 <= attack_range <= M

Time Limit:

Java: 0.5s
We guarantee the length of STH is always equal to the length of RNG.

Case:

case1: 20 points: N <= 10, M < 10
case2: 20 points: N <= 200000, M <= 200000
case3: 20 points: N <= 10000, M <= 5000
case4: 20 points: N <= 400000, M <= 200000
case5: 20 points: N <= 1000000, M <= 500000

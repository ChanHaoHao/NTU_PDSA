111-1 PDSA HW3
---
Due date: 10/7 21:00

HW3: Warriors
---
There are warriors standing in a line. Each warrior has two important properties: STH and RNG. STH stands for “strength”, the power of each warrior. RNG stands for “range”, the effective radius that the warrior can attack.

Suppose that there are N warriors in this contest. An index i (i=0, 1, …, N-1) indicates one’s position (an integer coordinate). An attack will be blocked if there is a warrior with a higher or equal STH within its RNG distance. Formally speaking, let {
s
0
, 
s
1
, …, 
s
N
−
1
} be the sequence of STH for the N warriors, and {
r
0
, 
r
1
, …, 
r
N
−
1
} be the sequence of RNG for them. Then the i-th warrior can attack the j-th warrior if and only if the following conditions are satisfied:

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
• 
a
i
 = the index of the leftmost standing warrior that the i-th warrior can attack;
• 
b
i
 = the index of the rightmost standing warrior that the i-th warrior can attack.

Please determine the sequence of pairs {(
a
0
, 
b
0
), …, (
a
N
−
1
, 
b
N
−
1
)}.

Hint
---
first compare the strength without considering the effective range (regardless of the range)
using Stack
2022/10/18 Solution guide
Template
---
import java.util.Arrays;

class warrior{
    int Strength;
    int Range;
    int Index;
    warrior(int str,int rng, int i){
        Strength=str;
        Range=rng;
        Index=i;
    }
}

class Warriors {
    public int[] warriors(int[] strength, int[] range) {
          // Given the attributes of each warrior and output the minimal and maximum 
          // index of warrior can be attacked by each warrior.
        return ....; // complete the code by returning an int[]
    }

    public static void main(String[] args) {
        Warriors sol = new Warriors();
        System.out.println(Arrays.toString(
            sol.warriors(new int[] {11, 13, 11, 7, 15},
                         new int[] { 1,  8,  1, 7,  2})));
        // Output: [0, 0, 0, 3, 2, 3, 3, 3, 2, 4]
    }
}
Test Data
---
N is the number of warriors
0 <= strength <= 1000000000
0 <= attack_range <= M

Time Limit:

Java: 0.3s
We guarantee the length of STH is always equal to the length of RNG.

Case:

case1: 20 points: N <= 10, M < 10
case2: 20 points: N <= 200000, M <= 200000
case3: 20 points: N <= 10000, M <= 5000
case4: 20 points: N <= 400000, M <= 200000
case5: 20 points: N <= 1000000, M <= 500000

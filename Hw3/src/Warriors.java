import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;

import edu.princeton.cs.algs4.Stack;

class warrior {
    int strength;
    int range;
    int Index;
    warrior (int str, int rng, int i) {
        strength=str;
        range=rng;
        Index=i;
    }
}

class Warriors {
    int[] ans;
    public int[] warriors(int[] strength, int[] range) {
    // Given the attributes of each warrior and output the minimal and maximum
    // index of warrior can be attacked by each warrior.
        ans = new int[strength.length*2];
        /*
        for (int i=0; i<strength.length; i++) {
            int left = i;
            int right = i;
            boolean L = true;
            boolean R = true;
            for (int j=1; j<=range[i]; j++) {
                if (!L && !R)
                    continue;
                if (i-j>=0) {
                    if (strength[i - j] < strength[i] && L)
                        left = i - j;
                    else
                        L = false;
                }
                else
                    L = false;
                if (i+j<=strength.length-1) {
                    if (strength[i+j] < strength[i] && R)
                        right = i+j;
                    else
                        R = false;
                }
                else
                    R = false;
            }
            ans[2*i] = left;
            ans[2*i+1] = right;
        }
         */

        Stack<warrior> warriorStack = new Stack<>();
        Stack<warrior> warriorStack1 = new Stack<>();
        warriorStack.push(new warrior(strength[0], range[0], 0));
        warriorStack1.push(new warrior(strength[strength.length-1], range[strength.length-1], strength.length-1));
        ans[2*strength.length-1] = strength.length-1;
        for (int i=1; i<strength.length; i++) {
            // left index
            if (strength[i-1]<strength[i]) {
                while (!warriorStack.isEmpty() && warriorStack.peek().strength<strength[i]) {
                    warriorStack.pop();
                }
                int tmp;
                if (warriorStack.isEmpty())
                    tmp = 0;
                else
                    tmp = warriorStack.peek().Index+1;
                ans[i*2] = Math.max(tmp, i-range[i]);
                warriorStack.push(new warrior(strength[i], range[i], i));
            }
            else {
                warriorStack.push(new warrior(strength[i], range[i], i));
                ans[i * 2] = warriorStack.peek().Index;
            }

            // right index
            if (strength[strength.length-i-1]>strength[strength.length-i]) {
                while (!warriorStack1.isEmpty() && warriorStack1.peek().strength<strength[strength.length-i-1]) {
                    warriorStack1.pop();
                }
                int tmp;
                if (warriorStack1.isEmpty())
                    tmp = strength.length-1;
                else
                    tmp = warriorStack1.peek().Index-1;
                ans[(strength.length-i-1)*2+1] = Math.min(tmp, strength.length-i-1+range[strength.length-i-1]);
                warriorStack1.push(new warrior(strength[strength.length-i-1], range[strength.length-i-1], strength.length-i-1));
            }
            else {
                warriorStack1.push(new warrior(strength[strength.length-i-1], range[strength.length-i-1], strength.length-i-1));
                ans[(strength.length-i-1)*2+1] = warriorStack1.peek().Index;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Warriors sol = new Warriors();
        System.out.println(Arrays.toString(new int[] {11, 13, 11, 7, 15}));
        System.out.println(Arrays.toString(new int[] { 1,  8,  1, 7,  2}));
        System.out.println(Arrays.toString(
                sol.warriors(new int[] {11, 13, 11, 7, 15},
                             new int[] { 1,  8,  1, 7,  2})));
        // 0,0,0,3,2,3,3,3,2,4
    }
}

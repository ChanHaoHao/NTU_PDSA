import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Arrays;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Point2D;

import static edu.princeton.cs.algs4.Point2D.X_ORDER;
import static edu.princeton.cs.algs4.Point2D.Y_ORDER;

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

class Kings{
    Stack<King> kingStack = new Stack<>();
    Point2D[] kingPoints;
    public Kings(int[] strength, int[] range){
        // Given the attributes of each warrior
        kingStack.push(new King(strength[0], range[0], 0));

        for (int i=1; i<strength.length; i++) {
            if (kingStack.peek().Strength<strength[i]) {
                while (!kingStack.isEmpty() && kingStack.peek().Strength<strength[i] && range[i]>=(i-kingStack.peek().Index)) {
                    kingStack.pop();
                }
            }
            else if (kingStack.peek().Strength>strength[i]){
                if (kingStack.peek().Range>=(i-kingStack.peek().Index))
                    continue;
            }
            kingStack.push(new King(strength[i], range[i], i));
        }

        kingPoints = new Point2D[kingStack.size()];
        int i=0;
        while (!kingStack.isEmpty()) {
            King tmp = kingStack.pop();
            kingPoints[i] = new Point2D(tmp.Index, tmp.Strength);
            i++;
        }
    }

    public int[] topKKings(int k) {
        int[] kings;
        // complete the code by returning an int[]
        // remember to return the array of indexes in the descending order of strength
        Arrays.sort(kingPoints, X_ORDER);
        Arrays.sort(kingPoints, Y_ORDER.reversed());
        int j = Math.min(kingPoints.length, k);
        kings = new int[j];
        for (int i=0; i<j; i++) {
            kings[i] = (int) kingPoints[i].x();
        }
        return kings;
    }

    public static void main(String[] args) {
        test t = new test(args);
    }
}
class test{
    public test(String[] args){
        Kings sol;
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(args[0])){
            JSONArray all = (JSONArray) jsonParser.parse(reader);
            for(Object CaseInList : all){
                JSONArray a = (JSONArray) CaseInList;
                int q_cnt = 0, wa = 0,ac = 0;
                for (Object o : a) {
                    q_cnt++;
                    JSONObject person = (JSONObject) o;
                    JSONArray arg_str = (JSONArray) person.get("strength");
                    JSONArray arg_rng = (JSONArray) person.get("attack_range");
                    Long arg_k = (Long) person.get("k");
                    JSONArray arg_ans = (JSONArray) person.get("answer");
                    int STH[] = new int[arg_str.size()];
                    int RNG[] = new int[arg_str.size()];
                    int k = Integer.parseInt(arg_k.toString());

                    int Answer[] = new int[arg_ans.size()];
                    int Answer_W[] = new int[arg_ans.size()];
                    for(int i=0;i<arg_ans.size();i++){
                        Answer[i]=(Integer.parseInt(arg_ans.get(i).toString()));
                    }
                    for(int i=0;i<arg_str.size();i++){
                        STH[i]=(Integer.parseInt(arg_str.get(i).toString()));
                        RNG[i]=(Integer.parseInt(arg_rng.get(i).toString()));
                    }
                    sol = new Kings(STH,RNG);
                    Answer_W = sol.topKKings(k);
                    System.out.println(Answer_W.length);
                    for(int i=0;i<arg_ans.size();i++){
                        if(Answer_W[i]==Answer[i]){
                            if(i==arg_ans.size()-1){
                                System.out.println(q_cnt+": AC");
                            }
                        }else {
                            wa++;
                            System.out.println(q_cnt+": WA");
                            System.out.print("my answer: ");
                            for (int z=0; z<Answer_W.length; z++) {
                                System.out.print(Answer_W[z]+" ");
                            }
                            System.out.println();
                            System.out.print("real answer: ");
                            for (int z=0; z<Answer.length; z++) {
                                System.out.print(Answer[z]+" ");
                            }
                            System.out.println();
                            break;
                        }
                    }

                }
                System.out.println("Score: "+(q_cnt-wa)+"/"+q_cnt);

            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
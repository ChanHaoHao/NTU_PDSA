import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class trial {
    private static class Node {
        private Point2D site;
        private Node next;
    }

    private final int[] board;
    private final int size;
    private WeightedQuickUnionUF BotTop;
    private WeightedQuickUnionUF noBot;
    private WeightedQuickUnionUF noBotTop;
    private int[] whichlist;
    private Point2D[] ans;

    // create N-by-N grid, with all sites blocked
    public trial(int N) {
        size = N;
        board = new int[N*N];
        BotTop = new WeightedQuickUnionUF(N*N+2);
        noBot = new WeightedQuickUnionUF(N*N+1);
        noBotTop = new WeightedQuickUnionUF(N*N);
        // to know which list the node is in
        whichlist = new int[N*N];
        for (int i=0; i<size; i++) {
            // connect top
            BotTop.union(i, size*size);
            // connect bot
            BotTop.union(size*(size-1)+i, size*size+1);
            // connect top
            noBot.union(i, size*size);
        }
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        board[i*size+j] = 1;
        whichlist[i*size+j] = 1;

        // top
        connect(i, j, i-1, j);
        // bot
        connect(i, j, i+1, j);
        // left
        connect(i, j, i, j-1);
        // right
        connect(i, j, i, j+1);

        if (percolates() && ans==null) {
            int count=0;
            ans = new Point2D[whichlist[noBotTop.find(i*size+j)]];
            for (int x=0; x<size; x++) {
                for (int y=0; y<size; y++) {
                    if (noBotTop.find(i*size+j) == noBotTop.find(y*size+x)) {
                        ans[count] = new Point2D(y, x);
                        System.out.println(ans[count]);
                        count++;
                    }
                }
            }
            //Merge.sort(ans);
        }
    }

    private void connect(int oriI, int oriJ, int i, int j) {
        if (i<0 || i>size-1 || j<0 || j>size-1) {
            return;
        }

        if (board[i*size+j]==1){
            int root1 = noBotTop.find(oriI*size+oriJ);
            int root2 = noBotTop.find(i*size+j);
            noBotTop.union(oriI*size+oriJ, i*size+j);
            noBot.union(oriI*size+oriJ, i*size+j);
            if (!percolates())
                BotTop.union(oriI*size+oriJ, i*size+j);
            whichlist[noBotTop.find(i*size+j)] = whichlist[root1]+whichlist[root2];
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        return board[i*size+j]==1;
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        if (size==1) {
            if (board[0]==0)
                return false;
        }
        return noBot.connected(i*size+j, size*size);
    }

    // does the system percolate?
    public boolean percolates() {
        if (size==1) {
            if (board[0]==0)
                return false;
        }
        return BotTop.connected(size*size, size*size+1);
    }

    // return the array of the sites of the percolated region in order (using Point2D default compare.to)
    // This function should always return the content of the percolated region AT THE MOMENT when percolation just happened.
    public Point2D[] PercolatedRegion() {
        return ans;
    }

    public static void test(String[] args){
        trial g;
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(args[0])){
            JSONArray all = (JSONArray) jsonParser.parse(reader);
            int count = 0;
            for(Object CaseInList : all){
                count++;
                JSONArray a = (JSONArray) CaseInList;
                int testSize = 0; int waSize = 0;
                System.out.print("Case ");
                System.out.println(count);
                //Board Setup
                JSONObject argsSeting = (JSONObject) a.get(0);
                a.remove(0);

                JSONArray argSettingArr = (JSONArray) argsSeting.get("args");
                g = new trial(
                        Integer.parseInt(argSettingArr.get(0).toString()));

                for (Object o : a)
                {
                    JSONObject person = (JSONObject) o;

                    String func =  person.get("func").toString();
                    JSONArray arg = (JSONArray) person.get("args");

                    switch(func){
                        case "open":
                            g.open(Integer.parseInt(arg.get(0).toString()),
                                    Integer.parseInt(arg.get(1).toString()));
                            break;
                        case "isOpen":
                            testSize++;
                            String true_isop = (Boolean)person.get("answer")?"1":"0";
                            String ans_isop = g.isOpen(Integer.parseInt(arg.get(0).toString()),
                                    Integer.parseInt(arg.get(1).toString()))?"1":"0";
                            if(true_isop.equals(ans_isop)){
                                System.out.println("isOpen : AC");
                            }else{
                                waSize++;
                                System.out.println("isOpen : WA");
                            }
                            break;
                        case "isFull":
                            testSize++;
                            String true_isfu =  (Boolean)person.get("answer")?"1":"0";
                            String ans_isfu = g.isFull(Integer.parseInt(arg.get(0).toString()),
                                    Integer.parseInt(arg.get(1).toString()))?"1":"0";
                            if(true_isfu.equals(ans_isfu)){
                                System.out.println("isFull : AC");
                            }else{
                                waSize++;
                                System.out.println("isFull : WA");
                            }
                            break;
                        case "percolates":
                            testSize++;
                            String true_per = (Boolean)person.get("answer")?"1":"0";
                            String ans_per = g.percolates()?"1":"0";
                            if(true_per.equals(ans_per)){
                                System.out.println("percolates : AC");
                            }else{
                                waSize++;
                                System.out.println("percolates : WA");
                            }
                            break;
                        case "PercolatedRegion":
                            testSize++;
                            String true_reg = person.get("args").toString();
                            String reg = "[";
                            Point2D[] pr = g.PercolatedRegion();
                            for (int i = 0; i < pr.length; i++) {
                                reg = reg + ((int)pr[i].x() + "," + (int)pr[i].y());
                                if(i != pr.length - 1){
                                    reg =reg + ",";
                                }
                            }
                            reg = reg +"]";
                            if(true_reg.equals(reg)){
                                System.out.println("PercolatedRegion : AC");
                            }else{
                                waSize++;
                                System.out.println("PercolatedRegion : WA");
                            }
                            break;
                    }

                }
                System.out.println("Score: " + (testSize-waSize) + " / " + testSize + " ");
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public static void main(String args[]){
        test(args);
        /*Percolation s = new Percolation(3);
        s.open(1,1);
        System.out.println(s.isFull(1, 1));
        System.out.println(s.percolates());
        s.open(0,1);
        s.open(2,0);
        System.out.println(s.isFull(1, 1));
        System.out.println(s.isFull(0, 1));
        System.out.println(s.isFull(2, 0));
        System.out.println(s.percolates());
        s.open(2,1);
        System.out.println(s.isFull(1, 1));
        System.out.println(s.isFull(0, 1));
        System.out.println(s.isFull(2, 0));
        System.out.println(s.isFull(2, 1));
        System.out.println(s.percolates());*/
    }
}
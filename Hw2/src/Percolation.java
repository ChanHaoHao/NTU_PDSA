import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

class Percolation {
    private static class Node {
        private Point2D site;
        private Node next;
    }

    private final int[] board;
    private final int size;
    private final WeightedQuickUnionUF BotTop;
    private final WeightedQuickUnionUF noBot;
    private final WeightedQuickUnionUF noBotTop;
    private final int[] whichlist;
    private Point2D[] ans;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
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
            int tmp = noBotTop.find(i*size+j);
            for (int x=0; x<size; x++) {
                for (int y=0; y<size; y++) {
                    if (tmp == noBotTop.find(y*size+x)) {
                        ans[count] = new Point2D(y, x);
                        count++;
                    }
                }
            }
            //Merge.sort(ans);
        }
    }

    private void connect(int oriI, int oriJ, int i, int j) {
        // out of the boarder
        if (i<0 || i>size-1 || j<0 || j>size-1) {
            return;
        }

        if (board[i*size+j]==1){
            if (!noBotTop.connected(oriI*size+oriJ, i*size+j)) {
                int root1 = noBotTop.find(oriI*size+oriJ);
                int root2 = noBotTop.find(i*size+j);
                noBotTop.union(oriI*size+oriJ, i*size+j);
                noBot.union(oriI*size+oriJ, i*size+j);
                BotTop.union(oriI*size+oriJ, i*size+j);
                whichlist[noBotTop.find(i*size+j)] = whichlist[root1]+whichlist[root2];
            }
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

    /*
    public static void main(String[] args) {
        Percolation s = new Percolation(3);
        s.open(0,0);
        s.open(0,1);
        s.open(0,2);
        s.open(2,0);
        s.open(2,1);
        s.open(2,2);
        System.out.println(s.percolates());
        s.open(1,1);
        Point2D[] pr = s.PercolatedRegion();
        for (int i = 0; i < pr.length; i++) {
            System.out.println("("+(int)pr[i].x() + "," + (int)pr[i].y()+")");
        }
    }

     */
}
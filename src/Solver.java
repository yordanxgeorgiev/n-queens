import java.util.ArrayList;
import java.util.Random;

public class Solver {
    private final int n;
    private int[] q; // the row where the queen is
    private int[] r; // conflicts in row
    private int[] d1; // conflicts in first diagonals
    private int[] d2; // conflicts in second diagonals

    private boolean solved;
    private final ArrayList<Integer> container;
    private final Random rnd;

    public Solver(int n)
    {
        this.n = n;
        solved = false;
        rnd = new Random();
        container = new ArrayList<>();
    }

    public void solve()
    {
        init();

        int iter = 0;
        int colWithMaxConf;
        int rowWithMinConf;

        while(iter < 3*n)
        {
            iter++;
            colWithMaxConf = colWithMaxConflicts(); // also checks if the max conflicts are 0 -> solved

            if(solved) return;

            rowWithMinConf = rowWithMinConflicts(colWithMaxConf);
            r[q[colWithMaxConf]]--;
            d1[colWithMaxConf - q[colWithMaxConf] + n - 1]--;
            d2[colWithMaxConf + q[colWithMaxConf]]--;

            q[colWithMaxConf] = rowWithMinConf;

            r[rowWithMinConf]++;
            d1[colWithMaxConf - rowWithMinConf + n - 1]++;
            d2[colWithMaxConf + rowWithMinConf]++;
        }
        if(hasConflicts())
        {
            solve();
        }
    }

    private void init()
    {
        q = new int[n];
        r = new int[n];
        d1 = new int[2*n-1];
        d2 = new int[2*n-1];
        int minConflictsRow;
        for(int i = 0; i < n; i++)
        {
            minConflictsRow = rowWithMinConflicts(i);
            q[i] = minConflictsRow;
            r[minConflictsRow]++;
            d1[i-minConflictsRow+n-1]++;
            d2[i+minConflictsRow]++;
        }
    }

    public int rowWithMinConflicts(int col)
    {
        int conflicts;
        int minConflicts = n;

        for(int i = 0; i < n; i++)
        {
            conflicts = countConflicts(i, col);

            if(conflicts <= minConflicts)
            {
                if(conflicts < minConflicts)
                {
                    container.clear();
                    minConflicts = conflicts;
                }
                container.add(i);
            }
        }
        return container.get(rnd.nextInt(container.size()));
    }

    public int colWithMaxConflicts()
    {
        int maxConflicts = 0;
        int conflicts;

        for(int i = 0; i < n; i++)
        {
            conflicts = countConflicts(q[i], i);
            if(conflicts >= maxConflicts)
            {
                if(conflicts > maxConflicts)
                {
                    container.clear();
                    maxConflicts = conflicts;
                }
                container.add(i);
            }
        }
        if(maxConflicts == 0)
        {
            System.out.println("Solved!");
            solved = true;
            print();
        }

        return container.get(rnd.nextInt(container.size()));
    }

    private int countConflicts(int row, int col)
    {
        return r[row] + d1[col-row+n-1] + d2[col+row] - 3;
    }

    private boolean hasConflicts()
    {
        int col = colWithMaxConflicts();
        return countConflicts(q[col],col) != 0;
    }

    public void print()
    {
        if(n <= 10)
        {
            for(int i = 0; i < n; i++)
            {
                for(int j = 0; j < n; j++)
                {
                    if(q[j] == i)
                    {
                        System.out.print(" *");
                    }
                    else System.out.print(" _");
                }
                System.out.print("\n");
            }
        }
    }
}

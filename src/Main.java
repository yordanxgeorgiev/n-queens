import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("n: ");
        int n = scanner.nextInt();

        if(n < 4)
        {
            System.out.println("\"n\" can't be below 4!");
            System.exit(-1);
        }

        long startTime = System.currentTimeMillis();

        Solver s = new Solver(n);
        s.solve();

        long endTime = System.currentTimeMillis();

        String time = new DecimalFormat("#0.00").format((endTime - startTime) / 1000F);

        System.out.println("Time: " + time + " seconds.");
    }
}

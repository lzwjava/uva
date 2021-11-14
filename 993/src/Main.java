import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    BufferedReader in;
    PrintWriter out;

    Main() {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
    }

    int[] primes = new int[]{2, 3, 5, 7};

    boolean possible(int x) {
        if (x == 0 || x == 1) {
            return true;
        }
        for (int i = 0; i < primes.length; i++) {
            int pi = primes[i];
            if (x % pi == 0) {
                while (x % pi == 0) {
                    x /= pi;
                }
            }
        }
        return x == 1;
    }

    int[] counts(int x) {
        int[] counts = new int[4];
        for (int i = 0; i < primes.length; i++) {
            int pi = primes[i];
            if (x % pi == 0) {
                while (x % pi == 0) {
                    x /= pi;
                    counts[i]++;
                }
            }
        }
        return counts;
    }

    int cal(int x) {
        if (x == 0) {
            return 0;
        }
        int s = 1;
        while (x != 0) {
            s *= (x % 10);
            x /= 10;
        }
        return s;
    }

    void solve() throws IOException {
        int t = Integer.parseInt(in.readLine().trim());
        while (t > 0) {
            int n = Integer.parseInt(in.readLine().trim());
            boolean found = false;
            if (!possible(n)) {
                out.append("-1\n");
            } else {
                if (n < 2) {
                    out.append(String.format("%d\n", n));
                } else {
//                int[] counts = counts(n);
                    ArrayList<Integer> nums = new ArrayList<Integer>();
                    for (int i = 9; i >= 2; i--) {
                        if (n % i == 0) {
                            while (n % i == 0) {
                                nums.add(i);
                                n /= i;
                            }
                        }
                    }
                    Collections.sort(nums);
                    for (int i = 0; i < nums.size(); i++) {
                        out.append(String.valueOf(nums.get(i)));
                    }
                    out.append('\n');
                }
            }
            t--;
        }
    }

    void close() throws IOException {
        if (in != null) {
            in.close();
        }
        if (out != null) {
            out.flush();
            out.close();
        }
    }

    public static void main(String[] args) throws Exception {
        FileInputStream inStream = null;
        PrintStream outStream = null;
        boolean isLocal = System.getenv("LOCAL_JUDGE") != null;
        if (isLocal) {
            inStream = new FileInputStream("2.in");
            outStream = new PrintStream("1.out");
            System.setIn(inStream);
            System.setOut(outStream);
        }

        Main main = new Main();
        main.solve();
        main.close();

        if (isLocal) {
            inStream.close();
            // outStream.close();
        }
    }
}

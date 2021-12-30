import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Main {

    BufferedReader in;
    PrintWriter out;

    Main() {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
    }

    int red(int[][] grid, int a, int b, int m) {
        int red = 0;
        for (int i = a; i <= b; i++) {
            for (int j = 1; j <= m; j++) {
                if (grid[i][j] == 0) {
                    red++;
                }
            }
        }
        return red;
    }

    int cal(int k, int a, int b) {
        int n = (1 << k) + 1;
        int[][] grid = new int[n][n];
        // 0: red, 1: blue
        grid[1][1] = 0;
        int m = 1;
        for (int k1 = 0; k1 < k; k1++) {
            int im = m, jm = m;
            for (int i = 1; i <= im; i++) {
                for (int j = 1; j <= jm; j++) {
                    if (grid[i][j] == 0) {
                        for (int d = -1; d <= 0; d++) {
                            for (int f = -1; f <= 0; f++) {
                                int nv;
                                if (d == 0 && f == 0) {
                                    nv = 1;
                                } else {
                                    nv = 0;
                                }
                                grid[2 * i + d][2 * j + f] = nv;
                            }
                        }
                    } else {
                        for (int d = -1; d <= 0; d++) {
                            for (int f = -1; f <= 0; f++) {
                                grid[2 * i + d][2 * j + f] = 1;
                            }
                        }
                    }
                }
            }
            m *= 2;
            for (int i = 1; i <= m; i++) {
                int red = red(grid, i, i, m);
                out.append(String.format("%d ", red));
            }
            out.append('\n');
        }
        int red = red(grid, a, b, m);
        return red;
    }

    int dp(int k, int i) {
        if (k == 0) {
            return 1;
        }
        int m = 1 << (k - 1);
        if (i < m) {
            return dp(k - 1, i);
        } else {
            return dp(k - 1, i - m);
        }
    }

    int cal2(int k, int a, int b) {
        int ans = 0;
        for (int i = a - 1; i <= b - 1; i++) {
            ans += dp(k, i);
        }
        return ans;
    }

    int cal1(int k, int a, int b) {
        int[] rs = new int[1];
        rs[0] = 1;
        int m = 1;
        for (int i = 0; i < k; i++) {
            int m2 = m * 2;
            int[] ns = new int[m2];
            for (int j = 0; j < m; j++) {
                ns[j] = rs[j] * 2;
            }
            for (int j = m; j < m2; j++) {
                ns[j] = rs[j - m];
            }
            rs = ns;
            m = m2;
        }
        int red = 0;
        for (int i = a - 1; i <= b - 1; i++) {
            red += rs[i];
        }
        return red;
    }

    void solve() throws IOException {
        int tt = Integer.parseInt(in.readLine());
        for (int t = 0; t < tt; t++) {
            StringTokenizer st = new StringTokenizer(in.readLine());
            int k = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int red = cal2(k, a, b);
            out.append(String.format("Case %d: %d\n", t + 1, red));
        }
    }

    void close() throws IOException {
        in.close();
        out.flush();
        out.close();
    }

    public static void main(String[] args) throws IOException {
        Main m = new Main();
        m.solve();
        m.close();
    }
}
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

    int n;
    int m;

    int[] dx = new int[]{-1, 1, 0, 0};
    int[] dy = new int[]{0, 0, -1, 1};

    // Ashish win
    boolean dp(int[][] g, boolean ashish, int level) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (g[i][j] == 1) {
                    continue;
                }
                // 0
                boolean ok = true;
                for (int d = 0; d < dx.length; d++) {
                    int ni = i + dx[d];
                    int nj = j + dy[d];
                    if (ni >= 0 && ni < n && nj >= 0 && nj < m) {
                        if (g[ni][nj] == 1) {
                            ok = false;
                            break;
                        }
                    }
                }
                if (ok) {
                    g[i][j] = 1;
                    boolean result = dp(g, !ashish, level + 1);
                    g[i][j] = 0;
                    if (ashish && result) {
                        return true;
                    }
                    if (!ashish && !result) {
                        return false;
                    }
                }
            }
        }
        if (ashish) {
            return false;
        } else {
            return true;
        }
    }

    void solve() throws IOException {
        int t = Integer.parseInt(in.readLine());
        while (t > 0) {
            t--;
            StringTokenizer st = new StringTokenizer(in.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            int[][] g = new int[n][m];
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(in.readLine());
                for (int j = 0; j < m; j++) {
                    g[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            boolean win = dp(g, true, 0);
            if (win) {
                out.append("Ashish\n");
            } else {
                out.append("Vivek\n");
            }
        }
    }

}
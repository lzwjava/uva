import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
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

    int[] parseArray(String s) {
        StringTokenizer st = new StringTokenizer(s);
        int n = st.countTokens();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }
        return a;
    }

    public static void main(String[] args) throws IOException {
        Main m = new Main();
        m.solve();
        m.close();
    }

    int dp(int[] a, int i, int start) {
        int n = a.length;
        if (i == n) {
            return 0;
        }
        if (a[i] > start) {
            int len1 = dp(a, i + 1, a[i]) + 1;
            int len2 = dp(a, i + 1, start);
            return Integer.max(len1, len2);
        } else {
            return dp(a, i + 1, start);
        }
    }

    class Item implements Comparable<Item> {
        int v, d;

        Item(int v, int d) {
            this.v = v;
            this.d = d;
        }

        @Override
        public int compareTo(Item o) {
            if (d != o.d) {
                return Integer.compare(o.d, d);
            } else {
                return Integer.compare(v, o.v);
            }
        }
    }

    int cal(int[] a) {
        int n = a.length;
        int[] d = new int[n];
        int[] v = new int[n + 1];
        Arrays.fill(v, -1);
        int vn = 1;
        v[0] = 0;
        for (int i = 0; i < n; i++) {
            int maxLen = 0;
            int idx = Arrays.binarySearch(v, 0, vn, a[i]);
            if (idx >= 0) {
                while (v[idx] == a[i]) {
                    idx--;
                }
                maxLen = idx;
            } else {
                idx = -(idx + 1);
                maxLen = idx - 1;
            }
            int k = maxLen + 1;
            d[i] = k;
            if (v[k] == -1 || v[k] > a[i]) {
                v[k] = a[i];
            }
            if (k >= vn) {
                vn++;
            }
        }
        return vn - 1;
    }

    void solve() throws IOException {
        int z = Integer.parseInt(in.readLine());
        while (z > 0) {
            z--;
            int n = Integer.parseInt(in.readLine());
            int[] a = parseArray(in.readLine());
            int ans = cal(a);
            out.append(String.format("%d\n", ans));
        }
    }
}
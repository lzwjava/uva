import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

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


    int[] parseArray(String s) {
        StringTokenizer st = new StringTokenizer(s);
        int n = st.countTokens();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }
        return a;
    }

    void solve() throws IOException {
        int t = Integer.parseInt(in.readLine());
        while (t > 0) {
            t--;
            int n = Integer.parseInt(in.readLine());
            int[] p = parseArray(in.readLine());
            String s = in.readLine();
            int c0 = 0, c1 = 0;
            int[] idxs = new int[n + 1];
            for (int i = 0; i < n; i++) {
                idxs[p[i]] = i;
            }
            List<Integer> oneList = new ArrayList<>();
            List<Integer> zeroList = new ArrayList<>();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '1') {
                    oneList.add(p[i]);
                    c1++;
                } else {
                    zeroList.add(p[i]);
                    c0++;
                }
            }

            List<Integer> qlist1 = new ArrayList<>();
            for (int i = 0; i < c1; i++) {
                qlist1.add(n - i);
            }
            Collections.sort(qlist1, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    int idx1 = idxs[o1];
                    int idx2 = idxs[o2];
                    return Integer.compare(idx1, idx2);
                }
            });

            List<Integer> qlist2 = new ArrayList<>();
            for (int i = 0; i < c0; i++) {
                qlist2.add(i + 1);
            }
            Collections.sort(qlist2, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    int idx1 = idxs[o1];
                    int idx2 = idxs[o2];
                    return Integer.compare(idx1, idx2);
                }
            });

            int q1 = 0, q2 = 0;
            List<Integer> flist = new ArrayList<>();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '1') {
                    flist.add(qlist1.get(q1));
                    q1++;
                } else {
                    flist.add(qlist2.get(q2));
                    q2++;
                }
            }
            for (int i = 0; i < n; i++) {
                if (i != 0) {
                    out.append(' ');
                }
                out.append(String.format("%d", flist.get(i)));
            }
        }
    }

}
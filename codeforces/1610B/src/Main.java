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

    public static void main(String[] args) throws IOException {
        Main m = new Main();
        m.solve();
//        m.test();
        m.close();
    }

    void close() {
        out.flush();
        out.close();
    }

    boolean palindrome(List<Integer> list) {
        int m = list.size();
        for (int i = 0; i < m; i++) {
            int a = list.get(i);
            int b = list.get(m - 1 - i);
            if (a != b) {
                return false;
            }
        }
        return true;
    }

    boolean kalindrome(int n, List<Integer> list) {
        boolean ok;
        if (palindrome(list)) {
            ok = true;
        } else {
            ok = false;
            Set<Integer> set = new HashSet<>();
            for (int i = 0; i < n / 2; i++) {
                int i1 = i;
                int i2 = n - 1 - i;
                if (!list.get(i1).equals(list.get(i2))) {
                    set.add(list.get(i1));
                    set.add(list.get(i2));
                }
            }
            for (int x : set) {
                List<Integer> tl = new ArrayList<>();
                for (int y : list) {
                    if (y != x) {
                        tl.add(y);
                    }
                }
                if (palindrome(tl)) {
                    ok = true;
                    break;
                }
            }
        }
        return ok;
    }

    void test() {
        Random random = new Random();
//        while (true) {
        List<Integer> list = new ArrayList<>();
        int m = 200000;
        for (int i = 0; i < m; i++) {
            list.add(random.nextInt(10));
        }
        int n = list.size();
        List<Integer> nlist = new ArrayList<>(list);
        for (int i = 0; i < nlist.size() / 2; i++) {
            int t = nlist.get(i);
            nlist.set(i, nlist.get(n - 1 - i));
            nlist.set(n - 1 - i, t);
        }
        list.addAll(nlist);
        int k = random.nextInt(100);
        int v = random.nextInt(10);
        for (int i = 0; i < k; i++) {
            int idx = random.nextInt(list.size());
            list.add(idx, v);
        }
//        list.add(random.nextInt(list.size()), random.nextInt(m));
        List<Integer> alist = new ArrayList<>(list);
        assert kalindrome(alist.size(), alist);
//        }
    }

    void solve() throws IOException {
        int t = Integer.parseInt(in.readLine());
        while (t > 0) {
            t--;
            int n = Integer.parseInt(in.readLine());
            List<Integer> list = new ArrayList<>();
            StringTokenizer st = new StringTokenizer(in.readLine());
            for (int i = 0; i < n; i++) {
                int v = Integer.parseInt(st.nextToken());
                list.add(v);
            }
            boolean ok = kalindrome(n, list);
            if (ok) {
                out.append("YES\n");
            } else {
                out.append("NO\n");
            }
        }
    }
}
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

    BufferedReader in;
    PrintWriter out;

    Main() {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
    }

    enum Dir {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    class Cube {
        char top;
        char front;

        Cube(char top, char front) {
            this.top = top;
            this.front = front;
        }

        char left() {
            String s = "BWR";
            char[] chs = new char[]{top, front};
            for (char c : s.toCharArray()) {
                if (!Arrays.asList(chs).contains(c)) {
                    return c;
                }
            }
            return ' ';
        }

        Cube turn(Dir d) {
            char ntop, nfront;
            if (d == Dir.RIGHT) {
                ntop = left();
                nfront = front;
            } else if (d == Dir.LEFT) {
                ntop = left();
                nfront = front;
            } else if (d == Dir.TOP) {
                ntop = front;
                nfront = top;
            } else {
                ntop = front;
                nfront = top;
            }
            return new Cube(ntop, nfront);
        }

        @Override
        public String toString() {
            return String.format("%c%c", top, front);
        }
    }

    class State {
        Cube[][] cubes;
        int x, y;
        int dist;

        State(Cube[][] cubes, int x, int y) {
            this.cubes = cubes;
            this.x = x;
            this.y = y;
            this.dist = 0;
        }

        char[][] tops() {
            char[][] ts = new char[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Cube c = cubes[i][j];
                    char top;
                    if (c != null) {
                        top = c.top;
                    } else {
                        top = 'E';
                    }
                    ts[i][j] = top;
                }
            }
            return ts;
        }

        @Override
        protected State clone() {
            Cube[][] ncubes = new Cube[3][3];
            for (int i = 0; i < 3; i++) {
                ncubes[i] = Arrays.copyOf(cubes[i], 3);
            }
            State ns = new State(ncubes, x, y);
            ns.dist = dist;
            return ns;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    String s;
                    Cube c = cubes[i][j];
                    if (c == null) {
                        s = "EE";
                    } else {
                        s = c.toString();
                    }
                    sb.append(String.format("%s,", s));
                }
            }
            return sb.toString();
        }
    }

    // top, bottom, left, right
    int[] dx = new int[]{-1, 1, 0, 0};
    int[] dy = new int[]{0, 0, -1, 1};

    Set<String> set;
    char[][] grid;
    int ans;

    int differ(State s) {
        char[][] tops = s.tops();
        int cnt = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tops[i][j] != grid[i][j]) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    void dfs(State s) {
        int differ = differ(s);
        if (differ == 0) {
            if (s.dist < ans) {
                ans = s.dist;
            }
            return;
        }
        if (s.dist + differ > ans) {
            return;
        }
        if (s.dist >= 30) {
            return;
        }
        for (int d = 0; d < dx.length; d++) {
            int nx = s.x + dx[d];
            int ny = s.y + dy[d];
            if (nx < 0 || nx >= 3 || ny < 0 || ny >= 3) {
                continue;
            }
            Dir dir = Dir.values()[d];
            State ns = changeState(s, dir, nx, ny, s.x, s.y);
            ns.dist = s.dist + 1;
            String nsStr = ns.toString();
            if (!set.contains(nsStr)) {
                set.add(nsStr);
                dfs(ns);
            }
        }
    }

    void solve() throws IOException {
        while (true) {
            StringTokenizer st = new StringTokenizer(in.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            if (x == 0 && y == 0) {
                break;
            }
            x--;
            y--;
            int t = x;
            x = y;
            y = t;
            grid = new char[3][3];
            for (int i = 0; i < 3; i++) {
                st = new StringTokenizer(in.readLine());
                for (int j = 0; j < 3; j++) {
                    grid[i][j] = st.nextToken().charAt(0);
                }
            }
            Cube[][] cubes = new Cube[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Cube c = new Cube('W', 'R');
                    if (i == x && j == y) {
                        c = null;
                    }
                    cubes[i][j] = c;
                }
            }
            set = new HashSet<>();
            State init = new State(cubes, x, y);
            set.add(init.toString());
            ans = Integer.MAX_VALUE;
            dfs(init);
            if (ans == Integer.MAX_VALUE) {
                out.append("-1\n");
            } else {
                out.append(String.format("%d\n", ans));
            }
        }
    }

    void print(State s) {
        char[][] tops = s.tops();
        for (int i = 0; i < tops.length; i++) {
            String str = new String(tops[i]);
            out.append(String.format("%s\n", str));
        }
        out.append('\n');
        out.flush();
    }

    State changeState(State s, Dir d, int nx, int ny, int x, int y) {
        State ns = s.clone();
        Cube c;
        if (d == Dir.LEFT) {
            c = ns.cubes[nx][ny].turn(Dir.RIGHT);
        } else if (d == Dir.RIGHT) {
            c = ns.cubes[nx][ny].turn(Dir.LEFT);
        } else if (d == Dir.TOP) {
            c = ns.cubes[nx][ny].turn(Dir.BOTTOM);
        } else {
            c = ns.cubes[nx][ny].turn(Dir.TOP);
        }
        ns.x = nx;
        ns.y = ny;
        ns.cubes[x][y] = c;
        ns.cubes[nx][ny] = null;
        return ns;
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
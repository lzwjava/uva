import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    Scanner in;
    PrintWriter out;

    Main() {
        in = new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
    }

    void solve() throws IOException {
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
        Main main = new Main();
        main.solve();
        main.close();
    }
}

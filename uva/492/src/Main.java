import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Main {

    BufferedReader in;
    PrintWriter out;

    String vowels = "aeiouAEIOU";

    Main() {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
    }
    
    String fixWord(String word) {
        char fch = word.charAt(0);
        if (vowels.indexOf(fch) != -1) {
            word += "ay";
        } else {
            word = word.substring(1) + word.charAt(0) + "ay";
        }
        return word;
    }
   
    void solve() throws IOException {
        int len = 100000000;
        char cbuf[] = new char[len];
        int offset = 0;
        while (true) {
            int read = in.read(cbuf, offset, len);
            if (read == -1) {
                break;
            }
            String word = "";
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < read; i++) {
                char ch = cbuf[i];
                if (Character.isAlphabetic(ch)) {
                    word += ch;
                } else {
                    if (!word.isEmpty()) {
                        sb.append(fixWord(word));
                        word = "";
                    }
                    sb.append(ch);
                }
            }
            if (!word.isEmpty()) {
                sb.append(fixWord(word));
            }
            out.append(sb.toString());
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
        boolean isLocal = System.getProperty("os.name").equals("Mac OS X");        
        if (isLocal) {
            inStream = new FileInputStream("1.in");
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

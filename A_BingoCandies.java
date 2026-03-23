import java.io.*;
import java.util.*;

public class A_BingoCandies {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();
        int t = fs.nextInt();
        while (t-- > 0) {
            int n = fs.nextInt();
            long maxFreq = 0;
            Map<Integer, Integer> freq = new HashMap<>();
            for (int i = 0; i < n * n; i++) {
                int v = fs.nextInt();
                int f = freq.getOrDefault(v, 0) + 1;
                freq.put(v, f);
                if (f > maxFreq) maxFreq = f;
            }

            if (n == 1) {
                out.append("NO\n");
                continue;
            }

            long limit = (long) n * (n - 1); // max of a color to avoid forcing a monochrome row/col
            out.append(maxFreq <= limit ? "YES\n" : "NO\n");
        }
        System.out.print(out.toString());
    }

    // Simple fast scanner
    private static class FastScanner {
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        private final InputStream in;

        FastScanner(InputStream in) {
            this.in = in;
        }

        private int readByte() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        Integer nextInt() throws IOException {
            int c;
            do {
                c = readByte();
                if (c == -1) return null;
            } while (c <= ' ');
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = readByte();
            }
            int val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = readByte();
            }
            return val * sign;
        }
    }
}

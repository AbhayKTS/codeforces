import java.io.*;
import java.util.*;

public class E_SumOfDigitsAndAgain {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder out = new StringBuilder();
        while (t-- > 0) {
            out.append(solve(br.readLine().trim())).append('\n');
        }
        System.out.print(out);
    }

    // Sum of digits of a positive integer
    private static int digitSum(int v) {
        int s = 0;
        while (v > 0) { s += v % 10; v /= 10; }
        return s;
    }

    // Sum of ALL digit VALUES across the entire string S(v)
    // S(v) = str(v) + S(digitSum(v)) if v > 9, else str(v)
    // g(v) = digitSum(v) + g(digitSum(v))  for v > 9
    // g(v) = v                              for v <= 9
    // For v <= 9*10^5: g(v) <= 72, always < 100
    //
    // Loop structure: add digits of v first, then break if v is a single digit.
    // This ensures the final single-digit value is counted exactly once.
    private static int gVal(int v) {
        int total = 0;
        while (true) {
            int x = v;
            while (x > 0) { total += x % 10; x /= 10; }
            if (v <= 9) break;   // single-digit already counted above; stop here
            v = digitSum(v);
        }
        return total;
    }

    // Build the string S(v) by repeatedly appending digit sums
    private static String buildS(int v) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            sb.append(v);
            if (v <= 9) break;
            v = digitSum(v);
        }
        return sb.toString();
    }

    private static String solve(String s) {
        int n = s.length();
        // Single digit: S(x) = x already
        if (n == 1) return s;

        int[] freq = new int[10];
        int T = 0;
        for (char c : s.toCharArray()) {
            int d = c - '0';
            freq[d]++;
            T += d;
        }

        // Find s1 = digitSum(x) satisfying s1 + g(s1) = T.
        // For s1 > 9: g(s1) < 100, so s1 must be in [T-100, T).
        // For s1 <= 9: g(s1) = s1, so T = 2*s1 (checked in the same loop).
        for (int s1 = 1; s1 < T; s1++) {
            if (s1 > 9 && s1 < T - 100) continue;   // safe skip: g(s1) < 100
            if (s1 + gVal(s1) != T) continue;

            String suffix = buildS(s1);

            // Check digit multiset of suffix is a submultiset of freq
            int[] sf = new int[10];
            for (char c : suffix.toCharArray()) sf[c - '0']++;
            boolean ok = true;
            for (int d = 0; d < 10; d++) {
                if (sf[d] > freq[d]) { ok = false; break; }
            }
            if (!ok) continue;

            // Remaining digits form x; verify their digit sum equals s1
            int[] xf = new int[10];
            int xSum = 0;
            for (int d = 0; d < 10; d++) {
                xf[d] = freq[d] - sf[d];
                xSum += d * xf[d];
            }
            if (xSum != s1) continue;

            // Build x: largest digit first to avoid leading zeros
            StringBuilder xb = new StringBuilder();
            for (int d = 9; d >= 1; d--) {
                for (int i = 0; i < xf[d]; i++) xb.append((char) ('0' + d));
            }
            for (int i = 0; i < xf[0]; i++) xb.append('0');

            return xb.toString() + suffix;
        }

        return s; // unreachable for valid inputs per problem constraints
    }
}

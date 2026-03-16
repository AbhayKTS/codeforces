import java.util.*;
import java.io.*;

public class E_SumOfDigits {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine().trim());

        while (t-- > 0) {
            String s = br.readLine().trim();
            int n = s.length();

            // Single-digit string: S(x) = str(x) for x <= 9, answer is s itself.
            if (n == 1) {
                sb.append(s).append('\n');
                continue;
            }

            int[] cnt = new int[10];
            int T = 0;
            for (char c : s.toCharArray()) {
                int d = c - '0';
                cnt[d]++;
                T += d;
            }

            // Find s1 (digit sum of x, the first segment after x) such that:
            //   T = s1 + g(s1)
            // where g(s1) = sum of all individual digit values in the string S(s1).
            // g(n) = digit_sum(n) + g(digit_sum(n))  for n > 9
            // g(n) = n                                for n <= 9
            //
            // When T = s1 + g(s1), the remaining digits (cnt - digits of S(s1)) form x
            // with digit_sum(x) = s1, so x + S(s1) = S(x). Any arrangement of x's digits
            // (non-zero leading digit) gives a valid answer.
            //
            // Total iterations across all test cases <= 9 * sum(|s|) <= 9*10^5.
            for (int s1 = 1; s1 < T; s1++) {
                // Build chain s1 -> digit_sum(s1) -> ... -> sk (single digit)
                // and compute g(s1) simultaneously.
                // Chain length is at most ~4 for s1 up to 9*10^5 (each step shrinks dramatically).
                int[] chain = new int[10]; // 10 is a safe upper bound
                int chainLen = 0;
                int cur = s1;
                int gSum = 0;

                while (cur > 9) {
                    chain[chainLen++] = cur;
                    int dsum = digitSum(cur);
                    gSum += dsum;   // contributes digit_sum(cur) = next value in chain
                    cur = dsum;
                }
                chain[chainLen++] = cur;  // sk: single digit
                gSum += cur;              // g(sk) = sk itself (single digit)

                // where s2=digit_sum(s1), s3=digit_sum(s2), ..., sk is the final single digit.
                // g(s1) = s2 + s3 + ... + sk (loop) + sk (g(sk)=sk), i.e. s2+...+sk+sk.
                if (s1 + gSum != T) continue;

                // Count digits used by the tail string S(s1) = str(s1)+str(s2)+...+str(sk)
                int[] tailCnt = new int[10];
                for (int i = 0; i < chainLen; i++) {
                    int v = chain[i];
                    while (v > 0) {
                        tailCnt[v % 10]++;
                        v /= 10;
                    }
                }

                // Check tail digits are available in input
                boolean ok = true;
                for (int i = 0; i < 10; i++) {
                    if (tailCnt[i] > cnt[i]) { ok = false; break; }
                }
                if (!ok) continue;

                // Remaining digits go to x; compute count and length
                int[] xCnt = cnt.clone();
                int xLen = n;
                for (int i = 0; i < 10; i++) {
                    xCnt[i] -= tailCnt[i];
                    xLen -= tailCnt[i];
                }

                // x must be a multi-digit number (x > 9), so it needs >= 2 digits.
                // (If xLen == 1, x would be a single digit and S(x) = str(x) alone, no tail.)
                if (xLen < 2) continue;

                // Build x: put the largest non-zero digit first to avoid leading zeros,
                // then remaining digits in descending order (any valid order works).
                for (int i = 9; i >= 1; i--) {
                    if (xCnt[i] > 0) {
                        sb.append((char) ('0' + i));
                        xCnt[i]--;
                        break;
                    }
                }
                for (int i = 9; i >= 0; i--) {
                    for (int j = 0; j < xCnt[i]; j++) {
                        sb.append((char) ('0' + i));
                    }
                }

                // Append tail: str(s1) + str(s2) + ... + str(sk)
                for (int i = 0; i < chainLen; i++) {
                    sb.append(chain[i]);
                }
                sb.append('\n');
                break;
            }
        }

        System.out.print(sb);
    }

    static int digitSum(int n) {
        int s = 0;
        while (n > 0) {
            s += n % 10;
            n /= 10;
        }
        return s;
    }
}

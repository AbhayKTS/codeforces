import java.util.*;
import java.io.*;
public class B_Cyclists {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int[] a = new int[n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                a[i] = Integer.parseInt(st.nextToken());
            }
            long winCost = a[p - 1];
            int setupNeeded = Math.max(0, p - k);
            long setupCost = 0;
            if (setupNeeded > 0) {
                int[] before = Arrays.copyOfRange(a, 0, p - 1);
                Arrays.sort(before);
                for (int i = 0; i < setupNeeded; i++) {
                    setupCost += before[i];
                }
            }

            int cycleNeeded = Math.max(0, n - k);
            long cycleExtra = 0;
            if (cycleNeeded > 0) {
                int[] others = new int[n - 1];
                int idx = 0;
                for (int i = 0; i < n; i++) {
                    if (i != p - 1) others[idx++] = a[i];
                }
                Arrays.sort(others);
                for (int i = 0; i < cycleNeeded; i++) {
                    cycleExtra += others[i];
                }
            }

            long numerator = (long) m - setupCost + cycleExtra;
            long denominator = winCost + cycleExtra;
            long r = Math.max(0, numerator / denominator);

            sb.append(r).append('\n');
        }

        System.out.print(sb);
    }
}

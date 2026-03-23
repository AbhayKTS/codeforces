import java.util.*;
import java.io.*;

public class D1_TreeOrientation {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            boolean[][] s = new boolean[n][n];

            for (int i = 0; i < n; i++) {
                String line = br.readLine().trim();
                for (int j = 0; j < n; j++) {
                    s[i][j] = line.charAt(j) == '1';
                }
            }

            // Validation 1: diagonal must all be 1
            boolean valid = true;
            for (int i = 0; i < n; i++) {
                if (!s[i][i]) { valid = false; break; }
            }

            // Validation 2: no mutual reachability between different nodes
            // (oriented trees have no directed cycles)
            if (valid) {
                outer:
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (i != j && s[i][j] && s[j][i]) {
                            valid = false;
                            break outer;
                        }
                    }
                }
            }

            // Validation 3: transitivity - if i->j and j->k then i->k
            if (valid) {
                outer:
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (i != j && s[i][j]) {
                            for (int k = 0; k < n; k++) {
                                if (k != i && s[j][k] && !s[i][k]) {
                                    valid = false;
                                    break outer;
                                }
                            }
                        }
                    }
                }
            }

            if (!valid) {
                sb.append("No\n");
                continue;
            }

            // Find direct edges: u-v is a direct edge if exactly one of s[u][v],
            // s[v][u] is true, and no intermediate node w lies on the path.
            // In an oriented tree edge u->v, no w satisfies s[u][w]=1 and s[w][v]=1.
            List<int[]> edges = new ArrayList<>();
            for (int u = 0; u < n; u++) {
                for (int v = u + 1; v < n; v++) {
                    boolean uToV = s[u][v];
                    boolean vToU = s[v][u];

                    if (!uToV && !vToU) continue; // neither can reach the other

                    // Check no intermediate node on directed path
                    boolean hasIntermediate = false;
                    for (int w = 0; w < n; w++) {
                        if (w == u || w == v) continue;
                        if ((uToV && s[u][w] && s[w][v]) || (vToU && s[v][w] && s[w][u])) {
                            hasIntermediate = true;
                            break;
                        }
                    }

                    if (!hasIntermediate) {
                        edges.add(uToV ? new int[]{u, v} : new int[]{v, u});
                    }
                }
            }

            // Must have exactly n-1 edges
            if (edges.size() != n - 1) {
                sb.append("No\n");
                continue;
            }

            // Check connectivity (and no cycles) using union-find
            int[] parent = new int[n];
            Arrays.fill(parent, -1);
            boolean hasCycle = false;
            for (int[] e : edges) {
                int pu = find(parent, e[0]), pv = find(parent, e[1]);
                if (pu == pv) { hasCycle = true; break; }
                parent[pu] = pv;
            }
            if (hasCycle) {
                sb.append("No\n");
                continue;
            }
            // All nodes in one component?
            int root = find(parent, 0);
            for (int i = 1; i < n; i++) {
                if (find(parent, i) != root) { valid = false; break; }
            }
            if (!valid) {
                sb.append("No\n");
                continue;
            }

            // Validate: compute reachability of constructed directed tree via BFS,
            // then compare against the input matrix
            List<List<Integer>> adj = new ArrayList<>();
            for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
            for (int[] e : edges) adj.get(e[0]).add(e[1]);

            boolean matches = true;
            outer:
            for (int start = 0; start < n; start++) {
                boolean[] reachable = new boolean[n];
                reachable[start] = true;
                Queue<Integer> queue = new ArrayDeque<>();
                queue.add(start);
                while (!queue.isEmpty()) {
                    int cur = queue.poll();
                    for (int next : adj.get(cur)) {
                        if (!reachable[next]) {
                            reachable[next] = true;
                            queue.add(next);
                        }
                    }
                }
                for (int j = 0; j < n; j++) {
                    if (reachable[j] != s[start][j]) {
                        matches = false;
                        break outer;
                    }
                }
            }

            if (!matches) {
                sb.append("No\n");
            } else {
                edges.sort((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
                sb.append("Yes\n");
                for (int[] e : edges) {
                    sb.append(e[0] + 1).append(' ').append(e[1] + 1).append('\n');
                }
            }
        }

        System.out.print(sb);
    }

    static int find(int[] parent, int x) {
        if (parent[x] < 0) return x;
        return parent[x] = find(parent, parent[x]);
    }
}

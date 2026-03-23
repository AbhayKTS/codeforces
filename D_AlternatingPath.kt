import java.util.LinkedList
fun main() {
    val br = System.`in`.bufferedReader()
    val sb = StringBuilder()
    val t = br.readLine().trim().toInt()
    repeat(t) {
        val (n, m) = br.readLine().trim().split(" ").map { it.toInt() }
        val adj = Array(n + 1) { mutableListOf<Int>() }
        repeat(m) {
            val (u, v) = br.readLine().trim().split(" ").map { it.toInt() }
            adj[u].add(v)
            adj[v].add(u)
        }
        val colour = IntArray(n + 1) { -1 }
        var ans = 0
        for (start in 1..n) {
            if (colour[start] != -1) continue
            colour[start] = 0
            var c0 = 1
            var c1 = 0
            var bipartite = true
            val queue = LinkedList<Int>()
            queue.add(start)
            while (queue.isNotEmpty()) {
                val cur = queue.poll()
                for (nb in adj[cur]) {
                    if (colour[nb] == -1) {
                        colour[nb] = 1 - colour[cur]
                        if (colour[nb] == 0) c0++ else c1++
                        queue.add(nb)
                    } else if (colour[nb] == colour[cur]) {
                        bipartite = false
                    }
                }
            }
            if (bipartite) ans += maxOf(c0, c1)
        }
        sb.appendLine(ans)
    }

    print(sb)
}

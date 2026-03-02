import java.util.LinkedList

fun main() {
    val br = System.`in`.bufferedReader()
    val sb = StringBuilder()
    val t = br.readLine().trim().toInt()
    
    repeat(t) {
        val (n, m) = br.readLine().trim().split(" ").map { it.toInt() }
        
        data class Edge(val u: Int, val v: Int, val w: Int)
        
        val edges = ArrayList<Edge>(m)
        repeat(m) {
            val (u, v, w) = br.readLine().trim().split(" ").map { it.toInt() }
            edges.add(Edge(u, v, w))
        }
        
        // Sort edges by weight descending
        edges.sortByDescending { it.w }
        
        // DSU
        val parent = IntArray(n + 1) { it }
        val rank = IntArray(n + 1)
        
        fun find(x: Int): Int {
            var r = x
            while (parent[r] != r) r = parent[r]
            var c = x
            while (c != r) {
                val next = parent[c]
                parent[c] = r
                c = next
            }
            return r
        }
        
        fun unite(a: Int, b: Int): Boolean {
            val ra = find(a)
            val rb = find(b)
            if (ra == rb) return false
            if (rank[ra] < rank[rb]) {
                parent[ra] = rb
            } else if (rank[ra] > rank[rb]) {
                parent[rb] = ra
            } else {
                parent[rb] = ra
                rank[ra]++
            }
            return true
        }
        
        // Adjacency list for edges added so far
        val adj = Array(n + 1) { mutableListOf<Int>() }
        
        var bestEdge: Edge? = null
        
        for (e in edges) {
            if (find(e.u) == find(e.v)) {
                // This edge creates a cycle - it has the minimum weight
                bestEdge = e
                break
            }
            unite(e.u, e.v)
            adj[e.u].add(e.v)
            adj[e.v].add(e.u)
        }
        
        // Find path from bestEdge.u to bestEdge.v using BFS on added edges
        val start = bestEdge!!.u
        val end = bestEdge!!.v
        val weight = bestEdge!!.w
        
        val prev = IntArray(n + 1) { -1 }
        val visited = BooleanArray(n + 1)
        val queue = LinkedList<Int>()
        queue.add(start)
        visited[start] = true
        
        while (queue.isNotEmpty()) {
            val cur = queue.poll()
            if (cur == end) break
            for (next in adj[cur]) {
                if (!visited[next]) {
                    visited[next] = true
                    prev[next] = cur
                    queue.add(next)
                }
            }
        }
        
        // Reconstruct path
        val cycle = mutableListOf<Int>()
        var cur = end
        while (cur != -1) {
            cycle.add(cur)
            cur = prev[cur]
        }
        
        sb.appendLine("$weight ${cycle.size}")
        sb.appendLine(cycle.joinToString(" "))
    }
    
    print(sb)
}

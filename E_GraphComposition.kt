fun main() {
    val br = System.`in`.bufferedReader()
    val sb = StringBuilder()
    val t = br.readLine().trim().toInt()
    
    repeat(t) {
        val (n, m1, m2) = br.readLine().trim().split(" ").map { it.toInt() }
        
        // Read edges of F
        val edgesF = Array(m1) {
            val (u, v) = br.readLine().trim().split(" ").map { it.toInt() }
            u to v
        }
        
        // Read edges of G and find connected components using DSU
        val parentG = IntArray(n + 1) { it }
        val rankG = IntArray(n + 1)
        
        fun findG(x: Int): Int {
            var r = x
            while (parentG[r] != r) r = parentG[r]
            var c = x
            while (c != r) { val next = parentG[c]; parentG[c] = r; c = next }
            return r
        }
        
        fun uniteG(a: Int, b: Int) {
            val ra = findG(a); val rb = findG(b)
            if (ra == rb) return
            if (rankG[ra] < rankG[rb]) parentG[ra] = rb
            else if (rankG[ra] > rankG[rb]) parentG[rb] = ra
            else { parentG[rb] = ra; rankG[ra]++ }
        }
        
        repeat(m2) {
            val (u, v) = br.readLine().trim().split(" ").map { it.toInt() }
            uniteG(u, v)
        }
        
        // DSU for F-subgraph within each G-component
        val parentF = IntArray(n + 1) { it }
        val rankF = IntArray(n + 1)
        
        fun findF(x: Int): Int {
            var r = x
            while (parentF[r] != r) r = parentF[r]
            var c = x
            while (c != r) { val next = parentF[c]; parentF[c] = r; c = next }
            return r
        }
        
        fun uniteF(a: Int, b: Int) {
            val ra = findF(a); val rb = findF(b)
            if (ra == rb) return
            if (rankF[ra] < rankF[rb]) parentF[ra] = rb
            else if (rankF[ra] > rankF[rb]) parentF[rb] = ra
            else { parentF[rb] = ra; rankF[ra]++ }
        }
        
        var toRemove = 0
        
        for ((u, v) in edgesF) {
            if (findG(u) != findG(v)) {
                // Edge crosses G-components, must remove
                toRemove++
            } else {
                // Edge is within a G-component, keep it in F-subgraph
                uniteF(u, v)
            }
        }
        
        // For each G-component, count how many F-sub-components exist
        // If a G-component has c F-sub-components, we need c-1 edges added
        // Group vertices by G-component, then count distinct F-roots
        val gCompFRoots = HashMap<Int, HashSet<Int>>()
        for (i in 1..n) {
            val gc = findG(i)
            val fc = findF(i)
            gCompFRoots.getOrPut(gc) { HashSet() }.add(fc)
        }
        
        var toAdd = 0
        for ((_, froots) in gCompFRoots) {
            toAdd += froots.size - 1
        }
        
        sb.appendLine(toRemove + toAdd)
    }
    
    print(sb)
}
fun main() {
    val br = System.`in`.bufferedReader()
    val sb = StringBuilder()
    val t = br.readLine().trim().toInt()
    repeat(t) {
        val (n, m, k) = br.readLine().trim().split(" ").map { it.toInt() }
        val a = br.readLine().trim().split(" ").map { it.toInt() }
        val q = br.readLine().trim().split(" ").map { it.toInt() }

        val known = BooleanArray(n + 1)
        for (x in q) known[x] = true
        if (k == n) {
            for (i in 0 until m) sb.append('1')
        } else if (k == n - 1) {
            // find the one question he doesn't know
            // sum of 1..n minus sum of known questions
            val totalSum = n.toLong() * (n + 1) / 2
            val knownSum = q.fold(0L) { acc, x -> acc + x }
            val missing = (totalSum - knownSum).toInt()
            for (i in 0 until m) {
                if (a[i] == missing) sb.append('1')
                else sb.append('0')
            }
        } else {
            for (i in 0 until m) sb.append('0')
        }
        sb.append('\n')
    }
    print(sb)
}

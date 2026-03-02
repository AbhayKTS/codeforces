fun main() {
    val t = readLine()!!.trim().toInt()
    repeat(t) {
        val (a1, a2, a4, a5) = readLine()!!.trim().split(" ").map { it.toInt() }

        // possible candidates for a3 that could satisfy at least one condition:
        // condition 1: a3 = a1 + a2
        // condition 2: a4 = a2 + a3  =>  a3 = a4 - a2
        // condition 3: a5 = a3 + a4  =>  a3 = a5 - a4
        val candidates = setOf(a1 + a2, a4 - a2, a5 - a4)

        var best = 0
        for (a3 in candidates) {
            var score = 0
            if (a3 == a1 + a2) score++
            if (a4 == a2 + a3) score++
            if (a5 == a3 + a4) score++
            if (score > best) best = score
        }

        println(best)
    }
}

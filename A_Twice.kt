fun main() {
    val t = readLine()!!.trim().toInt()
    repeat(t) {
        val n = readLine()!!.trim().toInt()
        val a = readLine()!!.trim().split(" ").map { it.toInt() }

        val freq = mutableMapOf<Int, Int>()
        for (x in a)
            freq[x] = (freq[x] ?: 0) + 1

        var score = 0
        for (count in freq.values)
            score += count / 2

        println(score)
    }
}

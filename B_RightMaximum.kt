fun main() {
    val t = readLine()!!.trim().toInt()
    repeat(t) {
        val n = readLine()!!.trim().toInt()
        val a = readLine()!!.trim().split(" ").map { it.toInt() }

        var ops = 0
        var prefixMax = 0
        for (x in a) {
            if (x >= prefixMax) {
                ops++
                prefixMax = x
            }
        }

        println(ops)
    }
}

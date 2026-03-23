fun main() {
    val t = readLine()!!.trim().toInt()
    repeat(t) {
        val n = readLine()!!.trim().toInt()
        val s = readLine()!!.trim()

        val received = mutableSetOf<Int>()
        var pos = 0
        received.add(pos)

        repeat(n) {
            pos = if (s[pos] == 'R') pos + 1 else pos - 1
            received.add(pos)
        }

        println(received.size)
    }
}

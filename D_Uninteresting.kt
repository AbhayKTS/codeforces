fun main() {
    val br = System.`in`.bufferedReader()
    val sb = StringBuilder()
    val t = br.readLine().trim().toInt()
    repeat(t) {
        val s = br.readLine().trim()

        // Only digits whose square is also a digit can be changed:
        // 0->0, 1->1, 2->4, 3->9, these are the only ones where x^2 < 10
        // So only 2s and 3s can meaningfully change:
        //   2 can become 4 (change in digit sum: +2)
        //   3 can become 9 (change in digit sum: +6, i.e. 0 mod 9)
        // Wait: 3->9 changes digit sum by +6 which is +6 mod 9
        // Actually let's reconsider: digits that can be squared:
        // 0^2=0, 1^2=1, 2^2=4, 3^2=9 (all < 10)
        // 4^2=16 (not a digit), so 4+ can't be squared
        // So each 2 can optionally add +2 to digit sum (mod 9)
        // Each 3 can optionally add +6 to digit sum (mod 9)

        var baseSum = 0
        var count2 = 0
        var count3 = 0
        for (ch in s) {
            val d = ch - '0'
            if (d == 2) count2++
            else if (d == 3) count3++
            baseSum += d
        }
        baseSum %= 9

        // Try all combinations of how many 2s and 3s to square
        // Each squared 2 adds 2 mod 9, each squared 3 adds 6 mod 9
        // Since adding 6 mod 9 is equivalent: 3 squared 3s add 18 ≡ 0 mod 9
        // So we only need to try count3 mod 3 options (0, 1, or 2 threes squared)
        // and count2 mod 9 options for 2s (each adds 2)
        // But count2 can be up to 10^5, however adding 2 each time:
        // we only need to try 0..min(count2, 8) since 9*2=18≡0 mod 9

        var found = false
        for (j in 0..minOf(count3, 2)) {
            for (i in 0..minOf(count2, 8)) {
                if ((baseSum + i * 2 + j * 6) % 9 == 0) {
                    found = true
                    break
                }
            }
            if (found) break
        }

        sb.appendLine(if (found) "YES" else "NO")
    }
    print(sb)
}

fun main() {
    val br = System.`in`.bufferedReader()
    val sb = StringBuilder()
    val t = br.readLine().trim().toInt()
    
    repeat(t) {
        val line1 = br.readLine().trim().split(" ")
        val n = line1[0].toInt()
        val s = line1[1].toLong()
        val x = line1[2].toLong()
        val a = br.readLine().trim().split(" ").map { it.toLong() }
        
        // Count subsegments with sum=s and max<=threshold
        // Elements > threshold are barriers splitting into blocks
        // Within each block, use prefix sums + hashmap
        fun countAtMost(threshold: Long): Long {
            var result = 0L
            var i = 0
            while (i < n) {
                if (a[i] > threshold) {
                    i++
                    continue
                }
                // Found start of a block where all elements <= threshold
                val map = HashMap<Long, Int>()
                var prefixSum = 0L
                map[0L] = 1
                while (i < n && a[i] <= threshold) {
                    prefixSum += a[i]
                    // We need prefixSum - prefix[l-1] = s => prefix[l-1] = prefixSum - s
                    val target = prefixSum - s
                    result += (map[target] ?: 0)
                    map[prefixSum] = (map[prefixSum] ?: 0) + 1
                    i++
                }
            }
            return result
        }
        
        val ans = countAtMost(x) - countAtMost(x - 1)
        sb.appendLine(ans)
    }
    
    print(sb)
}
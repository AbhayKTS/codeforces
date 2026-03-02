fun main() {
    val br = System.`in`.bufferedReader()
    val sb = StringBuilder()
    val t = br.readLine().trim().toInt()
    
    repeat(t) {
        val (n, k) = br.readLine().trim().split(" ").map { it.toInt() }
        val a = br.readLine().trim().split(" ").map { it.toInt() }
        val b = br.readLine().trim().split(" ").map { it.toInt() }
        
        // Collect all critical price points
        val prices = mutableSetOf<Int>()
        
        for (i in 0 until n) {
            prices.add(a[i])     // last price for positive review
            prices.add(a[i] + 1) // first price for negative review
            prices.add(b[i])     // last price for any purchase
            prices.add(b[i] + 1) // first price for no purchase
        }
        
        var maxRevenue = 0L
        
        for (price in prices) {
            var negativeReviews = 0
            var totalRevenue = 0L
            
            for (i in 0 until n) {
                when {
                    price <= a[i] -> {
                        // positive review + buy
                        totalRevenue += price
                    }
                    price <= b[i] -> {
                        // negative review + buy
                        negativeReviews++
                        totalRevenue += price
                    }
                    // else: no purchase
                }
            }
            
            if (negativeReviews <= k) {
                maxRevenue = maxOf(maxRevenue, totalRevenue)
            }
        }
        
        sb.appendLine(maxRevenue)
    }
    
    print(sb)
}
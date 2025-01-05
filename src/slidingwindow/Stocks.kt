package slidingwindow

/**
 * Problem Statement:
 * [Best Time to Buy and Sell Stock](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/description/)
 */
fun maxProfit(prices: IntArray): Int {
    if (prices.size <= 1) return 0

    var buy = 0
    var sell = 0
    var maxProfit = 0

    while (++sell <= prices.size - 1) {
        val currProfit = prices[sell] - prices[buy]
        if (currProfit < 0) buy = sell
        if (currProfit > maxProfit) maxProfit = currProfit
    }
    return maxProfit
}
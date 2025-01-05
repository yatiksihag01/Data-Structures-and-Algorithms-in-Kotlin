package slidingwindow

/**
 * Problem statement: [Two Sum II](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/description/)
 */
class TwoSumSortedArray {
    fun twoSum(numbers: IntArray, target: Int): IntArray {
        if (numbers.size <= 2) return intArrayOf(1, 2)
        var left = 0
        var right = numbers.size - 1

        while (numbers[left] + numbers[right] != target) {
            if (numbers[left] + numbers[right] > target) right--
            else left++
        }
        return intArrayOf(++left, ++right)

    }
}
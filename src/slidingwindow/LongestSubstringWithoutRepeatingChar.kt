package slidingwindow

/**
 * Problem Statement:
 * [Longest Substring Without Repeating Characters](https://neetcode.io/problems/longest-substring-without-duplicates)
 *
 * Approach:
 * This implementation uses the sliding window technique with a HashMap
 * to track the most recent index of each character. The sliding window
 * ensures that we efficiently handle duplicate characters and compute
 * the maximum length of a substring without repetition.
 *
 * Time Complexity: O(n), where n is the length of the input string.
 * Space Complexity: O(k), where k is the size of the character set.
 *
 * @param s The input string.
 * @return The length of the longest substring without repeating characters.
 */
fun lengthOfLongestSubstring(s: String): Int {
    if (s.length <= 1) return s.length

    var back = 0
    var front = 0
    var maxLen = 0

    val map = HashMap<Char, Int>()
    map.put(s[0], 0)

    while (++front <= s.length - 1) {
        val index = map[s[front]]
        if (index != null && index >= back) {
            back = map[s[front]]!! + 1
        }
        maxLen = maxLen.coerceAtLeast(front - back + 1)
        map.put(s[front], front)
    }
    return maxLen
}
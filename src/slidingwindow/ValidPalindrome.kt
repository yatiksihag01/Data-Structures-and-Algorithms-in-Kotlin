package slidingwindow

/**
 * A palindrome is a string that reads the same forward and backward.
 *
 * Practice here: [ValidPalindrome](https://neetcode.io/problems/is-palindrome)
 *
 * @return `true` if it is a palindrome, otherwise return `false`.
 */
fun String.isValidPalindrome(): Boolean {
    var s = ""
    for (i in this.indices) {
        val charCode = this[i].lowercaseChar().code
        if (charCode >= 'a'.code && charCode <= 'z'.code || this[i].isDigit()) {
            s += this[i].lowercaseChar()
        }
    }
    var left = 0
    var right = s.length - 1
    while (left < right) {
        if (s[left++] != s[right--]) return false
    }
    return true
}
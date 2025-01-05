package backtracking

import slidingwindow.isValidPalindrome

/**
 * Extension function to find all possible palindrome partitions of a given string.
 *
 * This function splits the string into all possible partitions where each substring is a palindrome.
 * A partition is a list of substrings. The function returns all such partitions as a list of lists.
 *
 * @receiver String - The input string to be partitioned.
 * @return List<List<String>> - A list of lists containing all possible palindrome partitions.
 *
 * Example:
 * ```
 * val partitions = "aab".partitions()
 * println(partitions) // Output: [[a, a, b], [aa, b]]
 * ```
 */
fun String.partitions(): List<List<String>> {
    val currentPartition = mutableListOf<String>()
    val res = mutableListOf<List<String>>()
    partitionsRec(0, this, currentPartition, res)
    return res
}

/**
 * Recursive helper function to find all palindrome partitions starting from a given index.
 *
 * @param index Int - The starting index in the string to consider for partitioning.
 * @param str String - The input string being partitioned.
 * @param currentPartition MutableList<String> - Current list of substrings forming a valid partition so far.
 * @param res MutableList<List<String>> - The resulting list of lists to store all valid partitions.
 */
private fun partitionsRec(
    index: Int,
    str: String,
    currentPartition: MutableList<String>,
    res: MutableList<List<String>>
) {
    if (index == str.length) {
        res.add(ArrayList(currentPartition))
        return
    }
    for (i in index until str.length) {
        if (str.substring(index, i + 1).isValidPalindrome()) {
            currentPartition.add(str.substring(index, i + 1))
            partitionsRec(i + 1, str, currentPartition, res)
            currentPartition.removeLast()
        }
    }
}
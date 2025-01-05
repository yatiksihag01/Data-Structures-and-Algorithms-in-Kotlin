package sorting

import swap

/**
 * Extension function to perform in-place insertion sort on an IntArray.
 *
 * Insertion sort builds the sorted array one element at a time by repeatedly
 * taking the next element from the unsorted part and inserting it into its correct
 * position in the sorted part.
 *
 * Time Complexity: O(n^2) in the worst case, O(n) in the best case (already sorted array).
 * Space Complexity: O(1), as the sorting is performed in place.
 *
 * Example usage:
 * ```
 * val array = intArrayOf(64, 25, 12, 22, 11)
 * array.insertionSort()
 * println(array.contentToString()) // Output: [11, 12, 22, 25, 64]
 * ```
 */
fun IntArray.insertionSort() {
    for (i in 0 until this.size - 1) {
        var j = i + 1
        while (j > 0 && this[j] < this[j - 1]) {
            this.swap(j, j - 1)
            j--
        }
        println(this.contentToString())
    }
}

/**
 * Extension function to perform in-place selection sort on an array of [Comparable] elements.
 *
 * Selection sort is a simple sorting algorithm that divides the array into
 * a sorted and an unsorted region. It repeatedly selects the smallest element
 * from the unsorted region and swaps it with the first element of the unsorted region.
 *
 * This function works for any type that implements the Comparable interface, such as Int, String, etc.
 *
 * Time Complexity: O(n^2), where n is the size of the array.
 * Space Complexity: O(1), as the sorting is performed in place.
 *
 * Example usage:
 * ```
 * val intArray = intArrayOf(64, 25, 12, 22, 11)
 * intArray.selectionSort()
 * println(intArray.contentToString()) // Output: [11, 12, 22, 25, 64]
 *
 * val stringArray = arrayOf("banana", "apple", "cherry", "date")
 * stringArray.selectionSort()
 * println(stringArray.contentToString()) // Output: [apple, banana, cherry, date]
 * ```
 */
fun <T : Comparable<T>> Array<T>.selectionSort() {
    (0 until this.size - 1).forEach { i ->
        var minIndex = i
        for (j in i + 1 until this.size) {
            if (this[j] < this[minIndex]) {
                minIndex = j
            }
        }
        this.swap(i, minIndex)

        // Comment below line if you do not want to visualize sorting process.
        println(this.contentToString())
    }
}

/**
 * Extension function to perform in-place selection sort on an IntArray.
 *
 * This function is an overload of the generic selectionSort and is implemented
 * for performance optimization specifically for primitive IntArray.
 */
fun IntArray.selectionSort() {
    for (i in 0 until this.size - 1) {
        var minIndex = i
        for (j in i + 1 until this.size) {
            if (this[j] < this[minIndex]) {
                minIndex = j
            }
        }
        this.swap(i, minIndex)

        // Comment below line if you do not want to visualize.
        println(this.contentToString())
    }
}

/**
 * Helper function for [quickSortRecursive] to partition the array around a pivot element.
 *
 * All elements smaller than or equal to the pivot are moved to the left of the pivot,
 * and all elements greater than the pivot are moved to the right.
 *
 * @param array The array to partition.
 * @param low The starting index of the partitioning range.
 * @param high The ending index of the partitioning range.
 * @return The final index of the pivot element.
 */
private fun partition(array: IntArray, low: Int, high: Int): Int {
    val pivot = array[low] // Choose the first element as the pivot
    var i = low + 1
    var j = high

    while (true) {
        // Move `i` right until an element larger than the pivot is found
        while (i < high && array[i] <= pivot) i++

        // Move `j` left until an element smaller than or equal to the pivot is found
        while (j > low && array[j] > pivot) j--

        if (i >= j) break
        array.swap(i, j)

        // Comment this if you do not want to visualize.
        println("i = $i, j = $j")
        println(array.contentToString())
    }

    array.swap(low, j)
    return j
}

/**
 * Helper function to perform the recursive quicksort.
 *
 * @param array The array to sort.
 * @param low The starting index of the current subarray.
 * @param high The ending index of the current subarray.
 */
private fun quickSortRecursive(array: IntArray, low: Int, high: Int) {
    if (low >= high) return
    val pivotIndex = partition(array, low, high)
    quickSortRecursive(array, low, pivotIndex - 1)
    quickSortRecursive(array, pivotIndex + 1, high)
}

/**
 * Extension function to perform in-place quicksort on an IntArray.
 *
 * Quicksort is a divide-and-conquer algorithm that partitions an array into
 * two halves based on a pivot, then recursively sorts each half. It is efficient
 * for large datasets and is one of the fastest sorting algorithms in practice.
 *
 * Time Complexity:
 * - Best/Average Case: O(n log n), where n is the size of the array.
 * - Worst Case: O(n^2), occurs when the array is already sorted or reverse sorted and the pivot is poorly chosen.
 *
 * Space Complexity: O(log n) due to the recursion stack.
 *
 * Example usage:
 * ```
 * val array = intArrayOf(64, 25, 12, 22, 11)
 * array.quickSort()
 * println(array.contentToString()) // Output: [11, 12, 22, 25, 64]
 * ```
 */
fun IntArray.quickSort() {
    quickSortRecursive(this, low = 0, high = this.size - 1)
}

/**
 * Merges two sorted sub-arrays of `array` into a single sorted subarray.
 *
 * @param array The array containing the sub-arrays to merge.
 * @param low The starting index of the first subarray.
 * @param mid The ending index of the first subarray. The second subarray starts from `mid + 1`.
 * @param high The ending index of the second subarray.
 */
private fun merge(array: IntArray, low: Int, mid: Int, high: Int) {
    var left = low
    var right = mid + 1
    val temp = IntArray(high - low + 1)
    var tempInd = 0

    while (left <= mid && right <= high) {
        if (array[left] <= array[right]) {
            temp[tempInd++] = array[left++]
        } else {
            temp[tempInd++] = array[right++]
        }
    }
    while (left <= mid) temp[tempInd++] = array[left++]
    while (right <= high) temp[tempInd++] = array[right++]

    for (i in temp.indices) {
        array[low + i] = temp[i]
    }
}

/**
 * Recursively sorts the `array` using the merge sort algorithm.
 *
 * @param array The array to sort.
 * @param low The starting index of the segment to sort.
 * @param high The ending index of the segment to sort.
 */
private fun mergeSortRecursive(array: IntArray, low: Int, high: Int) {
    if (low >= high) return
    val mid = (low + high) / 2

    // Comment below line if you do not want to visualize.
    println("Divide: ${array.copyOfRange(low, high + 1).contentToString()}")

    mergeSortRecursive(array, low, mid)
    mergeSortRecursive(array, mid + 1, high)

    // Merge the sorted halves
    merge(array, low, mid, high)

    // Comment below line if you do not want to visualize.
    println("Merge after sorting: ${array.copyOfRange(low, high + 1).contentToString()}")
}

/**
 * Extension function to perform merge sort on an IntArray.
 *
 * Merge sort is a divide-and-conquer algorithm that divides the array into
 * smaller sub-arrays, recursively sorts them, and then merges the sorted sub-arrays
 * back together. It is a stable sorting algorithm, making it ideal for datasets
 * where the order of equal elements must be preserved.
 *
 * Note: This implementation is **not in-place**. It uses additional memory
 * proportional to the size of the sub-arrays being merged.
 *
 * Time Complexity:
 * - Best/Average/Worst Case: O(n log n), where n is the size of the array.
 *   Merge sort guarantees this time complexity regardless of the input order.
 *
 * Space Complexity: O(n) due to the temporary array used during merging,
 * and O(log n) for the recursion stack.
 *
 * Example usage:
 * ```
 * val array = intArrayOf(64, 25, 12, 22, 11)
 * array.mergeSort()
 * println(array.contentToString()) // Output: [11, 12, 22, 25, 64]
 * ```
 */
fun IntArray.mergeSort() {
    mergeSortRecursive(this, low = 0, high = this.size - 1)
}

/**
 * Extension function to perform bucket sort on an IntArray.
 *
 * Bucket sort is a sorting algorithm that distributes elements into a number of buckets,
 * sorts each bucket individually, and then merges them back to produce a sorted array.
 * It works well for data that is uniformly distributed over a range.
 *
 * Time Complexity:
 * - Best Case: O(n + k), where n is the number of elements and k is the number of buckets.
 * - Worst Case: O(n^2), occurs when all elements end up in one bucket.
 *
 * Space Complexity: O(n + k), where n is the input size and k is the number of buckets.
 *
 * Limitations:
 * - Assumes input is composed of non-negative integers.
 * - May require adjustment of bucket size for non-uniform distributions or larger ranges.
 *
 * Example usage:
 * ```
 * val array = intArrayOf(42, 32, 33, 52, 37, 47, 51)
 * array.bucketSort()
 * println(array.contentToString()) // Output: [32, 33, 37, 42, 47, 51, 52]
 * ```
 */
fun IntArray.bucketSort() {
    if (this.size <= 1) return
    val bucketCount = this.size
    val maxElement = this.max()
    val bucket = List(bucketCount) { ArrayList<Int>() }
    for (element in this) {
        val position = (element * bucketCount) / maxElement
        // coerceAtMost(bucketCount - 1) ensures that maxElement also fit into bucket range
        bucket[position.coerceAtMost(bucketCount - 1)].add(element)
    }
    var index = 0
    for (arr in bucket) {
        arr.sort()

        // Comment if you do not want to visualize
        println(arr)
        arr.forEach { element ->
            this[index++] = element
        }
    }
}
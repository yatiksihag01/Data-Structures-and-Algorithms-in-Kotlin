fun <T : Comparable<T>> Array<T>.swap(i: Int, j: Int) {
    this[i] = this[j].also { this[j] = this[i] }
}

fun IntArray.swap(i: Int, j: Int) {
    this[i] = this[j].also { this[j] = this[i] }
}
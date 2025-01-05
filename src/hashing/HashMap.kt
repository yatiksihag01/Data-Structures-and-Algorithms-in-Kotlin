package hashing

/**
 * A custom implementation of a HashMap data structure.
 *
 * This class uses a chaining to handle collisions in hash buckets.
 * Supports basic operations such as adding, removing, and retrieving key-value pairs.
 *
 * The `initialCapacity` is coerced at 30 because a higher capacity means the underlying bucket array
 * will allocate more memory, even if it’s mostly empty. Larger capacities also negatively impact cache locality.
 * The larger the bucket array, the less likely it is to fit in the CPU cache, leading to slower lookups.
 *
 *
 * @param initialCapacity The initial number of buckets in the map. Defaults to 20.
 * @param loadFactor The maximum load factor before resizing occurs. Defaults to 0.75.
 * @param K The type of keys maintained by this map.
 * @param V The type of mapped values.
 */

class HashMap<K, V>(
    initialCapacity: Int = 20,
    private val loadFactor: Float = 0.75f,
) {
    companion object {
        private const val MAX_CAPACITY = 30
    }

    private data class Node<K, V>(
        val key: K,
        var value: V,
        var next: Node<K, V>? = null
    )

    private var size = 0
    private var capacity = initialCapacity.coerceAtMost(MAX_CAPACITY)
    private var buckets = MutableList<Node<K, V>?>(capacity) { null }

    private fun hash(key: K): Int {
        return key.hashCode() % capacity
    }

    fun isEmpty(): Boolean = size == 0

    /**
     * Returns the number of key-value pairs in the map.
     *
     * @return The size of the map.
     */
    fun size(): Int = size

    /**
     * Retrieves the value associated with the specified key.
     *
     * @param key The key whose associated value is to be returned.
     * @return The value associated with the key, or `null` if the key is not found.
     */
    operator fun get(key: K): V? {
        val hash = hash(key)
        var current = buckets[hash]
        while (current != null) {
            if (current.key == key) return current.value
            current = current.next
        }
        return null
    }

    fun containsKey(key: K): Boolean = get(key) != null

    /**
     * Associates the specified value with the specified key in the map.
     *
     * If the key already exists, its value is replaced with the specified value. If the key does not exist, it is
     * added to the map. Resizes the map if the load factor threshold is exceeded.
     *
     * @param key The key with which the specified value is to be associated.
     * @param value The value to be associated with the key.
     * @return The previous value associated with the key, or `null` if there was no mapping.
     */
    fun put(key: K, value: V): V? {
        val hash = hash(key)
        var current = buckets[hash]

        while (current != null) {
            if (current.key == key) {
                val oldValue = current.value
                current.value = value
                return oldValue
            }
            if (current.next == null) break
            current = current.next
        }
        val newNode = Node(key, value)
        if (current == null) {
            buckets[hash] = newNode
        } else {
            current.next = newNode
        }
        size++
        if (size >= capacity * loadFactor) {
            resize()
        }
        return null
    }

    /**
     * Resizes the underlying bucket array by factor of 2 to accommodate more elements when the load factor
     * threshold is exceeded.
     *
     * This process ensures that the map maintains efficient performance during insertions by reducing collisions.
     */
    private fun resize() {
        val newCapacity = (capacity * 2)
        val oldBuckets = buckets
        buckets = MutableList(newCapacity) { null }
        capacity = newCapacity
        size = 0

        for (bucket in oldBuckets) {
            var current = bucket
            while (current != null) {
                put(current.key, current.value)
                current = current.next
            }
        }
    }

    /**
     * Removes the mapping for a key from the map if it is present.
     *
     * @param key The key whose mapping is to be removed.
     * @return The previous value associated with the key, or `null` if there was no mapping.
     */
    fun remove(key: K): V? {
        val hash = hash(key)
        var current = buckets[hash]
        var prev: Node<K, V>? = null

        while (current != null) {
            if (current.key == key) {
                if (prev == null) {
                    buckets[hash] = current.next
                } else {
                    prev.next = current.next
                }
                size--
                return current.value
            }
            prev = current
            current = current.next
        }
        return null
    }

    /**
     * Returns a list of all keys in the map.
     *
     * @return A list of keys currently in the map.
     */
    fun keys(): List<K> {
        val keysList = mutableListOf<K>()
        for (bucket in buckets) {
            var current = bucket
            while (current != null) {
                keysList.add(current.key)
                current = current.next
            }
        }
        return keysList
    }

    /**
     * Returns a list of all values in the map.
     *
     * @return A list of values currently in the map.
     */
    fun values(): List<V> {
        val valuesList = mutableListOf<V>()
        for (bucket in buckets) {
            var current = bucket
            while (current != null) {
                valuesList.add(current.value)
                current = current.next
            }
        }
        return valuesList
    }
}
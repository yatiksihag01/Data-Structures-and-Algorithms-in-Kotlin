package tree

import java.util.*

/**
 * Class to handle binary tree view operations such as retrieving the top view.
 *
 * @param T the type of the elements in the binary tree. Must be [Comparable].
 */
class BinaryTreeView<T : Comparable<T>> {

    /**
     * Computes the top view of a binary tree and applies a provided action on each node's data in the top view.
     *
     * The "top view" of a binary tree is the set of nodes visible when the tree is viewed from above.
     * For nodes at the same horizontal distance, only the first encountered node (from top to bottom) is included.
     *
     * Time Complexity:
     * - O(n): Each node is visited exactly once during the level-order traversal, where `n` is the number of nodes in the tree.
     * - O(log n) to O(n): Each insertion into the `TreeMap` takes logarithmic time, and in the worst case, there are `n` entries.
     *
     * Space Complexity:
     * - O(n): The queue for level-order traversal can hold up to `n` nodes in the worst case (incomplete binary tree).
     * - O(k): The `TreeMap` can have up to `k` entries, where `k` is the maximum number of unique horizontal distances, and `k <= n`.
     *
     * @param root the root node of the binary tree.
     * @param view a function to be applied to each node's data in the top view, in left-to-right order.
     */
    fun topView(root: BinaryTreeNode<T>, view: (T) -> Unit) {
        val horizontalDistanceMap = TreeMap<Int, T>()
        val queue = LinkedList<Pair<BinaryTreeNode<T>, Int>>()
        queue.offer(Pair(root, 0))

        while (queue.isNotEmpty()) {
            val (node, distance) = queue.poll()
            horizontalDistanceMap.putIfAbsent(distance, node.data)
            node.left?.let { queue.offer(Pair(it, distance - 1)) }
            node.right?.let { queue.offer(Pair(it, distance + 1)) }
        }
        horizontalDistanceMap.values.forEach(view)
    }

    private fun rightViewRec(list: MutableList<T>, node: BinaryTreeNode<T>?, level: Int) {
        if (node == null) return
        if (level == list.size) list.add(node.data)
        rightViewRec(list, node.right, level + 1)
        rightViewRec(list, node.left, level + 1)
    }

    /**
     * Computes the right view of a binary tree and applies a provided action on each node's data in the right view.
     *
     * The "right view" of a binary tree is the set of nodes visible when the tree is viewed from the right side.
     * For each level, only the rightmost node is included in the view.
     *
     * Time Complexity:
     * - O(n): Each node is visited exactly once, where `n` is the number of nodes in the tree.
     *
     * Space Complexity:
     * - O(n): The list used to store the right view nodes may store up to `n` elements in the worst case.
     * - O(h): The recursion stack space depends on the height of the binary tree (`h`),
     * which contributes to additional memory usage.
     *   - In the best case (balanced tree), `h = log(n)`.
     *   - In the worst case (skewed tree), `h = n`.
     *
     * @param root the root node of the binary tree.
     * @param view a function to be applied to each node's data in the right view, in top-to-bottom order.
     */
    fun rightView(root: BinaryTreeNode<T>, view: (T) -> Unit) {
        val list = mutableListOf<T>()
        rightViewRec(list, root, 0)
        list.forEach(view)
    }

    private fun leftViewRec(list: MutableList<T>, node: BinaryTreeNode<T>?, level: Int) {
        if (node == null) return
        if (level == list.size) list.add(node.data)
        leftViewRec(list, node.left, level + 1)
        leftViewRec(list, node.right, level + 1)
    }

    /**
     * Computes the left view of a binary tree and applies a provided action on each node's data in the left view.
     *
     * The "left view" of a binary tree is the set of nodes visible when the tree is viewed from the left side.
     * For each level, only the leftmost node is included in the view.
     *
     * Time Complexity:
     * - O(n): Each node is visited exactly once, where `n` is the number of nodes in the tree.
     *
     * Space Complexity:
     * - O(n): The list used to store the right view nodes may store up to `n` elements in the worst case.
     * - O(h): The recursion stack space depends on the height of the binary tree (`h`),
     * which contributes to additional memory usage.
     *   - In the best case (balanced tree), `h = log(n)`.
     *   - In the worst case (skewed tree), `h = n`.
     *
     * @param root the root node of the binary tree.
     * @param view a function to be applied to each node's data in the left view, in top-to-bottom order.
     */
    fun leftView(root: BinaryTreeNode<T>, view: (T) -> Unit) {
        val list = mutableListOf<T>()
        leftViewRec(list, root, 0)
        list.forEach(view)
    }
}
package tree

import java.util.*

/**
 * Represents a node in a binary tree.
 *
 * @param T The type of [Comparable] data the node holds.
 *
 * Example usage:
 * ```
 * val root = BinaryTreeNode(10)
 * root.left = BinaryTreeNode(5)
 * root.right = BinaryTreeNode(15)
 * ```
 */
class BinaryTreeNode<T : Comparable<T>>(
    var data: T,
    var left: BinaryTreeNode<T>? = null,
    var right: BinaryTreeNode<T>? = null

) {
    override fun toString(): String {
        return "BinaryTreeNode(data=$data, left=$left, right=$right)"
    }

    /**
     * Creates a copy of this node and its entire subtree.
     *
     * @return This node along with all it's children
     */
    fun deepCopy(): BinaryTreeNode<T> {
        return BinaryTreeNode(
            data = this.data,
            left = this.left?.deepCopy(),
            right = this.right?.deepCopy()
        )
    }

    fun levelOrderTraversal(action: (T) -> Unit) {
        val queue = LinkedList<BinaryTreeNode<T>>()
        queue.offer(this)
        while (queue.isNotEmpty()) {
            val node = queue.poll()
            action(node.data)
            node.left?.let { queue.offer(node.left) }
            node.right?.let { queue.offer(node.right) }
        }
    }

    fun inOrderTraversal(action: (T) -> Unit) {
        left?.inOrderTraversal(action)
        action(data)
        right?.inOrderTraversal(action)
    }

    fun preOrderTraversal(action: (T) -> Unit) {
        action(data)
        left?.preOrderTraversal(action)
        right?.preOrderTraversal(action)
    }

    fun postOrderTraversal(action: (T) -> Unit) {
        left?.postOrderTraversal(action)
        right?.postOrderTraversal(action)
        action(data)
    }

    fun inOrderTraversalIterative(action: (T) -> Unit) {
        val stack = Stack<BinaryTreeNode<T>>()
        var node: BinaryTreeNode<T>? = this
        while (true) {
            if (node != null) {
                stack.push(node)
                node = node.left
            } else {
                if (stack.empty()) break
                node = stack.pop()
                action(node.data)
                node = node.right
            }
        }
    }
}

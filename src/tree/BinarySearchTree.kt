package tree

/**
 * Represents a Binary Search Tree (BST).
 *
 * A Binary Search Tree is a hierarchical data structure in which:
 * - Each node contains a value.
 * - The left subtree of a node contains values less than the node's value.
 * - The right subtree of a node contains values greater than the node's value.
 * - Duplicate values are not allowed.
 *
 * This class provides functionality to:
 * - Insert values while maintaining the BST property.
 * - Search for values within the tree.
 * - Delete values from the tree.
 * - Traverse the tree using various traversal techniques (in-order, pre-order, post-order, level-order).
 * - Create a deep copy of a subtree rooted at a specific value.
 *
 * Usage:
 * 1. Create an instance of the `BinarySearchTree` class.
 * 2. Use `insert(value)` to add elements to the tree.
 * 3. Perform operations like `search(value)`, `delete(value)`, or traversals.
 *
 * Example:
 * ```
 * val bst = BinarySearchTree<Int>()
 * bst.insert(10)
 * bst.insert(5)
 * bst.insert(15)
 *
 * bst.inOrderTraversal { println(it) }  // Prints: 5, 10, 15
 * val foundNode = bst.search(5)         // Returns the node containing 5, or null if not found
 * bst.delete(10)                        // Removes the node containing 10
 * ```
 *
 * Time Complexity:
 * - Insertion, Deletion, and Search:
 *   - Best case (balanced tree): O(log(n)), where n is the number of nodes.
 *   - Worst case (skewed tree): O(n), where n is the number of nodes.
 * - Traversals: O(n), as all nodes are visited.
 *
 * Space Complexity:
 * - O(h) for recursive methods, where h is the height of the tree.
 * - O(1) for iterative methods.
 *
 * @param T the type of values stored in the BST. Must implement `Comparable` to allow comparisons.
 */
class BinarySearchTree<T : Comparable<T>> {

    private var root: BinaryTreeNode<T>? = null

    /**
     * Inserts a value into the binary search tree (BST) recursively.
     *
     * @param node the current node being evaluated during the recursive traversal.
     * @param value the value to insert into the binary search tree.
     * @return the updated node after inserting the value.
     */
    private fun insertRec(node: BinaryTreeNode<T>?, value: T): BinaryTreeNode<T>? {
        if (node == null) {
            return BinaryTreeNode(value)
        }

        when {
            value < node.data -> node.left = insertRec(node.left, value)
            value > node.data -> node.right = insertRec(node.right, value)
        }
        return node
    }

    /**
     * Inserts a value into the binary search tree (BST).
     *
     * The insertion process respects the BST property:
     * - If the tree is empty, the value becomes the root node.
     * - Values smaller than the current node are placed in the left subtree.
     * - Values greater than the current node are placed in the right subtree.
     *
     * Time Complexity:
     * - O(h): The time complexity depends on the height of the binary search tree.
     * - Best case (balanced tree): h = log(n), where n is the number of nodes.
     * - Worst case (skewed tree): h = n, where n is the number of nodes.
     *
     * Space Complexity:
     * - O(h): Additional space is used by the recursive call stack when inserting a value.
     *
     * @param value the value to insert into the binary search tree.
     */
    fun insert(value: T) {
        root = insertRec(root, value)
    }

    /**
     * Performs an in-order traversal of the binary search tree.
     *
     * Traverses the nodes in ascending order of their values.
     * The provided action is applied to each node's data during the traversal.
     *
     * @param action a lambda to process each node's value.
     */
    fun inOrderTraversal(action: (T) -> Unit) {
        root?.inOrderTraversal(action)
    }

    /**
     * Performs a pre-order traversal of the binary search tree.
     *
     * Visits the root node first, followed by the left subtree and then the right subtree.
     * The provided action is applied to each node's data during the traversal.
     *
     * @param action a lambda to process each node's value.
     */
    fun preOrderTraversal(action: (T) -> Unit) {
        root?.preOrderTraversal(action)
    }

    /**
     * Performs a post-order traversal of the binary search tree.
     *
     * Visits the left subtree, followed by the right subtree, and then the root node.
     * The provided action is applied to each node's data during the traversal.
     *
     * @param action a lambda to process each node's value.
     */
    fun postOrderTraversal(action: (T) -> Unit) {
        root?.postOrderTraversal(action)
    }

    /**
     * Performs a level-order traversal (breadth-first traversal) of the binary search tree.
     *
     * Visits all nodes at each level from top to bottom, starting from the root.
     * The provided action is applied to each node's data during the traversal.
     *
     * @param action a lambda to process each node's value.
     */
    fun levelOrderTraversal(action: (T) -> Unit) {
        root?.levelOrderTraversal(action)
    }

    /**
     * Creates a deep copy of the subtree rooted at the node containing the specified value.
     *
     * The deep copy maintains the binary search tree structure.
     * The copied nodes are inserted into a new binary search tree.
     *
     * @param value the value of the node at the root of the subtree to copy.
     * @return a new binary search tree containing a deep copy of the subtree,
     *         or an empty tree if the specified value is not found.
     */
    fun deepCopy(value: T): BinarySearchTree<T> {
        val node = search(value)
        val copy = node?.deepCopy()
        val bst = BinarySearchTree<T>()
        copy?.levelOrderTraversal { data ->
            bst.insert(data)
        }
        return bst
    }

    /**
     * Searches for a given value in a binary search tree (BST).
     *
     * The search function utilizes this property to locate the value efficiently by traversing only a single path
     * from the root to a potential match.
     *
     * Time Complexity:
     * - O(h): The time complexity depends on the height of the binary search tree.
     *   - Best case (balanced tree): h = log(n), where n is the number of nodes.
     *   - Worst case (skewed tree): h = n, where n is the number of nodes.
     *
     * Space Complexity:
     * - O(1): No additional space is required beyond the recursion or iteration.
     * - O(h): If we use the recursive approach.
     *
     * @param value the value to search for in the binary search tree.
     * @return the node containing the value, or null if the value is not found.
     */
    fun search(value: T): BinaryTreeNode<T>? {
        var currentNode: BinaryTreeNode<T>? = root

        while (currentNode != null) {
            currentNode = when {
                value == currentNode.data -> return currentNode
                value < currentNode.data -> currentNode.left
                else -> currentNode.right
            }
        }
        return null
    }

    /**
     * Finds the node with the smallest value in a binary search tree.
     *
     * @param root the root node of the binary search tree.
     * @return the node with the smallest value in the tree.
     */
    private fun findMin(root: BinaryTreeNode<T>?): BinaryTreeNode<T> {
        var current = root
        while (current?.left != null) {
            current = current.left
        }
        return current ?: throw IllegalArgumentException("Tree must have at least one node.")
    }

    private fun deleteRec(root: BinaryTreeNode<T>?, value: T): BinaryTreeNode<T>? {
        if (root == null) return null

        when {
            value < root.data -> root.left = deleteRec(root.left, value)
            value > root.data -> root.right = deleteRec(root.right, value)
            else -> {
                if (root.left == null) return root.right // Case 1: No left child
                if (root.right == null) return root.left // Case 2: No right child

                // Case 3: Node with two children. Replace with the in-order successor
                val successor = findMin(root.right)
                root.data = successor.data
                root.right = deleteRec(root.right, successor.data)
            }
        }

        return root
    }

    /**
     * Deletes a given value from a binary search tree (BST).
     *
     * The deletion process involves three cases:
     * 1. Node to delete has no children (leaf node) – Remove the node directly.
     * 2. Node to delete has one child – Replace the node with its child.
     * 3. Node to delete has two children – Replace the node with its in-order successor (smallest value in the right subtree),
     *    and recursively delete the in-order successor.
     *
     * Time Complexity:
     * - O(h): The time complexity depends on the height of the binary search tree.
     *   - Best case (balanced tree): h = log(n), where n is the number of nodes.
     *   - Worst case (skewed tree): h = n, where n is the number of nodes.
     *
     * Space Complexity:
     * - O(h): The recursion stack space depends on the height of the binary search tree.
     *
     * @param root the root node of the binary search tree.
     * @param value the value to delete from the binary search tree.
     * @return the root node of the updated binary search tree.
     */
    fun delete(value: T): BinaryTreeNode<T>? {
        return deleteRec(this.root, value)
    }
}
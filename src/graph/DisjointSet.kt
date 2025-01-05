package graph

/**
 * Disjoint Set (Union-Find) Data Structure with Path Compression and Union by Rank.
 *
 * This implementation supports efficient union and find operations, commonly used in problems like
 * graph connectivity, Kruskal's MST, and connected components.
 *
 * Time Complexity:
 * - `findUltimateParent(node: Int)`: O(alpha(N)), where alpha is the inverse Ackermann function.
 *   Practically, it can be considered nearly constant for most inputs.
 * - `unionByRank(u: Int, v: Int)`: O(alpha(N)), due to the calls to `findUltimateParent`.
 * - `isSameComponent(u: Int, v: Int)`: O(alpha(N)), as it uses `findUltimateParent`.
 *
 * Space Complexity: O(N), where N is the size of the disjoint set.
 * - Space is used to store the `rank` and `parent` arrays.
 */
class DisjointSet(size: Int) {
    private val rank = IntArray(size) { 0 }
    private val parent = IntArray(size) { it }

    /**
     * Finds the ultimate parent (representative) of the given node with path compression.
     *
     * Path compression optimizes the structure by making nodes point directly to the root.
     *
     * @param node The node for which the ultimate parent is to be found.
     * @return The ultimate parent of the node.
     */
    fun findUltimateParent(node: Int): Int {
        if (node == parent[node]) return node
        // Path compression: Update the parent directly to the root
        return findUltimateParent(parent[node]).also {
            parent[node] = it
        }
    }

    /**
     * Performs union of two sets identified by nodes u and v using union by rank.
     *
     * Union by rank ensures that the tree with smaller rank is merged under the tree with larger rank.
     *
     * @param u First node.
     * @param v Second node.
     */
    fun unionByRank(u: Int, v: Int) {
        val ultimateParentOfU = findUltimateParent(u)
        val ultimateParentOfV = findUltimateParent(v)

        if (ultimateParentOfU == ultimateParentOfV) return

        if (rank[ultimateParentOfU] < rank[ultimateParentOfV]) {
            parent[ultimateParentOfU] = ultimateParentOfV
        } else if (rank[ultimateParentOfU] > rank[ultimateParentOfV]) {
            parent[ultimateParentOfV] = ultimateParentOfU
        } else {
            parent[ultimateParentOfV] = ultimateParentOfU
            rank[ultimateParentOfU] += 1
        }
    }

    /**
     * Checks if two nodes belong to the same connected component.
     *
     * @param u First node.
     * @param v Second node.
     * @return True if u and v are in the same component, false otherwise.
     */
    fun areInSameSet(u: Int, v: Int): Boolean {
        return findUltimateParent(u) == findUltimateParent(v)
    }

}

//Example Set
//val disjointSet = DisjointSet(8)
//disjointSet.unionByRank(1, 2)
//disjointSet.unionByRank(2, 3)
//disjointSet.unionByRank(4, 5)
//disjointSet.unionByRank(6, 7)
//disjointSet.unionByRank(5, 6)
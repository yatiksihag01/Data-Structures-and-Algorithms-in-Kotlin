package graph

/**
 * Implementation of the Floyd-Warshall algorithm for finding the shortest paths
 * between all pairs of nodes in a weighted graph. The graph is represented as an
 * adjacency matrix, where the value at `adjMatrix[i][j]` represents the weight
 * of the edge between node `i` and node `j`. If there is no edge, `Int.MAX_VALUE` is used.
 *
 * Time Complexity:
 * - The algorithm has three nested loops, each iterating over all nodes (of size `n`).
 * - The total time complexity is therefore O(n^3), where `n` is the number of nodes (or the size of the matrix).
 *
 * Space Complexity:
 * - The algorithm creates a new `cost` matrix of the same size as the input matrix (`n x n`).
 * - Therefore, the auxiliary space complexity is O(n^2).
 *
 * @param adjMatrix A 2D list of integers representing the adjacency matrix of the graph.
 *                  The value at `adjMatrix[i][j]` represents the weight of the edge from
 *                  node `i` to node `j`, and `Int.MAX_VALUE` represents the absence of an edge.
 * @return A 2D list of integers representing the shortest distances between all pairs of nodes.
 *         The value at `result[i][j]` represents the shortest path from node `i` to node `j`.
 *
 */
fun floydWarshall(adjMatrix: List<IntArray>): List<IntArray> {
    val size = adjMatrix.size
    val cost = MutableList(size) { i -> adjMatrix[i].clone() }

    for (k in 0 until size) {
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (cost[i][k] != Int.MAX_VALUE && cost[k][j] != Int.MAX_VALUE) {
                    cost[i][j] = cost[i][j].coerceAtMost(cost[i][k] + cost[k][j])
                }
            }
        }
    }
    return cost
}

// Example graph
//val graph = listOf(
//    intArrayOf(0, 3, Int.MAX_VALUE, 7),
//    intArrayOf(8, 0, 2, Int.MAX_VALUE),
//    intArrayOf(5, Int.MAX_VALUE, 0, 1),
//    intArrayOf(2, Int.MAX_VALUE, Int.MAX_VALUE, 0)
//)
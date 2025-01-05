package graph

/**
 * Implements the Bellman-Ford algorithm to find the shortest path from a source node (0)
 * to all other nodes in a weighted graph. Detects negative weight cycles.
 *
 * - Time Complexity: `O(V * E)`, where V is the number of vertices and E is the number of edges.
 *
 * - Space Complexity: `O(V)`, where V is the number of vertices (for the distances array).
 *
 * @param edges A list of edges where each edge is represented as a list of three integers:
 *              [source, destination, weight].
 * @param totalNodes The total number of nodes in the graph.
 * @return An array of shortest distances from the source node to each node.
 * @throws IllegalArgumentException if a negative weight cycle is detected.
 */
fun bellmanFord(edges: List<List<Int>>, totalNodes: Int): IntArray {
    val distances = IntArray(totalNodes) { Int.MAX_VALUE }
    distances[0] = 0

    for (i in 1 until totalNodes) {
        for (edge in edges) {
            val (source, destination, weight) = edge
            if (distances[source] == Int.MAX_VALUE) continue
            val newWeight = distances[source] + weight

            if (newWeight < distances[destination]) {
                distances[destination] = newWeight
            }
        }
    }

    for (edge in edges) {
        val (source, destination, weight) = edge
        if (distances[source] != Int.MAX_VALUE &&
            distances[source] + weight < distances[destination]
        ) {
            throw IllegalArgumentException("The graph contains a negative weight cycle.")
        }
    }

    return distances
}

// An example graph with negative weight cycle
//val edges: List<List<Int>> = listOf(
//    listOf(3,2,6),
//    listOf(5,3,1),
//    listOf(0,1,5),
//    listOf(1,5,-3),
//    listOf(2,1,-5),
//    listOf(3,4,-2),
//    listOf(2,4,3),
//)

// An example DAG
//val edges: List<List<Int>> = listOf(
//    listOf(3,2,6),
//    listOf(5,3,1),
//    listOf(0,1,5),
//    listOf(1,5,-3),
//    listOf(1,2,-2),
//    listOf(3,4,-2),
//    listOf(2,4,3),
//)
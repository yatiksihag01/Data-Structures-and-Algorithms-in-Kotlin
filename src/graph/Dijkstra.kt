package graph

import java.util.PriorityQueue

/**
 * Data class representing an edge in the graph.
 * @param destination The destination vertex.
 * @param weight The weight of the edge.
 */
data class Edge(val destination: Int, val weight: Int)

/**
 * Private data class representing a node in the priority queue.
 * Implements Comparable to prioritize nodes by their distance.
 * @param vertex The vertex index.
 * @param distance The distance from the source to this vertex.
 */
private data class Node(val vertex: Int, val distance: Int) : Comparable<Node> {
    override fun compareTo(other: Node): Int {
        return distance.compareTo(other.distance)
    }
}

/**
 * Implements Dijkstra's algorithm to find the shortest distances from a start node to all other nodes.
 *
 * Time Complexity:
 * - Initialization: O(V), where V is the number of vertices.
 * - Processing each node: O((V + E) log V), where E is the number of edges.
 *   - Extracting min from the priority queue: O(log V)
 *   - Processing neighbors for each edge: O(E log V)
 * - Overall: O((V + E) log V)
 *
 * Space Complexity:
 * - Distances array: O(V)
 * - Priority queue: O(V)
 * - Adjacency list (input): O(V + E)
 * - Overall: O(V + E)
 *
 * @param adjList The adjacency list representation of the graph.
 *                Each vertex has a list of edges, where each edge specifies a destination and weight.
 * @param startNode The source vertex from which distances are calculated.
 * @return An array of shortest distances from the start node to each vertex.
 *         If a vertex is unreachable, its distance will be `Int.MAX_VALUE`.
 *
 */
fun dijkstra(
    adjList: List<List<Edge>>,
    startNode: Int,
): IntArray {
    val size = adjList.size
    val distances = IntArray(size) { Int.MAX_VALUE }
    val priorityQueue = PriorityQueue<Node>()

    priorityQueue.add(Node(startNode, 0))
    distances[startNode] = 0
    while (priorityQueue.isNotEmpty()) {
        val (currVertex, currDistance) = priorityQueue.poll()

        // Skip nodes that have already been visited with a shorter distance
        if (currDistance > distances[currVertex]) continue

        for (edge in adjList[currVertex]) {
            val neighbour = edge.destination
            val newDistance = currDistance + edge.weight
            if (newDistance < distances[neighbour]) {
                distances[neighbour] = newDistance
                priorityQueue.add(Node(neighbour, newDistance))
            }
        }

    }
    return distances
}

// Example graph as an adjacency list
//val graph = listOf(
//    listOf(Edge(destination =  1, weight = 4), Edge(2, 4)),
//    listOf(Edge(0, 4), Edge(2, 2)),
//    listOf(Edge(3, 3), Edge(4, 1), Edge(5, 6)),
//    listOf(Edge(2, 3), Edge(5, 2)),
//    listOf(Edge(2, 1), Edge(5, 3)),
//    listOf(Edge(3, 2), Edge(4, 3)),
//    emptyList<Edge>()
//)
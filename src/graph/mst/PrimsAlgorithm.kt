package graph.mst

import java.util.*

/**
 * Computes the Minimum Spanning Tree (MST) of a graph using Prim's algorithm.
 *
 * @param adjList Adjacency list representation of the graph. Each node maps to a list of its neighbors,
 *              where each neighbor is represented as a Pair of (node, weight).
 * @param startNode The starting node for the MST computation.
 *
 * @return A pair containing:
 *         1. A list of edges in the MST, where each edge is represented as a Pair of (startNode, endNode).
 *         2. The total weight (sum of all edge weights) of the MST.
 *
 * Time Complexity:
 * - O((V + E) * log(V)), where V is the number of vertices and E is the number of edges.
 * - Adding elements to the priority queue takes O(log(V)) and is done for all edges.
 *
 * Space Complexity:
 * - O(V + E), where V is for the visited array and E for the priority queue.
 */
fun primsMST(
    adjList: List<List<Pair<Int, Int>>>,
    startNode: Int
): Pair<List<Pair<Int, Int>>, Int> {

    var totalWeight = 0
    val visited = BooleanArray(adjList.size)
    val mst = mutableListOf<Pair<Int, Int>>()
    val priorityQueue = PriorityQueue<EdgeSet>()

    priorityQueue.add(EdgeSet(-1, startNode, 0))

    while (priorityQueue.isNotEmpty()) {
        val (parent, node, weight) = priorityQueue.poll()

        if (visited[node]) continue

        visited[node] = true
        if (parent != -1) mst.add(parent to node)
        totalWeight += weight

        for ((neighbor, edgeWeight) in adjList[node]) {
            if (!visited[neighbor]) {
                priorityQueue.add(EdgeSet(node, neighbor, edgeWeight))
            }
        }
    }

    return mst to totalWeight
}

// Example graph
//val graph = listOf(
//    listOf(Pair(1, 2), Pair(2, 1)),
//    listOf(Pair(0, 2), Pair(2, 1)),
//    listOf(Pair(0, 1), Pair(1, 1), Pair(4, 2), Pair(3, 2)),
//    listOf(Pair(2, 2), Pair(4, 1)),
//    listOf(Pair(2, 2), Pair(3, 1))
//)
package graph.mst

import graph.DisjointSet

/**
 * Kruskal's Algorithm for finding the Minimum Spanning Tree (MST) of a graph.
 *
 * This implementation uses a disjoint set data structure to manage the merging of components.
 *
 * Time Complexity:
 * - Transforming the adjacency list to [EdgeSet] takes O(V + E) time, where V is the number of vertices and E is the number of edges.
 * - Sorting the edges takes O(E * log E) time.
 * - Union by rank for all edges requires O(E) time due to nearly constant time union-find operations.
 *
 * Space Complexity:
 * - Sorted edge sets take O(E) space.
 * - The space required for the DisjointSet is O(V) because it maintains arrays of size V for parent and rank.
 * - The space complexity to store MST edges is O(V) since there will be V - 1 edges in the MST.
 * - Therefore, the overall space complexity is O(V + E).
 */
class KruskalAlgorithm {

    /**
     * Finds the Minimum Spanning Tree (MST) of a graph represented as an adjacency list.
     *
     * @param adjList Adjacency list where each vertex points to a list of pairs (neighbor, weight).
     * @return A list of edges in the MST, represented as pairs (vertex1, vertex2).
     */
    fun findMST(adjList: List<List<Pair<Int, Int>>>): List<Pair<Int, Int>> {
        val edgeSets = adjList.toSortedEdgeSets()

        val mstEdges = mutableListOf<Pair<Int, Int>>()
        val disjointSet = DisjointSet(adjList.size)

        for (edge in edgeSets) {
            if (!disjointSet.areInSameSet(edge.parent, edge.node)) {
                disjointSet.unionByRank(edge.parent, edge.node)
                mstEdges.add(Pair(edge.parent, edge.node))
            }
        }
        return mstEdges
    }

    /**
     * Converts the adjacency list to a list of edges sorted by weight.
     *
     * @return A list of EdgeSet objects representing the edges of the graph, sorted by weight.
     */
    private fun List<List<Pair<Int, Int>>>.toSortedEdgeSets(): List<EdgeSet> {
        val edges = mutableListOf<EdgeSet>()

        for (i in indices) {
            for (neighbour in this[i]) {
                edges.add(
                    EdgeSet(
                        parent = i,
                        node = neighbour.first,
                        weight = neighbour.second,
                    )
                )
            }

        }
        // Sort edges by weight
        return edges.also { it.sort() }
    }
}

//Example graph:
//val graph = listOf(
//    listOf(Pair(1, 2), Pair(2, 1)),
//    listOf(Pair(0, 2), Pair(2, 1)),
//    listOf(Pair(0, 1), Pair(1, 1), Pair(4, 2), Pair(3, 2)),
//    listOf(Pair(2, 2), Pair(4, 1)),
//    listOf(Pair(2, 2), Pair(3, 1))
//)
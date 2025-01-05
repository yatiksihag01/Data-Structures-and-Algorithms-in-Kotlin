package graph.mst

/**
 * Represents an edge in the graph for Prim's and Kruskal's algorithm.
 *
 * @property parent The parent node from which the edge originates.
 * @property node The destination node of the edge.
 * @property weight The weight of the edge.
 */
internal data class EdgeSet(
    val parent: Int,
    val node: Int,
    val weight: Int
) : Comparable<EdgeSet> {
    override fun compareTo(other: EdgeSet): Int = this.weight.compareTo(other.weight)
}
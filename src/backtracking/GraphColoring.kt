package backtracking

/**
 * You are given an undirected graph consisting of v vertices and a list of edges, along with an integer m.
 * Your task is to determine whether it is possible to color the graph using at most `m` different colors
 * such that no two adjacent vertices share the same color.
 *
 * Practice here: [M-Coloring Problem](https://www.geeksforgeeks.org/problems/m-coloring-problem-1587115620/1)
 */
class GraphColoring {

    /**
     * Checks if it is safe to color the given node with the specified color.
     *
     * @param node The current node to be colored.
     * @param graph The matrix representation of the graph.
     * @param color The color to be assigned to the current node.
     * @param assignedColors An array representing the colors assigned to each node.
     * @return True if it is safe to assign the color, false otherwise.
     */
    private fun isSafe(node: Int, graph: Array<IntArray>, color: Int, assignedColors: IntArray): Boolean {
        for (i in graph[node])
            if (assignedColors[i] == color) return false
        return true
    }

    /**
     * Attempts to color the graph using backtracking.
     *
     * @param currentNode The current node being processed.
     * @param graph The matrix representation of the graph.
     * @param n The total number of nodes in the graph.
     * @param maxColors The maximum number of colors allowed.
     * @param assignedColors An array representing the colors assigned to each node.
     * @return True if the graph can be colored using at most `maxColors`, false otherwise.
     */
    private fun backtrack(
        currentNode: Int,
        graph: Array<IntArray>,
        n: Int,
        maxColors: Int,
        assignedColors: IntArray
    ): Boolean {
        if (currentNode == n) return true

        for (i in 1..maxColors) {
            if (isSafe(currentNode, graph, i, assignedColors)) {
                assignedColors[currentNode] = i
                if (backtrack(currentNode + 1, graph, n, maxColors, assignedColors)) return true
                assignedColors[currentNode] = 0
            }
        }
        return false
    }

    /**
     * Determines if the graph can be colored using at most `m` colors.
     *
     * - Time Complexity: O(m^n), where `n` is the number of nodes and `m` is the maximum number of colors.
     *      - The backtracking function explores all possible color combinations in the worst case.
     * - Space Complexity: O(n), due to the recursion stack and the `assignedColors` array of size `n`.
     *
     * @param graph The matrix representation of the graph.
     * @param m The maximum number of colors allowed.
     * @return True if the graph can be colored using at most `m` colors, false otherwise.
     */
    fun canBeColored(graph: Array<IntArray>, m: Int): Boolean {
        val colors = IntArray(graph.size) { 0 }
        return backtrack(0, graph, graph.size, m, colors)
    }

}
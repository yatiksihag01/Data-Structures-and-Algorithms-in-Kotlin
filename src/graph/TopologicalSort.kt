package graph

import java.util.*

class TopologicalSort {

    /**
     * Performs topological sorting of a Directed Acyclic Graph (DAG) using Depth-First Search (DFS).
     *
     * - Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges.
     * - Space Complexity: O(V), due to the recursion stack and the `visited` array.
     *
     * Note: This function assumes the input graph is a DAG. If the graph contains cycles, the result
     *       will be undefined, as topological sorting is not possible for graphs with cycles.
     *
     * @param adjList The adjacency list representing the directed graph.
     * @param action A lambda function to perform an action on each vertex in topological order.
     *
     */
    fun dfsTopologicalSort(adjList: List<List<Int>>, action: (Int) -> Unit) {
        val size = adjList.size
        val visited = BooleanArray(size) { false }
        val stack = Stack<Int>()

        fun dfs(node: Int) {
            visited[node] = true
            for (neighbor in adjList[node]) {
                if (!visited[neighbor]) {
                    dfs(neighbor)
                }
            }
            stack.push(node)
        }

        for (i in 0 until size) {
            if (!visited[i]) {
                dfs(i)
            }
        }

        while (stack.isNotEmpty()) {
            action(stack.pop())
        }
    }

    /**
     * Performs topological sorting of a Directed Acyclic Graph (DAG) using Kahn's Algorithm.
     *
     * - Time Complexity: O(V + E), where V is the number of vertices and E is the number of edges.
     * - Space Complexity: O(V + E), due to the adjacency list and additional data structures (in-degree array and queue).
     *
     * @param adjList The adjacency list representing the directed graph.
     * @return An array representing the topological order of the vertices.
     * @throws IllegalArgumentException If the graph contains a cycle.
     *
     */
    fun kahnAlgoTopologicalSort(adjList: List<List<Int>>): IntArray {
        val size = adjList.size
        val inDegree = IntArray(size) { 0 }

        for (neighbors in adjList) {
            for (neighbor in neighbors) {
                inDegree[neighbor]++
            }
        }

        val queue: Queue<Int> = LinkedList()
        for (i in 0 until size) {
            if (inDegree[i] == 0) {
                queue.offer(i)
            }
        }

        val result = IntArray(size)
        var index = 0
        while (queue.isNotEmpty()) {
            val node = queue.poll()
            result[index++] = node
            for (neighbor in adjList[node]) {
                inDegree[neighbor]--
                if (inDegree[neighbor] == 0) {
                    queue.offer(neighbor)
                }
            }
        }

        if (index != size) {
            throw IllegalArgumentException("The graph contains a cycle.")
        }

        return result
    }
}

//Example graph
//val graph = listOf(
//    listOf(1, 2),    // Vertex 0 points to 1 and 2
//    listOf(3),       // Vertex 1 points to 3
//    listOf(3),       // Vertex 2 points to 3
//    listOf()         // Vertex 3 points to no one
//)
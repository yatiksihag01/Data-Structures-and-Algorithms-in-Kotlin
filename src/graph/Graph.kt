package graph

import java.util.LinkedList
import java.util.Queue
import kotlin.collections.indices

class Graph {

    /**
     * Performs Breadth-First Search (BFS) on a graph represented by an adjacency matrix.
     * This implementation handles disconnected graphs by ensuring all nodes are visited.
     *
     * @param adjacencyMatrix A 2D list where `adjacencyMatrix[i][j] = 1` indicates an edge from node i to node j.
     * @param totalNodes The total number of nodes in the graph.
     * @param startNode The node from which the BFS traversal starts.
     * @param action A lambda function to be executed for each visited node.
     *
     * ### Behavior:
     * - For connected components, it performs BFS starting from `startNode`.
     * - For disconnected graphs, it ensures all unvisited nodes are covered by initiating additional BFS traversals.
     *
     * ### Example Usage:
     * ```
     * val graph = Graph()
     * val matrix = listOf(
     *     listOf(0, 1, 0),
     *     listOf(1, 0, 1),
     *     listOf(0, 1, 0)
     * )
     * graph.breadthFirstSearch(matrix, 3, 0) { println(it) }
     * ```
     *
     * ### Complexity:
     * - **Time Complexity**: O(V^2) for a graph represented by an adjacency matrix, where V is the number of nodes.
     * - **Space Complexity**: O(V) for the `visited` array and O(V) for the queue (in the worst case).
     */
    fun breadthFirstSearch(
        adjacencyMatrix: List<List<Int>>,
        totalNodes: Int,
        startNode: Int,
        action: (Int) -> Unit
    ) {
        val visited = BooleanArray(totalNodes)
        fun bfs(startNode: Int) {
            val queue = LinkedList<Int>().apply { offer(startNode) }
            visited[startNode] = true
            action(startNode)

            while (queue.isNotEmpty()) {
                val node = queue.poll()
                for (neighbour in adjacencyMatrix[node].indices) {
                    if (adjacencyMatrix[node][neighbour] == 1 && !visited[neighbour]) {
                        visited[neighbour] = true
                        queue.offer(neighbour)
                        action(neighbour)
                    }
                }
            }
        }
        bfs(startNode)
        for (node in 0 until totalNodes) {
            if (!visited[node]) {
                bfs(node)
            }
        }
    }

    /**
     * Performs Depth-First Search (DFS) on a graph represented by an adjacency matrix.
     * This implementation handles disconnected graphs by ensuring all nodes are visited.
     *
     * @param adjacencyMatrix A 2D list where `adjacencyMatrix[i][j] = 1` indicates an edge between nodes i and j.
     * @param totalNodes The total number of nodes in the graph.
     * @param startNode The node from which the DFS traversal starts.
     * @param action A lambda function to be executed for each visited node.
     *
     * ### Behavior:
     * - For connected components, it performs DFS starting from `startNode`.
     * - For disconnected graphs, it ensures all unvisited nodes are covered by initiating additional DFS traversals.
     *
     * ### Example Usage:
     * ```
     * val graph = Graph()
     * val matrix = listOf(
     *     listOf(0, 1, 0, 0),
     *     listOf(1, 0, 1, 0),
     *     listOf(0, 1, 0, 1),
     *     listOf(0, 0, 1, 0)
     * )
     * graph.depthFirstSearch(matrix, 4, 0) { println(it) }
     * ```
     *
     * ### Complexity:
     * - **Time Complexity**: O(V^2) for a graph represented by an adjacency matrix, where V is the number of nodes.
     * - **Space Complexity**: O(V) for the `visited` array and O(V) for the recursive stack (in the worst case).
     */
    fun depthFirstSearch(
        adjacencyMatrix: List<List<Int>>,
        totalNodes: Int,
        startNode: Int,
        action: (Int) -> Unit
    ) {
        val visited = BooleanArray(totalNodes)

        fun dfsRec(node: Int) {
            visited[node] = true
            action(node)
            for (neighbour in adjacencyMatrix[node].indices) {
                if (adjacencyMatrix[node][neighbour] == 1 && !visited[neighbour]) {
                    dfsRec(neighbour)
                }
            }
        }

        dfsRec(startNode)

        for (node in 0 until totalNodes) {
            if (!visited[node]) {
                dfsRec(node)
            }
        }
    }

    /**
     * Determines if there is a cycle in an undirected graph using BFS.
     *
     * This function checks for cycles in an undirected graph represented as an adjacency list.
     * It uses Breadth-First Search (BFS) and tracks visited nodes along with parent information
     * to identify cycles.
     *
     * - Time Complexity:
     *      - O(V + E), where V is the number of vertices (nodes) and E is the number of edges.
     *   This is because each node and edge is processed at most once during the traversal.
     *
     * - Space Complexity:
     *      - O(V), for the visited array and the queue used for BFS.
     *
     * @param adjList The adjacency list representing the graph. Each index corresponds to a node,
     * and the list at that index contains the nodes adjacent to it.
     * @param startNode The node to start the BFS traversal from.
     * @return `true` if a cycle is detected, `false` otherwise.
     *
     */
    fun isCyclicBFS(adjList: List<List<Int>>, startNode: Int): Boolean {
        val size = adjList.size
        val visited = BooleanArray(size)

        // Queue stores pairs of (node, parent)
        val queue: Queue<Pair<Int, Int>> = LinkedList()
        queue.offer(Pair(startNode, -1))
        visited[startNode] = true

        while (queue.isNotEmpty()) {
            val (node, parent) = queue.poll()

            for (neighbour in adjList[node]) {
                if (!visited[neighbour]) {
                    visited[neighbour] = true
                    queue.offer(Pair(neighbour, node))
                } else if (neighbour != parent) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * Determines if there is a cycle in an undirected graph using DFS.
     *
     * This function checks for cycles in an undirected graph represented as an adjacency list.
     * It uses Depth-First Search (DFS) recursively and tracks visited nodes along with parent
     * information to identify cycles.
     *
     * - Time Complexity:
     *      - O(V + E), where V is the number of vertices (nodes) and E is the number of edges.
     *   Each node is visited once, and each edge is explored once.
     *
     * - Space Complexity:
     *      - O(V), due to the recursion stack and the visited array.
     *
     * @param adjList The adjacency list representing the graph. Each index corresponds to a node,
     * and the list at that index contains the nodes adjacent to it.
     * @param startNode The node to start the DFS traversal from.
     * @return `true` if a cycle is detected, `false` otherwise.
     *
     */
    fun isCyclicDFS(adjList: List<List<Int>>, startNode: Int): Boolean {
        val size = adjList.size
        val visited = BooleanArray(size)

        fun dfs(node: Int, parent: Int): Boolean {
            visited[node] = true

            for (neighbour in adjList[node]) {
                if (!visited[neighbour]) {
                    if (dfs(neighbour, node)) return true
                } else if (neighbour != parent) {
                    return true
                }
            }
            return false
        }
        return dfs(startNode, -1)
    }

}

fun main() {
    val graph = Graph()
    val adj = listOf(
        listOf(0,1,1,0,0,0,0,0),
        listOf(1,0,0,1,0,1,0,0),
        listOf(1,0,0,0,1,0,0,0),
        listOf(0,1,0,0,1,1,0,0),
        listOf(0,0,1,1,0,0,0,0),
        listOf(0,1,0,1,0,0,0,0),
        listOf(0,0,0,0,0,0,0,1),
        listOf(0,0,0,0,0,0,1,0),
    )

    graph.breadthFirstSearch(adj, 8, 0) {
        print("$it ")
    }
    println()
    graph.depthFirstSearch(adj, 8, 0) { print("$it ") }
    println()


    val adjList = listOf(
        listOf(1,2),
        listOf(4),
        listOf(0,3,5),
        listOf(2),
        listOf(1,6),
        listOf(2,6),
        listOf(4,5),
    )

    println("The given graph is ${if (graph.isCyclicDFS(adjList, 0)) "" else "not "}cyclic.")
}

// Cyclic Undirected Graph in adjacency list format
//val adjList = listOf(
//    listOf(1,2),
//    listOf(4),
//    listOf(0,3,5),
//    listOf(2),
//    listOf(1,6),
//    listOf(2,6),
//    listOf(4,5),
//)

// Disconnected Graph in adjacency matrix format
//val adj = listOf(
//    listOf(0,1,1,0,0,0,0,0),
//    listOf(1,0,0,1,0,1,0,0),
//    listOf(1,0,0,0,1,0,0,0),
//    listOf(0,1,0,0,1,1,0,0),
//    listOf(0,0,1,1,0,0,0,0),
//    listOf(0,1,0,1,0,0,0,0),
//    listOf(0,0,0,0,0,0,0,1),
//    listOf(0,0,0,0,0,0,1,0),
//)
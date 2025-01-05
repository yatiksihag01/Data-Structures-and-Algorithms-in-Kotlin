package backtracking

/**
 * The problem involves finding all possible paths for a rat to move from the top-left
 * to the bottom-right of a square maze while only moving through cells marked as open (1).
 *
 * Paths are represented as strings consisting of characters 'D' (down), 'L' (left),
 * 'R' (right), and 'U' (up). The order of directions explored is fixed as "DLRU".
 *
 * Video Solution: [Rat In A Maze by TUF](https://youtu.be/bLGZhJlt4y0?si=yzyI7E3-phjfT8QG)
 *
 * Similar Problem on LC: [Unique Paths III](https://leetcode.com/problems/unique-paths-iii/description/)
 */
class RatInMaze {

    companion object {
        private const val DIRECTION_ORDER = "DLRU"
        private val DIRECTIONS = listOf(
            Pair(1, 0),  // Down
            Pair(0, -1), // Left
            Pair(0, 1),  // Right
            Pair(-1, 0)  // Up
        )
    }

    /**
     * Finds all valid paths through the maze.
     *
     * Time Complexity:
     * - In the worst case, the function explores all possible paths through the maze.
     * - For an `n x n` maze, there can be up to `4^(n^2)` recursive calls in the worst case,
     *   where every cell has four possible directions to explore.
     * - However, the actual complexity is much lower due to constraints:
     *      - The rat can only move into cells marked as open (1).
     *      - Visited cells are skipped.
     * - Practical complexity depends on the structure of the maze but is bounded by O(4^(n^2)) in theory.
     *
     * Space Complexity:
     * - The primary space usage comes from:
     *   1. The `visited` matrix: O(n^2) space.
     *   2. The recursion stack: In the worst case, it can go as deep as `n^2` (all cells in the maze).
     * - Total space complexity: O(n^2).
     *
     * @param matrix A 2D array representing the maze, where 1 indicates open cells
     *               and 0 indicates blocked cells.
     * @param n The size of the maze (n x n).
     * @return A list of strings, where each string represents a valid path.
     */
    fun findPath(matrix: Array<IntArray>, n: Int): List<String> {
        if (matrix[0][0] == 0 || matrix[n - 1][n - 1] == 0) return emptyList()

        val visited = MutableList(n) { MutableList(n) { false } }
        val result = mutableListOf<String>()
        backtrack(0, 0, n, matrix, visited, "", result)

        return result
    }

    /**
     * Recursively explores all paths through the maze using backtracking.
     *
     * @param row The current row of the rat.
     * @param col The current column of the rat.
     * @param n The size of the maze.
     * @param matrix The maze grid.
     * @param visited A 2D list to track visited cells.
     * @param path The path taken so far as a string of directions.
     * @param result A list to store all valid paths.
     */
    private fun backtrack(
        row: Int,
        col: Int,
        n: Int,
        matrix: Array<IntArray>,
        visited: MutableList<MutableList<Boolean>>,
        path: String,
        result: MutableList<String>
    ) {
        if (row == n - 1 && col == n - 1) {
            result.add(path)
            return
        }
        visited[row][col] = true
        for ((i, direction) in DIRECTIONS.withIndex()) {
            val newRow = row + direction.first
            val newCol = col + direction.second

            if (isValidMove(newRow, newCol, n, matrix, visited)) {
                backtrack(newRow, newCol, n, matrix, visited, path + DIRECTION_ORDER[i], result)
            }
        }
        visited[row][col] = false
    }

    /**
     * Checks if a move to the given cell is valid.
     *
     * @param row The target row.
     * @param col The target column.
     * @param n The size of the maze.
     * @param matrix The maze grid.
     * @param visited A 2D list of visited cells.
     * @return True if the cell is within bounds, unvisited, and open; false otherwise.
     */
    private fun isValidMove(
        row: Int,
        col: Int,
        n: Int,
        matrix: Array<IntArray>,
        visited: MutableList<MutableList<Boolean>>
    ): Boolean {
        return row in 0 until n &&
                col in 0 until n &&
                matrix[row][col] == 1 &&
                !visited[row][col]
    }

}

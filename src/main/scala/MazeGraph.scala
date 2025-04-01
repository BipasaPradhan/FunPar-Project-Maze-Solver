import scala.collection.mutable

//convert given maze into adjacency list for easier lookup
object MazeGraph {
  // represents a cell of the maze
  case class Node(row: Int, col: Int)

  // directions for movement: up, down, left, right
  val directions = List((-1, 0), (1, 0), (0, -1), (0, 1))

  // cost of each terrain type
  val terrainCosts: Map[Int, Int] = Map(
    1 -> 1, // normal ground
    2 -> 3, // mud
    3 -> 5, // water
    0 -> Int.MaxValue // wall
  )

  // returns each node mapped to neighboring nodes it can move to
  def mazeToGraph(
      maze: Array[Array[Int]]
  ): mutable.Map[Node, List[(Node, Int)]] = {

    // map of walkable nodes with its walkable neighbors and their weights
    val graph = mutable.Map[Node, List[(Node, Int)]]().withDefaultValue(List())

    // loop through each row and column, and only process walkable cells
    for (row <- maze.indices; col <- maze(0).indices if maze(row)(col) != 0) {
      val node = Node(row, col)
      // find neighboring nodes
      val neighbors = directions.flatMap { case (direction_r, direction_c) =>
        val newRow = row + direction_r
        val newCol = col + direction_c

        if (
          newRow >= 0 && newRow < maze.length && newCol >= 0 && newCol < maze(
            0
          ).length && maze(newRow)(newCol) != 0
        ) {
          val neighborCost = terrainCosts(maze(newRow)(newCol))
          Some(Node(newRow, newCol) -> neighborCost)
        } else {
          None
        }
      }
      graph(node) = neighbors
    }
    graph
  }
}

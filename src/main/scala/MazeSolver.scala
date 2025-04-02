import scala.collection.mutable
import MazeGraph._

object MazeSolver {
  // BFS (Sequential Version)
  def bfs(
      graph: mutable.Map[Node, List[(Node, Int)]],
      start: Node,
      end: Node
  ): (Int, List[Node]) = {
    // stores shortest distance to each viable node
    val distances = mutable.Map[Node, Int]().withDefaultValue(-1)
    distances(start) = 0

    // track the previous node (node before reaching current node)
    val previous = mutable.Map[Node, Node]()

    val queue = mutable.Queue[Node]() // queue for BFS
    queue.enqueue(start) // start with the start node

    while (queue.nonEmpty) {
      val currNode = queue.dequeue()

      // reached the end -> reconstruct from end to beginning & prepend
      if (currNode == end) {
        var path = List[Node]()
        var node = end

        while (previous.contains(node)) {
          path = node :: path
          node = previous(node)
        }
        path = start :: path // prepend start node to path
        return (distances(end), path) // return distance and path
      }

      // update neighbors
      for ((neighbor, _) <- graph.getOrElse(currNode, List())) {

        if (distances(neighbor) == -1) { // not visited
          distances(neighbor) = distances(currNode) + 1
          previous(neighbor) = currNode
          queue.enqueue(neighbor)
        }
      }
    }

    // no path found
    (-1, List())
  }

  // sequential A* algorithm
  def aStar(
      graph: mutable.Map[Node, List[(Node, Int)]],
      start: Node,
      end: Node
  ): (Int, List[Node]) = {

    // Manhattan distance heuristic
    def heuristic(node: Node, goal: Node): Int = {
      Math.abs(node.row - goal.row) + Math.abs(node.col - goal.col)
    }
  }

}

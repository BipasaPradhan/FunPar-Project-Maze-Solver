import org.scalatest.funsuite.AnyFunSuite
import MazeGraph._

class MazeGraphTest extends AnyFunSuite {

  test("mazeToGraph should correctly convert a simple maze to an adjacency list") {
    val maze = Array(
      Array(1, 1, 0),
      Array(1, 0, 1),
      Array(0, 1, 1)
    )

    val expectedGraph = Map(
        Node(0, 0) -> List(Node(1, 0) -> 1, Node(0, 1) -> 1), // Node(0, 0) has neighbors Node(1, 0) and Node(0, 1)
        Node(0, 1) -> List(Node(0, 0) -> 1), // Node(0, 1) has neighbor Node(0, 0)
        Node(1, 0) -> List(Node(0, 0) -> 1), // Node(1, 0) has neighbor Node(0, 0)
        Node(1, 2) -> List(Node(2, 2) -> 1), // Node(1, 2) has neighbor Node(2, 2)
        Node(2, 1) -> List(Node(2, 2) -> 1), // Node(2, 1) has neighbor Node(2, 2)
        Node(2, 2) -> List(Node(1, 2) -> 1, Node(2, 1) -> 1) // Node(2, 2) has neighbors Node(1, 2) and Node(2, 1)
    )

    val graph = mazeToGraph(maze)

    assert(graph == expectedGraph)
  }

  test("mazeToGraph should correctly handle a maze with no valid paths") {
    val maze = Array(
      Array(1, 0, 0),
      Array(0, 0, 0),
      Array(0, 0, 1)
    )

    val expectedGraph = Map(
      Node(0, 0) -> List(), // Node(0, 0) has no valid neighbors
      Node(2, 2) -> List() // Node(2, 2) has no valid neighbors
    )

    val graph = mazeToGraph(maze)

    assert(graph == expectedGraph)
  }

  test("mazeToGraph should correctly handle a maze with only walls") {
    val maze = Array(
      Array(0, 0, 0),
      Array(0, 0, 0),
      Array(0, 0, 0)
    )

    val expectedGraph = Map.empty[Node, List[(Node, Int)]] // no walkable paths

    val graph = mazeToGraph(maze)

    assert(graph == expectedGraph)
  }

  test("mazeToGraph should correctly convert a larger maze with multiple walkable paths to an adjacency list") {
  val maze = Array(
    Array(1, 1, 0, 1, 0),
    Array(1, 0, 0, 1, 0),
    Array(1, 0, 1, 1, 1),
    Array(0, 0, 1, 0, 0),
    Array(1, 1, 1, 1, 1)
  )
  
  val expectedGraph = Map(
    //did not write test cases in order
    Node(0, 0) -> List(Node(1, 0) -> 1, Node(0, 1) -> 1),
    Node(0, 1) -> List(Node(0, 0) -> 1),
    Node(0, 3) -> List(Node(1, 3) -> 1),
    Node(1, 0) -> List(Node(0, 0) -> 1, Node(2, 0) -> 1),
    Node(1, 3) -> List(Node(0, 3) -> 1, Node(2, 3) -> 1),
    Node(2, 0) -> List(Node(1, 0) -> 1),
    Node(2, 2) -> List(Node(2, 3) -> 1, Node(3, 2) -> 1),
    Node(2, 3) -> List(Node(2, 2) -> 1, Node(2, 4) -> 1, Node(1, 3) -> 1),
    Node(2, 4) -> List(Node(2, 3) -> 1),
    Node(3, 2) -> List(Node(2, 2) -> 1, Node(4, 2) -> 1),
    Node(4, 0) -> List(Node(4, 1) -> 1),
    Node(4, 1) -> List(Node(4, 0) -> 1, Node(4, 2) -> 1),
    Node(4, 2) -> List(Node(4, 1) -> 1, Node(4, 3) -> 1, Node(3, 2) -> 1),
    Node(4, 3) -> List(Node(4, 2) -> 1, Node(4, 4) -> 1),
    Node(4, 4) -> List(Node(4, 3) -> 1)
    )

    val graph = mazeToGraph(maze)

    val sortedGraph = graph.map { case (node, neighbors) =>
      node -> neighbors.sortBy { case (neighbor, _) => (neighbor.row, neighbor.col) }
    }.toMap

    val sortedExpectedGraph = expectedGraph.map { case (node, neighbors) =>
      node -> neighbors.sortBy { case (neighbor, _) => (neighbor.row, neighbor.col) }
    }.toMap

    assert(sortedGraph == sortedExpectedGraph)
  }
}
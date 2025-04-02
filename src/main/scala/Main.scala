import MazeInput._
import MazeGraph._
import MazeSolver._
import scala.io.StdIn._
import Timer._
import ParallelMazeSolver._

object Main extends App {
  // function to print the maze in a visual format
  def printMaze(maze: Array[Array[Int]], path: List[Node] = List()): Unit = {
    for (row <- maze.indices) {
      for (col <- maze(row).indices) {
        val node = Node(row, col)
        if (path.contains(node)) {
          print("P ") // path representation
        } else if (maze(row)(col) == 0) {
          print("X ") // wall representation
        } else {
          print("1 ") // walkable cell
        }
      }
      println()
    }
  }

  // read the maze from input
  val maze = readMaze()

  // convert to adjacency list (graph)
  val graph = mazeToGraph(maze)

  // get start and goal from the user
  println("Enter start row:")
  val startRow = readInt()

  println("Enter start column:")
  val startCol = readInt()

  val start = Node(startRow, startCol)

  println("Enter goal row:")
  val goalRow = readInt()

  println("Enter goal column:")
  val goalCol = readInt()

  val goal = Node(goalRow, goalCol)

  // print the original maze
  println("\nOriginal Maze:")
  printMaze(maze)

  // Algorithm selection
  println(
    "\nSelect the algorithms to run (separate multiple choices with commas (ex: 1,2,3)):"
  )
  println("1. Sequential BFS")
  println("2. Parallel BFS")
  println("3. Sequential A*")

  val choices = readLine().split(",").map(_.trim).map(_.toInt).toList

  // list of algorithms with names and function references
  val algorithms = List(
    ("1", "Sequential BFS", () => bfs(graph, start, goal)),
    ("2", "Parallel BFS", () => parallelBFS(graph, start, goal)),
    ("3", "Sequential A*", () => aStar(graph, start, goal))
  )

  // run the selected algorithms
  choices.foreach { choice =>
    algorithms.find(_._1 == choice.toString) match {
      case Some((_, algorithmName, algorithm)) =>
        println(s"\nRunning $algorithmName...")
        val (distance, path) = algorithm()

        println(s"\n=== $algorithmName ===")
        if (distance == -1) {
          println("No path found.")
        } else {
          println(s"Steps: $distance")
          println("Path found:")
          printMaze(maze, path) // print the maze with the path
          println(
            s"Path taken: ${path.map(n => s"(${n.row},${n.col})").mkString(" -> ")}"
          ) // path nodes in sequence
        }

      case None => println("Invalid choice. Please select a valid algorithm.")
    }
  }
}

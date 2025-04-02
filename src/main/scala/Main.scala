import MazeInput._
import MazeGraph._
import MazeSolver._
import scala.io.StdIn._
import Timer._
import ParallelMazeSolver._

object Main extends App {
  // Function to print the maze in a visual format
  def printMaze(maze: Array[Array[Int]], path: List[Node] = List()): Unit = {
    for (row <- maze.indices) {
      for (col <- maze(row).indices) {
        val node = Node(row, col)
        if (path.contains(node)) {
          print("P ") // Path representation
        } else if (maze(row)(col) == 0) {
          print("X ") // Wall representation
        } else {
          print("1 ") // Walkable cell
        }
      }
      println()
    }
  }

  // Read the maze from input
  val maze = MazeInput.readMaze()

  // Convert to adjacency list (graph)
  val graph = mazeToGraph(maze)

  // Get start and goal from the user
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

  // Print the original maze
  println("\nOriginal Maze:")
  printMaze(maze)

  // Algorithm selection prompt
  println(
    "\nSelect the algorithms to run (separate multiple choices with commas):"
  )
  println("1. Sequential BFS")
  println("2. Parallel BFS")
  println("3. Sequential A*")

  // Get user input for multiple algorithm choices
  val choices = readLine().split(",").map(_.trim).map(_.toInt).toList

  // List of algorithms with names and function references
  val algorithms = List(
    ("1", "Sequential BFS", () => bfs(graph, start, goal)),
    ("2", "Parallel BFS", () => parallelBFS(graph, start, goal)),
    ("3", "Sequential A*", () => aStar(graph, start, goal))
  )

  // Run the selected algorithms
  choices.foreach { choice =>
    algorithms.find(_._1 == choice.toString) match {
      case Some((_, algorithmName, algorithm)) =>
        println(s"\nRunning $algorithmName...")
        val (distance, path) = algorithm() // Run the selected algorithm

        println(s"\n=== $algorithmName ===")
        if (distance == -1) {
          println("No path found.")
        } else {
          println(s"Steps: $distance")
          println("Path found:")
          printMaze(maze, path) // Print the maze with the path
          println(
            s"Path taken: ${path.map(n => s"(${n.row},${n.col})").mkString(" -> ")}"
          ) // Print nodes in sequence
        }

      case None => println("Invalid choice. Please select a valid algorithm.")
    }
  }
}

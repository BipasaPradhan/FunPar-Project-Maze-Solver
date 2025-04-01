import MazeInput._
import MazeGraph._
import MazeSolver._
import scala.io.StdIn._

object Main extends App {
  // read maze from console (not file supported yet)
  val maze = readMaze()

  // convert to adjacency list
  val graph = mazeToGraph(maze)

  // enter start and end
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

  // algorithm
  val (distance, path) = dijkstra(graph, start, goal)

  // results
  if (distance == -1) {
    println("No path found.")
  } else {
    println(s"Shortest distance: $distance")
    println(s"Path found: ${path.mkString(" -> ")}")
  }

  println("\nMaze with Weights:")
  for (row <- maze.indices) {
    for (col <- maze(row).indices) {
      val value = maze(row)(col) match {
        case 1 => "1 " // normal
        case 2 => "2 " // mud
        case 3 => "3 " // water
        case 0 => "X " // wall
      }
      print(value)
    }
    println()
  }

}

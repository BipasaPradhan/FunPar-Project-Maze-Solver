import MazeInput._
import MazeGraph._
import MazeSolver._
import scala.io.StdIn._
import Timer._
import ParallelMazeSolver._

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

  // algorithm + time (sequential)
  println("\nRunning Sequential Dijkstra...")
  val ((seqDistance, seqPath), seqElapsedTime) = measureTime {
    dijkstra(graph, start, goal)
  }
  // algorithm + time (parallel)
  println("\nRunning Parallel Dijkstra...")
  val ((parDistance, parPath), parElapsedTime) = measureTime {
    parallelDijkstra(graph, start, goal)
  }

  // results
  println("\n=== Sequential Dijkstra ===")
  if (seqDistance == -1) {
    println("No path found.")
  } else {
    println(s"Shortest distance: $seqDistance")
    println(s"Path found: ${seqPath.mkString(" -> ")}")
    println(s"Execution time: ${seqElapsedTime} ms")
  }

  println("\n=== Parallel Dijkstra ===")
  if (parDistance == -1) {
    println("No path found.")
  } else {
    println(s"Shortest distance: $parDistance")
    println(s"Path found: ${parPath.mkString(" -> ")}")
    println(s"Execution time: ${parElapsedTime} ms")
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

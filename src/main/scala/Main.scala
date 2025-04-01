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
  println("\nRunning Sequential BFS...")
  val ((seqDistance, seqPath), seqElapsedTime) = measureTime {
    bfs(graph, start, goal)
  }

  // results
  println("\n=== Sequential BFS ===")
  if (seqDistance == -1) {
    println("No path found.")
  } else {
    println(s"Shortest distance: $seqDistance")
    println(s"Path found: ${seqPath.mkString(" -> ")}")
    println(s"Execution time: ${seqElapsedTime} ms")
  }

  println("\nMaze Representation:")
  for (row <- maze.indices) {
    println(maze(row).mkString(" "))
  }

}

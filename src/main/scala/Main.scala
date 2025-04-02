import MazeInput._
import MazeGraph._
import MazeSolver._
import scala.io.StdIn._
import Timer._
import ParallelMazeSolver._

object Main extends App {
//   // read maze from console (not file supported yet)
//   val maze = readMaze()

//   // convert to adjacency list
//   val graph = mazeToGraph(maze)

//   // enter start and end
//   println("Enter start row:")
//   val startRow = readInt()

//   println("Enter start column:")
//   val startCol = readInt()

//   val start = Node(startRow, startCol)

//   println("Enter goal row:")
//   val goalRow = readInt()

//   println("Enter goal column:")
//   val goalCol = readInt()

//   val goal = Node(goalRow, goalCol)

  val maze = Array.tabulate(100, 100) { (i, j) =>
    if ((i + j) % 3 == 0 && !(i == j || i == j + 1 || i == j - 1)) 0
    else 1
  }

  for (i <- 0 until 100) {
    maze(i)(i) = 1
    if (i < 99) {
      maze(i)(i + 1) = 1
      maze(i + 1)(i) = 1
    }
  }
  val graph = mazeToGraph(maze)
  val start = Node(0, 0)
  val goal = Node(99, 99)

  // algorithm + time (sequential)
  println("\nRunning Sequential BFS...")
  val ((seqDistance, seqPath), seqElapsedTime) = measureTime {
    bfs(graph, start, goal)
  }

  // algorithm + time (parallel)
  println("\nRunning Parallel BFS...")
  val ((parDistance, parPath), parElapsedTime) = measureTime {
    parallelBFS(graph, start, goal)
  }

  // results
  println("\n=== Sequential BFS ===")
  if (seqDistance == -1) {
    println("No path found.")
  } else {
    println(s"Steps: $seqDistance")
    // println(s"Path found: ${seqPath.mkString(" -> ")}")
    println(s"Execution time: ${seqElapsedTime} ms")
  }

  println("\n=== Parallel BFS ===")

  if (parDistance == -1) {
    println("No path found.")
  } else {
    println(s"Steps: $parDistance")
    // println(s"Path found: ${parPath.mkString(" -> ")}")
    println(s"Execution time: ${parElapsedTime} ms")
  }

//   println("\nMaze Representation:")
//   for (row <- maze.indices) {
//     println(maze(row).mkString(" "))
//   }

}

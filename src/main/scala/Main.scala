import MazeInput._
import MazeGraph._
import MazeSolver._
import scala.io.StdIn._
import Timer._
import ParallelMazeSolver._

object Main extends App {
  val size = 5
  val trials = 10
  val maze =
    Array.fill(size, size)(1) // Generate a 5x5 maze of all 1's (open spaces)
  val graph = mazeToGraph(maze) // Convert to adjacency list
  val start = Node(0, 0) // Start node (top-left)
  val goal = Node(size - 1, size - 1) // Goal node (bottom-right)

  // Sequential BFS
  println("\nRunning Sequential BFS...")
  val ((seqDistance, _), seqElapsedTime) =
    measureTime(bfs(graph, start, goal), trials)

  // Parallel BFS
  println("\nRunning Parallel BFS...")
  val ((parDistance, _), parElapsedTime) =
    measureTime(parallelBFS(graph, start, goal), trials)

  // Sequential A*
  println("\nRunning Sequential A*...")
  val ((seqDistanceA, _), seqElapsedTimeA) =
    measureTime(aStar(graph, start, goal), trials)

  // Print results
  println("\n=== Sequential BFS ===")
  println(s"Steps: $seqDistance")
  println(f"Avg Execution time: $seqElapsedTime%.2f ms")

  println("\n=== Parallel BFS ===")
  println(s"Steps: $parDistance")
  println(f"Avg Execution time: $parElapsedTime%.2f ms")

  println("\n=== Sequential A* ===")
  println(s"Steps: $seqDistanceA")
  println(f"Avg Execution time: $seqElapsedTimeA%.2f ms")
}

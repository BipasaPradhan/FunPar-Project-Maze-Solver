import java.util.concurrent._
import scala.collection.mutable
import MazeGraph._
import scala.concurrent.ExecutionContext.Implicits.global
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.collection.mutable
import scala.concurrent.ExecutionContext
import java.util.concurrent.atomic.AtomicReference

object ParallelMazeSolver {
  def parallelBFS(
      graph: mutable.Map[Node, List[(Node, Int)]],
      start: Node,
      end: Node
  ): (Int, List[Node]) = {
    val numThreads = Math.max(
      2,
      Runtime.getRuntime.availableProcessors() / 2
    ) // number of threads to use -> half of available processors

    // shortest distance to each viable node
    val distances = new ConcurrentHashMap[Node, Int]()
    graph.keys.foreach(node => distances.put(node, -1))
    distances.put(start, 0)

    // previous node for path reconstruction
    val previous = new ConcurrentHashMap[Node, Node]()

    val queue = new ConcurrentLinkedQueue[Node]()
    queue.add(start)

    // visited
    val visited = new ConcurrentHashMap[Node, java.lang.Boolean]()
    visited.put(start, true)

    // flag to stop when target is found
    val stopFlag = new AtomicBoolean(false)

    // thread pool
    val executor = Executors.newFixedThreadPool(numThreads)
    val tasks = (0 until numThreads).map { _ =>
      executor.submit(new Callable[Unit] {
        override def call(): Unit = {
          while (!queue.isEmpty && !stopFlag.get()) {
            val batch = new java.util.ArrayList[Node]()
            queue.synchronized {
              while (!queue.isEmpty && batch.size() < 50) { // take 50 at once
                batch.add(queue.poll())
              }
            }

            batch.forEach { currentNode =>
              if (currentNode != null) {
                for ((neighbor, _) <- graph.getOrElse(currentNode, List())) {
                  if (
                    visited
                      .putIfAbsent(neighbor, java.lang.Boolean.TRUE) == null
                  ) {
                    distances.put(neighbor, distances.get(currentNode) + 1)
                    previous.put(neighbor, currentNode)
                    queue.add(neighbor)
                    if (neighbor == end) stopFlag.set(true)
                  }
                }
              }
            }
          }
        }
      })
    }

    // wait for all tasks to complete
    tasks.foreach(_.get())
    executor.shutdown()

    // reconstruct the path
    if (!previous.containsKey(end)) {
      return (-1, List())
    }

    var path = List[Node]()
    var node = end
    while (previous.containsKey(node)) {
      path = node :: path
      node = previous.get(node)
    }
    path = start :: path

    (distances.getOrDefault(end, -1), path)
  }
}

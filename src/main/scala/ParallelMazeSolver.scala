import java.util.concurrent._
import scala.collection.mutable
import MazeGraph._
import ExecutionContext.Implicits.global

object ParallelMazeSolver {
  def parallelBFS(
      graph: mutable.Map[Node, List[Node]],
      start: Node,
      end: Node
  ): (Int, List[Node]) = {
    // stores shortest distance to each viable node
    val distances = new ConcurrentHashMap[Node, Int]()
    graph.keys.foreach(node => distances.put(node, -1))
    distances.put(start, 0)

    // track the previous node (node before reaching current node)
    val previous = new ConcurrentHashMap[Node, Node]()

    val queue = new ConcurrentLinkedQueue[Node]()
    queue.add(start) // start with the start node

    // track visited
    val visited = new ConcurrentHashMap[Node, Boolean]()
    visited.put(start, true)

    // flag to stop when node is found
    val stopFlag = new AtomicBoolean(false)

    val tasks =
      for (_ <- 0 until Runtime.getRuntime.availableProcessors()) yield {
        Future {
          while (!queue.isEmpty && !stopFlag.get()) {
            val currentNode = queue.poll()
            if (currentNode == null) return

            // explore neighbors
            for (neighbor <- graph.getOrElse(currentNode, List())) {
              if (visited.putIfAbsent(neighbor, true) == null) {
                distances.putIfAbsent(
                  neighbor,
                  distances.get(currentNode) + 1
                )
                previous.put(neighbor, currentNode)
                queue.add(neighbor)

                // end -> stop search
                if (neighbor == end) {
                  stopFlag.set(true)
                  return
                }
              }
            }
          }
        }
      }
    Await.result(Future.sequence(tasks), Duration.Inf)

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

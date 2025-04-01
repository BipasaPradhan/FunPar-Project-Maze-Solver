import java.util.concurrent._
import java.util.concurrent.atomic.AtomicBoolean
import scala.collection.mutable
import scala.jdk.CollectionConverters._
import MazeGraph._

object ParallelMazeSolver {
  def parallelDijkstra(
      graph: mutable.Map[Node, List[(Node, Int)]],
      start: Node,
      end: Node
  ): (Int, List[Node]) = {
    // stores shortest distance to each viable node
    val distances =
      new ConcurrentHashMap[Node, Int]()
    distances.put(start, 0)

    // track the previous node (node before reaching current node)
    val previous = new ConcurrentHashMap[Node, Node]()

    val pq = new PriorityBlockingQueue[(Node, Int)](
      100,
      Ordering.by[(Node, Int), Int](_._2).reverse
    )
    pq.put((start, 0))

    val numThreads = 4
    // threadpool with fixed number of threads
    val executor = Executors.newFixedThreadPool(numThreads)

    // flag to stop all threads when the end node is reached
    val stopFlag = new AtomicBoolean(false)

    val workers = (1 to numThreads).map { _ =>
      new Runnable {
        override def run(): Unit = {
          while (!stopFlag.get()) {
            val entry = pq.poll()
            if (entry == null) return
            val (currNode, currDis) = entry
            // skip nodes for which shorter path is found
            if (currDis > distances.getOrDefault(currNode, Int.MaxValue)) {
              // continue
            } else {
              if (currNode == end) {
                stopFlag.set(true)
                return
              }
              // update neighbors
              for ((neighbor, weight) <- graph.getOrElse(currNode, List())) {
                val newDis = currDis + weight
                var updated = false

                // if a shorter path is found
                distances.compute(
                  neighbor,
                  (_, curr) => {
                    val currDistance = Option(curr).getOrElse(Int.MaxValue)
                    if (newDis < currDistance) {
                      updated = true
                      newDis
                    } else currDistance
                  }
                )

                if (updated) {
                  previous.put(neighbor, currNode)
                  pq.put((neighbor, newDis))
                }

              }
            }
          }
        }
      }
    }

    workers.foreach(executor.execute)
    executor.shutdown()
    executor.awaitTermination(
      Long.MaxValue,
      TimeUnit.NANOSECONDS
    )

    // reconstruct the path from end to beginning & prepend
    if (!previous.containsKey(end)) {
      return (-1, List())
    }
    var path = List[Node]()
    var node = end
    while (previous.contains(node)) {
      path = node :: path
      node = previous.get(node)
    }
    path = start :: path

    (distances.getOrDefault(end, -1), path)
  }
}

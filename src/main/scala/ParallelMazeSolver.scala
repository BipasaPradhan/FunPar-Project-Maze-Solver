import java.util.concurrent.{
  ConcurrentHashMap,
  Executors,
  PriorityBlockingQueue,
  AtomicBoolean
}
import scala.collection.mutable
import scala.jdk.CollectionConverters._

object ParallelMazeSolver {
  def parallelDijkstra(
      graph: mutable.Map[Node, List[(Node, Int)]],
      start: Node,
      end: Node
  ): (Int, List[Node]) = {
    // stores shortest distance to each viable node
    val distances =
      new ConcurrentHashMap[Node, Int]().asScala.withDefaultValue(Int.MaxValue)
    distances.put(start, 0)

    // track the previous node (node before reaching current node)
    val previous = new ConcurrentHashMap[Node, Node]().asScala

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
      executor.submit(new Runnable {
        override def run(): Unit = {
          while (!stopFlag.get() && !pq.isEmpty) {
            val (currNode, currDis) = pq.poll()

            // skip nodes for which a shorter path is already found
            if (currDis > distances(currNode)) {
            } else {
              // Reached the end -> reconstruct the path from end to beginning & prepend
              if (currNode == end) {
                var path = List[Node]()
                var node = end
              }
            }
        }
      })
    }

  }
}

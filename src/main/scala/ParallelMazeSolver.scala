import java.util.concurrent.{
  ConcurrentHashMap,
  Executors,
  PriorityBlockingQueue
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
    distances(start) = 0

    // track the previous node (node before reaching current node)
    val previous = new ConcurrentHashMap[Node, Node]().asScala

    val pq = new PriorityBlockingQueue[(Node, Int)]()(
      Ordering.by[(Node, Int), Int](_._2).reverse
    ) // min-heap
    pq.enqueue((start, 0)) // start with the start node

  }
}

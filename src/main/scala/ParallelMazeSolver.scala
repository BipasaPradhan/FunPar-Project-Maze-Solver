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

object ParallelMazeSolver {
  def parallelBFS(
      graph: mutable.Map[Node, List[(Node, Int)]],
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
    val visited = new ConcurrentHashMap[Node, java.lang.Boolean]()
    visited.put(start, true)

    // flag to stop when node is found
    val stopFlag = new AtomicBoolean(false)

    val tasks = (0 until Runtime.getRuntime.availableProcessors()).map { _ =>
      Future {
        var continue = true
        while (continue && !stopFlag.get()) {
          val currentNode = queue.poll()
          if (currentNode == null) {
            continue = false
          } else {
            graph.getOrElse(currentNode, List()).foreach { case (neighbor, _) =>
              if (
                visited.putIfAbsent(neighbor, java.lang.Boolean.TRUE) == null
              ) {
                distances.put(neighbor, distances.get(currentNode) + 1)
                previous.put(neighbor, currentNode)
                queue.add(neighbor)
                if (neighbor == end) {
                  stopFlag.set(true)
                }
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

  def parallelAStar(
      graph: mutable.Map[Node, List[(Node, Int)]],
      start: Node,
      end: Node
  ): (Int, List[Node]) = {
    // Manhattan distance heuristic
    def heuristic(node: Node, goal: Node): Int = {
      math.abs(node.row - goal.row) + math.abs(node.col - goal.col)
    }

    val pq = mutable.PriorityQueue[(Node, Int)]()(
      Ordering.by[(Node, Int), Int](_._2).reverse
    )
    pq.enqueue((start, 0))

    val executor = Executors.newFixedThreadPool(numThreads)

    val gScore = new ConcurrentHashMap[Node, Int]()
    gScore.put(start, 0)

    val tScore = new ConcurrentHashMap[Node, Int]()
    tScore.put(start, heuristic(start, end))

    val previous = new ConcurrentHashMap[Node, Node]()

    // process nodes
    while (pq.nonEmpty) {
      val (current, _) = pq.dequeue()

      // reached the end -> reconstruct from end to beginning & prepend
      if (current == end) {
        var path = List[Node]()
        var node = end

        while (previous.containsKey(node)) {
          path = node :: path
          node = previous.get(node)
        }
        path = start :: path // prepend start node to path
        return (gScore.get(end), path) // return distance and path
      }

      
  }

}

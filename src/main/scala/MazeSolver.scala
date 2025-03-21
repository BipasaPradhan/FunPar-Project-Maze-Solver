import scala.collection.mutable
import MazeGraph._

object MazeSolver {
     
  //Dijkstra's Algorithm (Sequential Version)
  def dijkstra(graph: mutable.Map[Node, List[(Node, Int)]], start: Node, end: Node): (Int, List[Node]) = {
    //stores shortest distance to each viable node
    val distances = mutable.Map[Node, Int]().withDefaultValue(Int.MaxValue)
    distances(start) = 0

    //track the previous node (node before reaching current node)
    val previous = mutable.Map[Node, Node]()

    val pq = mutable.PriorityQueue[(Node, Int)]()(Ordering.by[(Node, Int), Int](_._2).reverse) //min-heap
    pq.enqueue((start, 0)) //start with the the start node

    while (pq.nonEmpty) {
      val (currNode, currDis) = pq.dequeue()

      //skip nodes for which a shorter path is already found
      if (currDis > distances(currNode)) {
      } else {
        // Reached the end -> reconstruct the path from end to beginning & prepend
        if (currNode == end) {
          var path = List[Node]()
          var node = end

          while (previous.contains(node)) {
            path = node :: path
            node = previous(node)
          }

          path = start :: path
          return (distances(end), path)
        }

        //update neighbors
        for ((neighbor, weight) <- graph.getOrElse(currNode, List())) {
          val newDis = currDis + weight

          //if a shorter path is found
          if (newDis < distances(neighbor)) {
            distances(neighbor) = newDis
            previous(neighbor) = currNode
            pq.enqueue((neighbor, newDis)) 
          }
        }
      }
    }

    //no path found
    (-1, List())
  }

}
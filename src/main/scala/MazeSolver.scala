import scala.collection.mutable
import MazeGraph._

object MazeSolver {
     
  //Dijkstra's Algorithm (Sequential Version)
  def dijkstra(graph: Map[Node, List[(Node, Int)]], start: Node, end: Node): (Int, List[Node]) = {
    //stores shortest distance to each viable node
    val distances = mutable.Map[Node, Int]().withDefaultValue(Int.MaxValue)
    distance(start) = 0

    //track the previous node (node before reaching current node)
    val previous = mutable.Map[Node, Node]()

    val toVisit = PriorityQueue[(Node, Int)]()(Ordering.by(-_._2)) //turn into min heap
    toVisit.enqueue((start, 0)) //enqueue the start 

    while(toVisit.nonEmpty) {
        val (currNode, currDis) = toVisit.dequeue()

        //reached the end
        if(currNode == end) {
            var path = List[Node]()
        }
    }
  }

}
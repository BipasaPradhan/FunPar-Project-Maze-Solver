import scala.io.StdIn._

object MazeInput {
  // read maze from console
  def readMaze(): Array[Array[Int]] = {
    println("Terrain Types:")
    println(
      "1 = Normal (cost 1), 2 = Mud (cost 3), 3 = Water (cost 5), 0 = Wall (not walkable)"
    )
    println("Enter the number of rows:")
    val rows = readInt()

    println("Enter the number of columns:")
    val cols = readInt()

    // turn into array
    println(s"Enter the maze (${rows}x${cols}) row by row:")
    val maze = Array.ofDim[Int](rows, cols)

    for (i <- 0 until rows) {
      val row = readLine().split(" ").map(_.toInt)
      maze(i) = row
    }
    maze
  }
}

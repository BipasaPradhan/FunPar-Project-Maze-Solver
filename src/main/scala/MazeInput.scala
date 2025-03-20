import scala.io.StdIn._

object MazeInput {
    //represents a cell of the maze
    case class Node(row: Int, col: Int)

    //directions for movement: left, right, down, up
    val directions = List((-1, 0), (1, 0), (0, -1), (0, 1))

    //read maze from console
    def readMaze(): Array[Array[Int]] = {
        println("Start reading the maze")
        println("Enter the number of rows:")
        val rows = readInt()
        println("Rows entered: " + rows)

        println("Enter the number of columns:")
        val cols = readInt()
        println("Columns entered: " + cols)

        println(s"Enter the maze (${rows}x${cols}) row by row:")
        val maze = Array.ofDim[Int](rows, cols)

        for (i <- 0 until rows) {
            println(s"Reading row $i")
            val row = readLine().split(" ").map(_.toInt)
            maze(i) = row
        }
        maze
    }
}
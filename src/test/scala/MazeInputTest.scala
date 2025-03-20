import org.scalatest.funsuite.AnyFunSuite
import java.io.ByteArrayInputStream
import MazeInput._

class MazeInputTest extends AnyFunSuite {

  test("readMaze should correctly read a small 2x2 maze") {
    val input = "2\n2\n1 0\n0 1\n"  //simulate input for 2x2

    //simulate user input 
    val inStream = new java.io.ByteArrayInputStream(input.getBytes)
    Console.withIn(inStream) {
      val maze = readMaze()
      
      //expected maze
      val expected = Array(
        Array(1, 0),
        Array(0, 1)
      )
      
      //comparing each row 
      for (i <- maze.indices) {
        assert(maze(i).sameElements(expected(i)), s"Row $i doesn't match")
      }
    }
  }
}
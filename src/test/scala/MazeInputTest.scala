import org.scalatest.funsuite.AnyFunSuite
import java.io.ByteArrayInputStream
import MazeInput._

class MazeInputTest extends AnyFunSuite {

  test("readMaze should correctly read a small 2x2 maze") {
    val input = "2\n2\n1 0\n0 1\n"  //simulate input for 2x2

    //simulate user input 
    val inStream = new ByteArrayInputStream(input.getBytes)
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

  test("readMaze should correctly read a 5x5 maze") {
    val input = "5\n5\n1 0 0 0 0\n1 1 1 0 0\n0 0 1 0 0\n0 0 1 1 1\n0 0 0 0 1\n"  
    
    val inStream = new ByteArrayInputStream(input.getBytes)
    Console.withIn(inStream) {
      val maze = readMaze()
      val expected = Array(
        Array(1, 0, 0, 0, 0),
        Array(1, 1, 1, 0, 0),
        Array(0, 0, 1, 0, 0),
        Array(0, 0, 1, 1, 1),
        Array(0, 0, 0, 0, 1)
      )
      for (i <- maze.indices) {
        assert(maze(i).sameElements(expected(i)), s"Row $i doesn't match")
      }
    }
  }

  test("readMaze should correctly handle a maze with no valid path (in cases for pathfinding later)") {
    val input = "3\n3\n1 0 0\n0 0 0\n0 0 1\n"  

    val inStream = new ByteArrayInputStream(input.getBytes)
    Console.withIn(inStream) {
      val maze = readMaze()
      val expected = Array(
        Array(1, 0, 0),
        Array(0, 0, 0),
        Array(0, 0, 1)
      )
      for (i <- maze.indices) {
        assert(maze(i).sameElements(expected(i)), s"Row $i doesn't match")
      }
    }
  }

  test("readMaze should correctly handle a single-row maze") {
    val input = "1\n5\n1 1 0 1 1\n"  
    
    val inStream = new ByteArrayInputStream(input.getBytes)
    
    Console.withIn(inStream) {
        val maze = readMaze()
        val expected = Array(Array(1, 1, 0, 1, 1))
        
        for (i <- maze.indices) {assert(maze(i).sameElements(expected(i)), s"Row $i doesn't match")
        }
    }
  }

  test("readMaze should correctly handle a 3x3 maze") {
    val input = "3\n3\n1 0 0\n1 0 0\n1 1 1\n"  

    val inStream = new ByteArrayInputStream(input.getBytes)
    Console.withIn(inStream) {
      val maze = readMaze()
      val expected = Array(
        Array(1, 0, 0),
        Array(1, 0, 0),
        Array(1, 1, 1)
      )
      for (i <- maze.indices) {
        assert(maze(i).sameElements(expected(i)), s"Row $i doesn't match")
      }
    }
  }
}
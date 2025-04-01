import scala.concurrent.duration._
object Timer {
  def measureTime[T](block: => T): (T, Long) = {
    val startTime = System.nanoTime()
    val result = block
    val duration = (System.nanoTime() - startTime).nanos.toMillis
    (result, duration)
  }
}

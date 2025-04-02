import scala.concurrent.duration._
object Timer {
  def measureTime[T](block: => T, trials: Int): (T, Double) = {

    // Measure multiple trials
    val times = (1 to trials).map { _ =>
      val startTime = System.nanoTime()
      val result = block
      val duration =
        (System.nanoTime() - startTime) / 1_000_000.0 // Convert to milliseconds
      (result, duration)
    }

    val (finalResult, durations) = times.unzip
    val medianTime = durations.sorted.apply(durations.length / 2) // Use median

    (finalResult.head, medianTime)
  }
}

name := "MazeSolver"

version := "0.1"

scalaVersion := "3.6.2"  

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.9" % Test
)
Compile / mainClass := Some("Main")

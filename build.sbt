javaOptions += "-Xmx64G"
javaOptions += "-XX:+UseG1GC"

name := "MazeSolver"

version := "0.1"

scalaVersion := "3.6.2"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.10" % Test
)

Compile / mainClass := Some("Main")

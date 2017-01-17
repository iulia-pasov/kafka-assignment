lazy val root = (project in file(".")).
  settings(
    name := "SparkStreamDemo",
    version := "0.1",
    organization := "com.assignment",
    scalaVersion := "2.11.8"
  )

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}


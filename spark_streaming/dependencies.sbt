val sparkVersion = "2.1.0"
val kafkaWriter = "0.2.0"
val playJsonVersion = "2.4.0-M2"

libraryDependencies ++= Seq(
  ("org.apache.spark" %% "spark-core" % sparkVersion % "provided").
    exclude("org.mortbay.jetty", "servlet-api").
    exclude("commons-beanutils", "commons-beanutils-core").
    exclude("commons-collections", "commons-collections").
    exclude("commons-logging", "commons-logging").
    exclude("com.esotericsoftware.minlog", "minlog"),
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided",
  "com.github.benfradet" %% "spark-kafka-0-10-writer" % kafkaWriter,
  "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.13",
  "org.apache.spark" %% "spark-hive" % sparkVersion % "provided",
  "com.typesafe.play" %% "play-json" % playJsonVersion
)

# Spark Streaming Project

### Compile and package up the example programs

First create the avro Message class.
```
java -jar /path/to/avro/tools.jar compile schema src/resources/Message.avsc src/main/java/
```

then
```
$ sbt assembly
...
```

### Running with Spark Streaming

The Spark Streaming project reads JSON String messages from a given topic and
writes Messages to another topic. This assumes that there are messages in the
initial topic. They can be added with the Java message-producer
(see other README for details).

The application requires 3 parameters:
* the bootstrap servers
* input topic
* output topic

Run it with your prefered spark streaming parameters, similar to the example:
```
$ /path/to/spark-2.1.0/bin/spark-submit --master local --packages org.apache.spark:spark-streaming-kafka-0-10_2.11:2.1.0 --class "com.assignment.MessageStreaming" target/scala-2.11/SparkStreamDemo-assembly-0.1.jar "localhost:9092" "inputTopic" "outputTopic"
```

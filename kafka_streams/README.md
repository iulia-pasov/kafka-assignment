# Kafka Streams Project

### Compile and package up the example programs
```
$ mvn package
...
```

The project is set up to produces an executable, `target/kafka-assignment`,
that includes all of the example programs and dependencies.

### Running the producers

There are 2 available producers for this assignment. They all require the same
arguments:
* a path to a configuration file for the Producer properties, default in
src/main/resources/
* a topic name where the data is sent
* a path to the JSON file that will be produced

The first producer, `whole-producer`, sends the whole content of the JSON file to
the given topic.
The second producer, `message-producer`, reads the data from the JSON in a List of
messages and sends each message to the given topic.

```
$ target/kafka-assignment whole-producer producer.props TopicName /path/to/json
$ target/kafka-assignment message-producer producer.props TopicName /path/to/json
```
### Running the stream

There is only one Java consumer option for the kafka-assignment project,
implemented with kafka streams. It reads JSON messages from an input topic,
hashes the username and writes to another topic. I needs 3 arguments:
* a path to a configuration file for the Stream properties, default in
src/main/resources
* an input topic name from which JSON messages are read
* an output topic name where the hashed-user messages are written

The option can also be used with the executable, but with the option
`message-stream`, as in the example:

```
target/kafka-assignment message-stream stream.props inputTopic outputTopic
```

# Kafka Assignment

## The Task
Given a JSON file containing an array of objects
{username: <user>, message: <msg>}. As an example:
```
[{"username": "Joana", "message": "Die immer lacht"},
{"username": "Holz", "message": "meine Bank, mein Tisch, meine Treppe, .."}]
```
The task is to ingest it into Kafka in a topic and on the consumer side to read
messages from Kafka, mask the username, and then write back to another Kafka
topic.

This README will take you through the steps for running the Producers and
Consumers.

## Pre-requisites
To start, you need to get Kafka up and running and create some topics.

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
### Running the consumer

There is only one Java consumer for the kafka-assignment project. It reads JSON
messages and hashes the username. I needs 3 arguments:
* a path to a configuration file for the Stream properties, default in
src/main/resources
* an input topic name from which JSON messages are read
* an output topic name where the hashed-user messages are written

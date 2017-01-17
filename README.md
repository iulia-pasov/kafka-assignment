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
Consumers. The project is split into two parts:
* the kafka-streams Java project
* the spark-streaming Scala project


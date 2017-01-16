package com.assignment

import java.util.Properties
import java.io.ByteArrayOutputStream

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;

import org.apache.avro.specific.SpecificDatumWriter
import org.apache.avro.io.EncoderFactory
import assignment.avro.Message

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kafka.common.serialization.ByteArraySerializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import com.github.benfradet.spark.kafka010.writer._

object MessageStreaming {
   def main(args: Array[String]) {
      if (args.length < 3) {
         System.err.println("Please insert input and output topics")
         System.exit(1)
      }

      val (zkQuorum, inputTopic, outputTopic) = (args(0), args(1), args(2))

      val sparkConf = new SparkConf().setAppName("Message Stream Demo")
      val streamingContext = new StreamingContext(sparkConf, Seconds(3))
      streamingContext.checkpoint("checkpoint")

      val kafkaParams = Map[String, Object](
         "bootstrap.servers" -> "localhost:9092",
         "key.deserializer" -> classOf[StringDeserializer],
         "value.deserializer" -> classOf[StringDeserializer],
         "group.id" -> "x",
         "auto.offset.reset" -> "earliest",
         "enable.auto.commit" -> (false: java.lang.Boolean))

      val producerProps = {
         val p = new Properties()
         p.setProperty("bootstrap.servers", "localhost:9092")
         p.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")//classOf[StringSerializer].getName)
         p.setProperty("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer") //classOf[ByteArraySerializer].getName)
         p
      }

      val kafkaTopics = Array(inputTopic)

      import _root_.kafka.serializer.StringDecoder
      val directStream = KafkaUtils.createDirectStream[String, String](
         streamingContext,
         PreferConsistent,
         Subscribe[String, String](kafkaTopics, kafkaParams))

      directStream.map{ record =>
         val msgString = record.value;
         val msg: Message = MessageReader.readFromText(msgString)
         val toSend = new Message(msg.username.hashCode.toString, msg.message)
         val msgBytes = serializeMessage(toSend)
         msgBytes
      }.writeToKafka(
            producerProps,
            s => new ProducerRecord[String, Array[Byte]](outputTopic, s))

      streamingContext.start()
      streamingContext.awaitTermination()
   }

   def serializeMessage(msg: Message): Array[Byte] = {
      val out = new ByteArrayOutputStream()
      val encoder = EncoderFactory.get.binaryEncoder(out, null)
      val writer = new SpecificDatumWriter[Message](Message.getClassSchema)

      writer.write(msg, encoder)
      encoder.flush
      out.close
      out.toByteArray
   }
}

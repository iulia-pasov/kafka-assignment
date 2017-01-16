package com.assignment;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.google.common.io.Resources;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KeyValueMapper;

public class MessageStreamDemo {

    public static void main(String[] args) throws Exception {
	if(args.length < 3) {
	    System.out.println("Please enter config path, input and output topics");
	    return;
	}
	String config = args[1];

	Properties props = new Properties();
	try (InputStream conf = Resources.getResource(config).openStream()) {
	    props.load(conf);
	    props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
	    props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
	} catch (IOException e) {
	    System.out.printf("Could not read file %s", config);
	    return;
	}
	props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

	KStreamBuilder builder = new KStreamBuilder();

	String inputTopic = args[2];
	String outputTopic = args[3];
	final ObjectMapper mapper = new ObjectMapper();

	KStream<String, String> messages = builder.stream(inputTopic);

	KStream<String, String> hashed = messages.map( new KeyValueMapper<String, String, KeyValue<String, String>>() {
		@Override
		public KeyValue<String, String> apply(String key, String value) {
		    try{
			Message inputMessage = mapper.readValue(value, Message.class);
			Message outputMessage = inputMessage.hashMe();
                        return new KeyValue<String, String> (key, mapper.writeValueAsString(outputMessage));
		    } catch (JsonProcessingException e) {
			return new KeyValue<String, String> (key,  String.format("Could not process json <%s>", value));
		    } catch (Exception e) {
			return new KeyValue<String, String> (key, String.format("Could not read json <%s>", value));
		    }

		}
	});

        // write to the result topic
        hashed.to(outputTopic);

        KafkaStreams streams = new KafkaStreams(builder, props);
        streams.start();

        Thread.sleep(5000L);

        streams.close();
    }
}

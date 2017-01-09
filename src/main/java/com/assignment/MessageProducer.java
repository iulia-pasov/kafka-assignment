package com.assignment;


import java.util.Properties;
import java.util.List;

import java.io.InputStream;
import java.io.IOException;

import static java.nio.file.Paths.get;
import static java.nio.file.Files.readAllBytes;

import com.google.common.io.Resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class MessageProducer {

    public static void main(String[] args) {
        if(args.length < 4) {
            System.out.println("Please enter config path, topic name and input file path");
            return;
        }

        String config = args[1];
        Properties props = new Properties();
        try (InputStream conf = Resources.getResource(config).openStream()) {
            props.load(conf);
        } catch (IOException e) {
            System.out.printf("Could not read configuration from %s", config);
            return;
        }
        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        String topicName = args[2];
        String jsonPath = args[3];

        try {
            ObjectMapper mapper = new ObjectMapper();
            String contentJSON = new String(readAllBytes(get(jsonPath)));
            List<Message> messages = mapper.readValue(contentJSON, new TypeReference<List<Message>>(){});

            int key = 0;
            for (Message msg: messages) {
                key++;
                String text = mapper.writeValueAsString(msg);
                producer.send(new ProducerRecord<String, String>(topicName, String.valueOf(key), text));
            }

            System.out.printf("%s messages sent successfully to %s", key, topicName);
        } catch (Throwable t) {
            System.out.printf("%s", t.getStackTrace());
        }finally {
            producer.close();
        }
    }
}

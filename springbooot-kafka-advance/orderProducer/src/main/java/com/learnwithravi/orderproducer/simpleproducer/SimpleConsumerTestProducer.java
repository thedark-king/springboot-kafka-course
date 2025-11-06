package com.learnwithravi.orderproducer.simpleproducer;

import com.learnwithravi.orderproducer.OrderProducerApplication;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class SimpleConsumerTestProducer {
    public static void main(String[] args) {
        SpringApplication.run(OrderProducerApplication.class, args);

        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");


        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
        ProducerRecord<String, String> record = new ProducerRecord<>("SimpleConsumerTopic", "TestKey1", "TestValue1");

        try {
            producer.send(record);
            System.out.println("Producer sent to Topic: " + record.topic());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }

    }
}

package com.learnwithravi.orderconsumer.avarideserializer;

import com.learnwithravi.orderconsumer.TruckAssignmentConsumer;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@SpringBootApplication
public class GenericAvroOrderConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenericAvroOrderConsumerApplication.class, args);
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "OrderGroup");
        props.setProperty("key.deserializer", KafkaAvroDeserializer.class.getName());
        props.setProperty("value.deserializer", KafkaAvroDeserializer.class.getName());
        props.setProperty("schema.registry.url", "http://localhost:8088");



        TruckAssignmentConsumer truckAssignmentConsumer = new TruckAssignmentConsumer();
        truckAssignmentConsumer.truckAssignConsume();

        KafkaConsumer<String, GenericRecord> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("OrderAvroGRTopic"));

        ConsumerRecords<String, GenericRecord> records = consumer.poll(Duration.ofSeconds(20));

        records.forEach(record -> {
            String customerName = record.key();
            System.out.println(" CustomerName : " + customerName);
            GenericRecord order = record.value();
            System.out.println(" Item Name : " + order.get("itemName") + " Quantity : " + order.get("quantity"));
        });
        consumer.close();
    }

}

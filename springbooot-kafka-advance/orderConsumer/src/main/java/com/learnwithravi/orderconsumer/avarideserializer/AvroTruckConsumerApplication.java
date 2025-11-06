package com.learnwithravi.orderconsumer.avarideserializer;

import com.learnwithravi.orderconsumer.TruckAssignmentConsumer;
import com.learnwithravi.orderproducer.avro.Truck;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@SpringBootApplication
public class AvroTruckConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AvroTruckConsumerApplication.class, args);
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "TruckGroup");
        props.setProperty("key.deserializer", KafkaAvroDeserializer.class.getName());
        props.setProperty("value.deserializer", KafkaAvroDeserializer.class.getName());
        props.setProperty("schema.registry.url", "http://localhost:8088");
        props.setProperty("specific.avro.reader", "true");



        TruckAssignmentConsumer truckAssignmentConsumer = new TruckAssignmentConsumer();
        truckAssignmentConsumer.truckAssignConsume();

        KafkaConsumer<String, Truck> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("TruckAvroTopic"));

        ConsumerRecords<String, Truck> records = consumer.poll(Duration.ofSeconds(20));
        records.forEach(record -> {
            String truckId = record.key();
            System.out.println(" Truck ID : " + truckId);
            Truck truck = record.value();
            System.out.println(" Truck Location - Latitude : " + truck.getLatitude() + " Longitude : " + truck.getLongitude());
        });
        consumer.close();
    }

}

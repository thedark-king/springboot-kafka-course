package com.learnwithravi.orderconsumer;

import com.learnwithravi.orderconsumer.DTO.Truck;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Properties;

//@Component
public class TruckAssignmentConsumer {


    public void truckAssignConsume() {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "com.learnwithravi.orderconsumer.customeDeserializer.TruckDeserializer");
        props.setProperty("group.id", "TruckAssignmentGroup");

        KafkaConsumer<String, Truck> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(java.util.Collections.singletonList("TruckCSAssignmentTopic"));

        ConsumerRecords<String, Truck> records = consumer.poll(Duration.ofSeconds(30));
        records.forEach(record -> {
            String truckId = record.key();
            System.out.println(" Truck ID : " + truckId);
            Truck truck = record.value();
            System.out.println(" Truck Location - Latitude : " + truck.getLatitude() + " Longitude : " + truck.getLongitude());
        });
        consumer.close();

    }
}

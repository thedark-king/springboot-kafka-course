package com.learnwithravi.orderproducer;

import com.learnwithravi.orderproducer.avro.Truck;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.UUID;

@Component
public class TruckAssignmentProducer {

    public void truckAssign() {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("key.serializer", KafkaAvroSerializer.class.getName());
        props.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        props.setProperty("schema.registry.url", "http://localhost:8088");

        Truck truck = new Truck();
        truck.setId(UUID.randomUUID().toString());
        truck.setLatitude("37.7749");
        truck.setLongitude("-122.4194");

        KafkaProducer<String, Truck> truckAssignmentKafkaProducer = new KafkaProducer<>(props);
        ProducerRecord<String, Truck> record = new ProducerRecord<>("TruckAvroTopic", UUID.randomUUID().toString(), truck);
        truckAssignmentKafkaProducer.send(record);
        System.out.println("sending truck assignment to topic");
    }
}
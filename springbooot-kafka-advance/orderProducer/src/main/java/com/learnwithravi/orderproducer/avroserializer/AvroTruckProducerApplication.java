package com.learnwithravi.orderproducer.avroserializer;

import com.learnwithravi.orderproducer.TruckAssignmentProducer;
import com.learnwithravi.orderproducer.avro.Truck;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;
import java.util.UUID;

@SpringBootApplication
public class AvroTruckProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AvroTruckProducerApplication.class, args);

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("key.serializer", KafkaAvroSerializer.class.getName());
        props.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        props.setProperty("schema.registry.url", "http://localhost:8088");

        Truck truck = new Truck();
        truck.setId(UUID.randomUUID().toString());
        truck.setLatitude("37.7749");
        truck.setLongitude("-122.4194");

        KafkaProducer<String, Truck> producer = new KafkaProducer<String, Truck>(props);
        ProducerRecord<String, Truck> record = new ProducerRecord<>("TruckAvroTopic", truck.getId().toString(), truck);

        //Calling truck assignment producer
        System.out.println("calling truck assignment producer");
        TruckAssignmentProducer truckAssignmentProducer = new TruckAssignmentProducer();
        truckAssignmentProducer.truckAssign();
        try {
//1            Send and forget method
//            Future<RecordMetadata> future = producer.send(record);

//2            Synchronous send
//            RecordMetadata recordMetadata = producer.send(record).get();
//            System.out.println("Message sent to partition " + recordMetadata.partition() + " with offset " + recordMetadata.partition());
//            System.out.println("Message offset " + recordMetadata.offset());
//            System.out.println("Sent message to topic");

//3            Asynchronous send
            producer.send(record);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }

}

package com.learnwithravi.orderproducer.avroserializer;

import com.learnwithravi.orderproducer.TruckAssignmentProducer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class GenericAvroOrderProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenericAvroOrderProducerApplication.class, args);

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("key.serializer", KafkaAvroSerializer.class.getName());
        props.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        props.setProperty("schema.registry.url", "http://localhost:8088");
        props.setProperty("avro.generic.record", "true");

        KafkaProducer<String, GenericRecord> producer = new KafkaProducer<>(props);
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse("{\n" +
                " \"type\":\"record\",\n" +
                " \"name\":\"Order\",\n" +
                " \"namespace\":\"com.learnwithravi.orderproducer.avro\",\n" +
                " \"fields\":[\n" +
                "     {\"name\":\"customerName\",\"type\":\"string\"},\n" +
                "     {\"name\":\"itemName\",\"type\":\"string\"},\n" +
                "     {\"name\":\"quantity\",\"type\":\"int\"}\n" +
                " ]\n" +
                "}");
        GenericRecord order = new org.apache.avro.generic.GenericData.Record(schema);
        order.put("customerName", "John Doe");
        order.put("itemName", "Samsung Galaxy S21");
        order.put("quantity", 5);
        ProducerRecord<String, GenericRecord> record = new ProducerRecord<>("OrderAvroGRTopic", order.get("customerName").toString(), order);

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

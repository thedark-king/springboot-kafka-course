package com.learnwithravi.orderproducer;

import com.learnwithravi.orderproducer.DTO.Order;
import com.learnwithravi.orderproducer.customdeserializeer.VIPPartitioner;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class OrderProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderProducerApplication.class, args);

        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.learnwithravi.orderproducer.customdeserializeer.OrderSerializer");
        props.setProperty("partitioner.class", VIPPartitioner.class.getName());
        props.setProperty(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "order-producer-1");
//        props.setProperty(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, "10000");
//        props.setProperty(ProducerConfig.ACKS_CONFIG, "all");
//        props.setProperty(ProducerConfig.BUFFER_MEMORY_CONFIG, "233434343");
//        props.setProperty(ProducerConfig.RETRIES_CONFIG, "3");
//        props.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
//        props.setProperty(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, "500");
//        props.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, "16384");
//        props.setProperty(ProducerConfig.LINGER_MS_CONFIG, "200");
//        props.setProperty(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, "100");
//        props.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

        Order order = new Order();
        order.setCustomerName("VIPCustomer1");
        order.setQuantity(10);
        order.setItemName("macbook Air");

        Order order2 = new Order();
        order2.setCustomerName("NormalCustomer1");
        order2.setQuantity(5);
        order2.setItemName("Dell Inspiron");

        KafkaProducer<String, Order> producer = new KafkaProducer<String, Order>(props);
        producer.initTransactions();
        ProducerRecord<String, Order> record = new ProducerRecord<>("OrderPartitionedTopic", order.getCustomerName(), order);
        ProducerRecord<String, Order> record2 = new ProducerRecord<>("OrderPartitionedTopic", order2.getCustomerName(), order2);

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
            producer.beginTransaction();
            producer.send(record);
            producer.send(record2);
            producer.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }

}
